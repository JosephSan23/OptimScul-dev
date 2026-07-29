package backend.academic.infrastructure.rest.dto.EntregaAcademica;

import backend.academic.domain.model.EstadoEntregaActividad;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EntregaDocenteResponseDto(
        UUID entregaId,
        UUID estudianteId,
        EstadoEntregaActividad estado,
        String comentarioEstudiante,
        LocalDateTime fechaEntrega,
        List<Archivo> documentos
) {
    public record Archivo(
            UUID id,
            String nombreOriginal,
            String mimeType,
            Long tamanoBytes,
            String urlDescarga
    ) {}
}