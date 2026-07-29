package backend.academic.application.usecase.actividad.entrega;

import backend.academic.application.port.EntregaActividadRepository;
import backend.academic.application.usecase.actividad.ObtenerActividadUseCase;
import backend.academic.domain.model.EntregaActividad;
import backend.academic.infrastructure.rest.dto.EntregaAcademica.EntregaDocenteResponseDto;
import backend.shared.ModuloDocumento;
import backend.shared.application.port.DocumentoRepository;
import backend.shared.application.port.StoragePort;
import backend.shared.infrastructure.storage.StorageProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class ListarEntregasActividadUseCase {

    private final ObtenerActividadUseCase obtenerActividad; // valida que la actividad sea del docente
    private final EntregaActividadRepository entregaRepo;
    private final DocumentoRepository documentoRepo;
    private final StoragePort storage;
    private final StorageProperties storageProps;

    public ListarEntregasActividadUseCase(ObtenerActividadUseCase obtenerActividad,
            EntregaActividadRepository entregaRepo, DocumentoRepository documentoRepo,
            StoragePort storage, StorageProperties storageProps) {
        this.obtenerActividad = obtenerActividad;
        this.entregaRepo = entregaRepo;
        this.documentoRepo = documentoRepo;
        this.storage = storage;
        this.storageProps = storageProps;
    }

    public List<EntregaDocenteResponseDto> ejecutar(UUID usuarioId, UUID actividadId) {
        // Lanza excepción si la actividad no existe o no es del docente autenticado
        obtenerActividad.ejecutar(usuarioId, actividadId);

        Duration exp = Duration.ofMinutes(storageProps.getUrlExpirationMinutes());

        List<EntregaActividad> entregas = entregaRepo.findByActividadId(actividadId);

        return entregas.stream().map(e -> {
            List<EntregaDocenteResponseDto.Archivo> archivos = documentoRepo
                    .findByModuloAndEntidadId(ModuloDocumento.ACTIVIDAD, e.getId()).stream()
                    .map(d -> new EntregaDocenteResponseDto.Archivo(
                            d.getId(), d.getNombreOriginal(), d.getMimeType(), d.getTamanoBytes(),
                            storage.generarUrlDescarga(d.getUrlArchivo(), exp)))
                    .toList();
            return new EntregaDocenteResponseDto(
                    e.getId(), e.getEstudianteId(), e.getEstado(),
                    e.getComentarioEstudiante(), e.getFechaEntrega(), archivos);
        }).toList();
    }
}