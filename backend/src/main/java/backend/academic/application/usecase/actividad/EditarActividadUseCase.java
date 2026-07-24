package backend.academic.application.usecase.actividad;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.EstadoActividad;
import backend.academic.infrastructure.rest.dto.ActividadAcademicaRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarActividadUseCase {
    private final ActividadAcademicaRepository actividadRepo;
    private final ObtenerActividadUseCase obtener;

    public EditarActividadUseCase(ActividadAcademicaRepository actividadRepo, ObtenerActividadUseCase obtener) {
        this.actividadRepo = actividadRepo; this.obtener = obtener;
    }

    @Transactional
    public ActividadAcademica ejecutar(UUID usuarioId, UUID actividadId, ActividadAcademicaRequestDto dto) {
        ActividadAcademica a = obtener.ejecutar(usuarioId, actividadId);   // valida propiedad
        if (a.getEstado() == EstadoActividad.ANULADA)
            throw new RuntimeException("No se puede editar una actividad anulada.");
        if (dto.getNotaMaxima() != null && dto.getNotaMaxima().signum() <= 0)
            throw new RuntimeException("La nota máxima debe ser mayor que cero.");
        if (dto.getPorcentaje() != null &&
                (dto.getPorcentaje().signum() < 0 || dto.getPorcentaje().compareTo(new BigDecimal("100")) > 0))
            throw new RuntimeException("El porcentaje debe estar entre 0 y 100.");

        a.setTipo(dto.getTipo());
        a.setTitulo(dto.getTitulo());
        a.setDescripcion(dto.getDescripcion());
        a.setPorcentaje(dto.getPorcentaje());
        if (dto.getNotaMaxima() != null) a.setNotaMaxima(dto.getNotaMaxima());
        a.setPermiteEntregaTardia(dto.getPermiteEntregaTardia() != null ? dto.getPermiteEntregaTardia() : a.getPermiteEntregaTardia());
        a.setFechaEntrega(dto.getFechaEntrega() != null ? dto.getFechaEntrega().atStartOfDay() : null);
        a.setFechaCierre(dto.getFechaCierre() != null ? dto.getFechaCierre().atStartOfDay() : null);
        a.setUpdatedBy(usuarioId);
        a.setUpdatedAt(LocalDateTime.now());
        return actividadRepo.save(a);
    }
}