package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.EscalaValorativaRepository;
import backend.academic.domain.model.EscalaValorativa;
import backend.academic.infrastructure.rest.dto.EscalaValorativaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarEscalaUseCase {
    private final EscalaValorativaRepository repo;
    private final AutorizacionService auth;
    private final CrearEscalaUseCase crear;

    public EditarEscalaUseCase(EscalaValorativaRepository repo, AutorizacionService auth, CrearEscalaUseCase crear) {
        this.repo = repo; this.auth = auth; this.crear = crear;
    }

    @Transactional
    public EscalaValorativa ejecutar(UUID usuarioId, UUID escalaId, EscalaValorativaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, CrearEscalaUseCase.ROLES);
        EscalaValorativa e = repo.findById(escalaId)
                .orElseThrow(() -> new RuntimeException("La escala no existe."));
        if (!inst.equals(e.getInstitucionId()))
            throw new SecurityException("No puedes editar escalas de otra institución.");
        crear.validar(inst, dto.getNotaMinima(), dto.getNotaMaxima(), escalaId);

        e.setNombre(dto.getNombre());
        e.setAbreviatura(dto.getAbreviatura());
        e.setNotaMinima(dto.getNotaMinima());
        e.setNotaMaxima(dto.getNotaMaxima());
        e.setAprueba(dto.getAprueba());
        e.setOrden(dto.getOrden());
        e.setUpdatedAt(LocalDateTime.now());
        return repo.save(e);
    }
}