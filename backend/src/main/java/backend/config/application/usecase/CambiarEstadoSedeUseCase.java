package backend.config.application.usecase;

import backend.people.application.port.SedeRepository;
import backend.people.domain.model.EstadoRegistro;
import backend.people.domain.model.Sede;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoSedeUseCase {
    private final SedeRepository sedeRepository;
    private final AutorizacionService autorizacionService;

    public CambiarEstadoSedeUseCase(SedeRepository sedeRepository, AutorizacionService autorizacionService) {
        this.sedeRepository = sedeRepository;
        this.autorizacionService = autorizacionService;
    }

    @Transactional public void activar(UUID adminId, UUID sedeId)   { cambiar(adminId, sedeId, EstadoRegistro.ACTIVO); }
    @Transactional public void inactivar(UUID adminId, UUID sedeId) { cambiar(adminId, sedeId, EstadoRegistro.INACTIVO); }

    private void cambiar(UUID adminId, UUID sedeId, EstadoRegistro estado) {
        UUID institucionId = autorizacionService.institucionDelAdmin(adminId);
        Sede s = sedeRepository.findById(sedeId)
                .orElseThrow(() -> new RuntimeException("La sede no existe."));
        if (!institucionId.equals(s.getInstitucionId())) {
            throw new SecurityException("No puedes cambiar sedes de otra institución.");
        }
        s.setEstado(estado);
        s.setUpdatedAt(LocalDateTime.now());
        sedeRepository.save(s);
    }
}