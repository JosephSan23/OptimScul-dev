package backend.academic.application.usecase.actividad.entrega;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.application.port.EntregaActividadRepository;
import backend.academic.application.service.ContextoEstudianteService;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.EntregaActividad;
import backend.academic.domain.model.EstadoEntregaActividad;
import backend.academic.infrastructure.rest.dto.EntregaAcademica.MiEntregaResponseDto;
import backend.shared.ModuloDocumento;
import backend.shared.application.port.DocumentoRepository;
import backend.shared.application.port.StoragePort;
import backend.shared.infrastructure.storage.StorageProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerMiEntregaUseCase {

    private final ContextoEstudianteService contexto;
    private final ActividadAcademicaRepository actividadRepo;
    private final EntregaActividadRepository entregaRepo;
    private final DocumentoRepository documentoRepo;
    private final StoragePort storage;
    private final StorageProperties storageProps;

    public ObtenerMiEntregaUseCase(ContextoEstudianteService contexto, ActividadAcademicaRepository actividadRepo,
            EntregaActividadRepository entregaRepo, DocumentoRepository documentoRepo,
            StoragePort storage, StorageProperties storageProps) {
        this.contexto = contexto;
        this.actividadRepo = actividadRepo;
        this.entregaRepo = entregaRepo;
        this.documentoRepo = documentoRepo;
        this.storage = storage;
        this.storageProps = storageProps;
    }

    public MiEntregaResponseDto ejecutar(UUID usuarioId, UUID actividadId) {
        var ctx = contexto.resolver(usuarioId);

        ActividadAcademica act = actividadRepo.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("La actividad no existe."));
        if (!act.getInstitucionId().equals(ctx.institucionId()))
            throw new SecurityException("Esta actividad no pertenece a tu institución.");

        Optional<EntregaActividad> entregaOpt = entregaRepo.findByActividadIdAndEstudianteId(actividadId,
                ctx.estudianteId());

        if (entregaOpt.isEmpty()) {
            return new MiEntregaResponseDto(null, actividadId,
                    EstadoEntregaActividad.PENDIENTE, null, null, List.of());
        }

        EntregaActividad e = entregaOpt.get();
        Duration exp = Duration.ofMinutes(storageProps.getUrlExpirationMinutes());

        List<MiEntregaResponseDto.DocumentoResumen> docs = documentoRepo
                .findByModuloAndEntidadId(ModuloDocumento.ACTIVIDAD, e.getId()).stream()
                .map(d -> new MiEntregaResponseDto.DocumentoResumen(
                        d.getId(), d.getNombreOriginal(), d.getMimeType(), d.getTamanoBytes(),
                        storage.generarUrlDescarga(d.getUrlArchivo(), exp)))
                .toList();

        return new MiEntregaResponseDto(e.getId(), actividadId, e.getEstado(),
                e.getComentarioEstudiante(), e.getFechaEntrega(), docs);
    }
}