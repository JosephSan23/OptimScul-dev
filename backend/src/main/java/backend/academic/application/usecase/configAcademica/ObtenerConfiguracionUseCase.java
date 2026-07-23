package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.ConfiguracionAcademicaRepository;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ObtenerConfiguracionUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final ConfiguracionAcademicaRepository repo;
    private final AutorizacionService auth;

    public ObtenerConfiguracionUseCase(ConfiguracionAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional
    public ConfiguracionAcademica ejecutar(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        return repo.findByInstitucionId(inst).orElseGet(() -> crearDefault(inst));
    }

    private ConfiguracionAcademica crearDefault(UUID inst) {
        LocalDateTime ahora = LocalDateTime.now();
        ConfiguracionAcademica c = new ConfiguracionAcademica();
        c.setId(UUID.randomUUID());
        c.setInstitucionId(inst);
        c.setUsaPeriodos(true);
        c.setNumeroPeriodos((short) 4);
        c.setNotaMinimaAprobacion(new BigDecimal("3.00"));
        c.setNotaMinima(new BigDecimal("0.00"));
        c.setNotaMaxima(new BigDecimal("5.00"));
        c.setDecimalesNota((short) 2);
        c.setUsaRecuperacion(true);
        c.setAsistenciaPorClase(true);
        c.setManejaComportamiento(true);
        c.setManejaPuestos(false);
        c.setCreatedAt(ahora);
        c.setUpdatedAt(ahora);
        return repo.save(c);
    }
}