package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.ConfiguracionAcademicaRepository;
import backend.academic.application.port.EscalaValorativaRepository;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.academic.domain.model.EscalaValorativa;
import backend.academic.infrastructure.rest.dto.EscalaValorativaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearEscalaUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final EscalaValorativaRepository repo;
    private final ConfiguracionAcademicaRepository configRepo;
    private final AutorizacionService auth;

    public CrearEscalaUseCase(EscalaValorativaRepository repo, ConfiguracionAcademicaRepository configRepo,
                              AutorizacionService auth) {
        this.repo = repo; this.configRepo = configRepo; this.auth = auth;
    }

    @Transactional
    public EscalaValorativa ejecutar(UUID usuarioId, EscalaValorativaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        validar(inst, dto.getNotaMinima(), dto.getNotaMaxima(), null);

        LocalDateTime ahora = LocalDateTime.now();
        EscalaValorativa e = new EscalaValorativa();
        e.setId(UUID.randomUUID());
        e.setInstitucionId(inst);
        e.setNombre(dto.getNombre());
        e.setAbreviatura(dto.getAbreviatura());
        e.setNotaMinima(dto.getNotaMinima());
        e.setNotaMaxima(dto.getNotaMaxima());
        e.setAprueba(dto.getAprueba());
        e.setOrden(dto.getOrden());
        e.setActiva(true);
        e.setCreatedAt(ahora);
        e.setUpdatedAt(ahora);
        return repo.save(e);
    }

    /** Rango dentro de config + sin solape con otras bandas activas. excluirId != null al editar. */
    void validar(UUID inst, BigDecimal min, BigDecimal max, UUID excluirId) {
        if (min.compareTo(max) >= 0)
            throw new RuntimeException("La nota mínima debe ser menor que la máxima.");

        ConfiguracionAcademica cfg = configRepo.findByInstitucionId(inst)
                .orElseThrow(() -> new RuntimeException(
                        "Configura primero la escala de notas en Configuración académica."));
        if (min.compareTo(cfg.getNotaMinima()) < 0 || max.compareTo(cfg.getNotaMaxima()) > 0)
            throw new RuntimeException("La banda debe estar entre " + cfg.getNotaMinima()
                    + " y " + cfg.getNotaMaxima() + " (rango definido en Configuración académica).");

        // Solape: [min, max) contra las demás bandas ACTIVAS. Tocarse (min == otraMax) está permitido.
        for (EscalaValorativa otra : repo.findByInstitucionId(inst)) {
            if (excluirId != null && excluirId.equals(otra.getId())) continue;
            if (!Boolean.TRUE.equals(otra.getActiva())) continue;
            boolean seSolapan = min.compareTo(otra.getNotaMaxima()) < 0
                             && max.compareTo(otra.getNotaMinima()) > 0;
            if (seSolapan)
                throw new RuntimeException("El rango se cruza con la banda \"" + otra.getNombre()
                        + "\" (" + otra.getNotaMinima() + "–" + otra.getNotaMaxima() + ").");
        }
    }
}