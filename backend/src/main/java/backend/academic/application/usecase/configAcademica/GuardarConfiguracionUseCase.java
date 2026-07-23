package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.ConfiguracionAcademicaRepository;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.academic.infrastructure.rest.dto.ConfiguracionAcademicaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GuardarConfiguracionUseCase {
    private final ConfiguracionAcademicaRepository repo;
    private final AutorizacionService auth;

    public GuardarConfiguracionUseCase(ConfiguracionAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional
    public ConfiguracionAcademica ejecutar(UUID usuarioId, ConfiguracionAcademicaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ObtenerConfiguracionUseCase.ROLES);

        if (dto.getNotaMinima().compareTo(dto.getNotaMaxima()) >= 0)
            throw new RuntimeException("La nota mínima debe ser menor que la máxima.");
        if (dto.getNotaMinimaAprobacion().compareTo(dto.getNotaMinima()) < 0
                || dto.getNotaMinimaAprobacion().compareTo(dto.getNotaMaxima()) > 0)
            throw new RuntimeException("La nota de aprobación debe estar entre la mínima y la máxima.");
        if (Boolean.TRUE.equals(dto.getUsaPeriodos()) && dto.getNumeroPeriodos() < 1)
            throw new RuntimeException("Debe haber al menos un periodo.");

        LocalDateTime ahora = LocalDateTime.now();
        ConfiguracionAcademica c = repo.findByInstitucionId(inst).orElseGet(() -> {
            ConfiguracionAcademica nueva = new ConfiguracionAcademica();
            nueva.setId(UUID.randomUUID());
            nueva.setInstitucionId(inst);
            nueva.setCreatedAt(ahora);
            return nueva;
        });
        c.setUsaPeriodos(dto.getUsaPeriodos());
        c.setNumeroPeriodos(dto.getNumeroPeriodos());
        c.setNotaMinimaAprobacion(dto.getNotaMinimaAprobacion());
        c.setNotaMinima(dto.getNotaMinima());
        c.setNotaMaxima(dto.getNotaMaxima());
        c.setDecimalesNota(dto.getDecimalesNota());
        c.setUsaRecuperacion(dto.getUsaRecuperacion());
        c.setAsistenciaPorClase(dto.getAsistenciaPorClase());
        c.setManejaComportamiento(dto.getManejaComportamiento());
        c.setManejaPuestos(dto.getManejaPuestos());
        c.setPorcentajeInasistenciaReprobacion(dto.getPorcentajeInasistenciaReprobacion());
        c.setUpdatedAt(ahora);
        return repo.save(c);
    }
}