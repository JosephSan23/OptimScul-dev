package backend.config.application.usecase;

import backend.people.application.port.JornadaRepository;
import backend.people.domain.model.EstadoRegistro;
import backend.people.domain.model.Jornada;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoJornadaUseCase {
    private final JornadaRepository jornadaRepository;
    private final AutorizacionService autorizacionService;
    public CambiarEstadoJornadaUseCase(JornadaRepository jornadaRepository, AutorizacionService autorizacionService) {
        this.jornadaRepository = jornadaRepository; this.autorizacionService = autorizacionService;
    }
    @Transactional public void activar(UUID a, UUID id)   { cambiar(a, id, EstadoRegistro.ACTIVO); }
    @Transactional public void inactivar(UUID a, UUID id) { cambiar(a, id, EstadoRegistro.INACTIVO); }
    private void cambiar(UUID adminId, UUID jornadaId, EstadoRegistro estado) {
        UUID inst = autorizacionService.institucionDelAdmin(adminId);
        Jornada j = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new RuntimeException("La jornada no existe."));
        if (!inst.equals(j.getInstitucionId())) throw new SecurityException("No puedes cambiar jornadas de otra institución.");
        j.setEstado(estado); j.setUpdatedAt(LocalDateTime.now()); jornadaRepository.save(j);
    }
}