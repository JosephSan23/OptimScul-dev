package backend.academic.application.usecase.actividad.entrega;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.EntregaActividadRepository;
import backend.academic.application.port.EstudianteDeClase;
import backend.academic.application.service.ContextoEstudianteService;
import backend.academic.domain.model.*;
import backend.academic.infrastructure.rest.dto.EntregaAcademica.MiEntregaResponseDto;
import backend.shared.ModuloDocumento;
import backend.shared.application.port.DocumentoRepository;
import backend.shared.application.port.StoragePort;
import backend.shared.domain.model.Documento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubirEntregaUseCase {

    private final ContextoEstudianteService contexto;
    private final ActividadAcademicaRepository actividadRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final DocenteConsultaRepository docenteConsulta;
    private final EntregaActividadRepository entregaRepo;
    private final DocumentoRepository documentoRepo;
    private final StoragePort storage;
    private final ObtenerMiEntregaUseCase obtenerMiEntrega;

    public SubirEntregaUseCase(ContextoEstudianteService contexto, ActividadAcademicaRepository actividadRepo,
            CargaAcademicaRepository cargaRepo, DocenteConsultaRepository docenteConsulta,
            EntregaActividadRepository entregaRepo, DocumentoRepository documentoRepo,
            StoragePort storage, ObtenerMiEntregaUseCase obtenerMiEntrega) {
        this.contexto = contexto;
        this.actividadRepo = actividadRepo;
        this.cargaRepo = cargaRepo;
        this.docenteConsulta = docenteConsulta;
        this.entregaRepo = entregaRepo;
        this.documentoRepo = documentoRepo;
        this.storage = storage;
        this.obtenerMiEntrega = obtenerMiEntrega;
    }

    /** Archivo que llega desde el controlador, sin acoplar la capa de aplicación a Spring web. */
    public record ArchivoEntrada(String nombreOriginal, String mimeType, long tamano, InputStream contenido) {}

    @Transactional
    public MiEntregaResponseDto ejecutar(UUID usuarioId, UUID actividadId, String comentario,
            List<ArchivoEntrada> archivos) {

        var ctx = contexto.resolver(usuarioId);

        ActividadAcademica act = actividadRepo.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("La actividad no existe."));
        if (!act.getInstitucionId().equals(ctx.institucionId()))
            throw new SecurityException("Esta actividad no pertenece a tu institución.");
        if (act.getEstado() != EstadoActividad.PUBLICADA)
            throw new RuntimeException("La actividad no está disponible para entregas.");

        // El estudiante debe pertenecer al grupo de esa clase
        CargaAcademica carga = cargaRepo.findById(act.getCargaAcademicaId())
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        Set<UUID> delGrupo = docenteConsulta.estudiantesDeGrupo(carga.getGrupoId()).stream()
                .map(EstudianteDeClase::getEstudianteId).collect(Collectors.toSet());
        if (!delGrupo.contains(ctx.estudianteId()))
            throw new SecurityException("No perteneces a esta clase.");

        LocalDateTime ahora = LocalDateTime.now();

        // Validar cierre y calcular si es entrega tardía
        boolean cerrada = act.getFechaCierre() != null && ahora.isAfter(act.getFechaCierre());
        boolean permiteTardia = Boolean.TRUE.equals(act.getPermiteEntregaTardia());
        if (cerrada && !permiteTardia)
            throw new RuntimeException("La actividad está cerrada; ya no se aceptan entregas.");
        boolean tardia = act.getFechaEntrega() != null && ahora.isAfter(act.getFechaEntrega());
        EstadoEntregaActividad estadoEntrega =
                tardia ? EstadoEntregaActividad.ENTREGADA_TARDE : EstadoEntregaActividad.ENTREGADA;

        // Buscar la entrega existente o crear una nueva
        EntregaActividad entrega = entregaRepo.findByActividadIdAndEstudianteId(actividadId, ctx.estudianteId())
                .orElseGet(() -> {
                    EntregaActividad nueva = new EntregaActividad();
                    nueva.setId(UUID.randomUUID());
                    nueva.setActividadId(actividadId);
                    nueva.setEstudianteId(ctx.estudianteId());
                    nueva.setCreatedAt(ahora);
                    return nueva;
                });
        if (entrega.getEstado() == EstadoEntregaActividad.CALIFICADA)
            throw new RuntimeException("La entrega ya fue calificada; no se puede modificar.");

        entrega.setComentarioEstudiante(comentario);
        entrega.setFechaEntrega(ahora);
        entrega.setEstado(estadoEntrega);
        entrega.setUpdatedAt(ahora);
        entrega = entregaRepo.save(entrega);
        final UUID entregaId = entrega.getId();

        // Subir cada archivo al storage y registrar su Documento
        for (ArchivoEntrada a : archivos) {
            String limpio = sanitizar(a.nombreOriginal());
            String clave = ctx.institucionId() + "/actividades/" + actividadId
                    + "/entregas/" + entregaId + "/" + UUID.randomUUID() + "_" + limpio;

            storage.subir(clave, a.contenido(), a.tamano(),
                    a.mimeType() != null ? a.mimeType() : "application/octet-stream");

            Documento doc = new Documento();
            doc.setId(UUID.randomUUID());
            doc.setInstitucionId(ctx.institucionId());
            doc.setModulo(ModuloDocumento.ACTIVIDAD);
            doc.setEntidadId(entregaId);
            doc.setNombreArchivo(clave);
            doc.setNombreOriginal(a.nombreOriginal());
            doc.setUrlArchivo(clave);          // guardamos la clave interna, no una URL pública
            doc.setMimeType(a.mimeType());
            doc.setTamanoBytes(a.tamano());
            doc.setSubidoPorUsuarioId(usuarioId);
            doc.setCreatedAt(ahora);
            documentoRepo.save(doc);
        }

        return obtenerMiEntrega.ejecutar(usuarioId, actividadId);
    }

    private String sanitizar(String nombre) {
        if (nombre == null || nombre.isBlank()) return "archivo";
        return nombre.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}