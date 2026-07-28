package backend.academic.infrastructure.rest.dto.EntregaAcademica;

import backend.academic.domain.model.EstadoEntregaActividad;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MiEntregaResponseDto(
        UUID entregaId,
        UUID actividadId,
        EstadoEntregaActividad estado,
        String comentarioEstudiante,
        LocalDateTime fechaEntrega,
        List<DocumentoResumen> documentos
) {
    public record DocumentoResumen(
            UUID id,
            String nombreOriginal,
            String mimeType,
            Long tamanoBytes,
            String urlDescarga
    ) {}
}
