package backend.academic.application.usecase.actividad.entrega;

import backend.academic.application.port.EntregaActividadRepository;
import backend.academic.application.service.ContextoEstudianteService;
import backend.academic.domain.model.EntregaActividad;
import backend.academic.domain.model.EstadoEntregaActividad;
import backend.academic.infrastructure.rest.dto.EntregaAcademica.MiEntregaResponseDto;
import backend.shared.ModuloDocumento;
import backend.shared.application.port.DocumentoRepository;
import backend.shared.application.port.StoragePort;
import backend.shared.domain.model.Documento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EliminarArchivoEntregaUseCase {

    private final ContextoEstudianteService contexto;
    private final EntregaActividadRepository entregaRepo;
    private final DocumentoRepository documentoRepo;
    private final StoragePort storage;
    private final ObtenerMiEntregaUseCase obtenerMiEntrega;

    public EliminarArchivoEntregaUseCase(ContextoEstudianteService contexto, EntregaActividadRepository entregaRepo,
            DocumentoRepository documentoRepo, StoragePort storage, ObtenerMiEntregaUseCase obtenerMiEntrega) {
        this.contexto = contexto;
        this.entregaRepo = entregaRepo;
        this.documentoRepo = documentoRepo;
        this.storage = storage;
        this.obtenerMiEntrega = obtenerMiEntrega;
    }

    @Transactional
    public MiEntregaResponseDto ejecutar(UUID usuarioId, UUID actividadId, UUID documentoId) {
        var ctx = contexto.resolver(usuarioId);

        EntregaActividad entrega = entregaRepo.findByActividadIdAndEstudianteId(actividadId, ctx.estudianteId())
                .orElseThrow(() -> new RuntimeException("No tienes una entrega para esta actividad."));
        if (entrega.getEstado() == EstadoEntregaActividad.CALIFICADA)
            throw new RuntimeException("La entrega ya fue calificada; no se puede modificar.");

        Documento doc = documentoRepo.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("El archivo no existe."));

        // Autorización: el documento debe ser de ESTA entrega y de la institución del estudiante
        boolean esDeLaEntrega = doc.getModulo() == ModuloDocumento.ACTIVIDAD
                && entrega.getId().equals(doc.getEntidadId());
        boolean mismaInstitucion = doc.getInstitucionId() != null
                && doc.getInstitucionId().equals(ctx.institucionId());
        if (!esDeLaEntrega || !mismaInstitucion)
            throw new SecurityException("No puedes eliminar este archivo.");

        storage.eliminar(doc.getUrlArchivo());
        documentoRepo.deleteById(documentoId);

        return obtenerMiEntrega.ejecutar(usuarioId, actividadId);
    }
}