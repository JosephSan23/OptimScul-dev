package backend.config.application.usecase.jornada;

import backend.config.infrastructure.rest.dto.JornadaRequestDto;
import backend.people.application.port.JornadaRepository;
import backend.people.domain.model.Jornada;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarJornadaUseCase {
    private final JornadaRepository jornadaRepository;
    private final AutorizacionService autorizacionService;
    public EditarJornadaUseCase(JornadaRepository jornadaRepository, AutorizacionService autorizacionService) {
        this.jornadaRepository = jornadaRepository; this.autorizacionService = autorizacionService;
    }
    @Transactional
    public Jornada ejecutar(UUID adminId, UUID jornadaId, JornadaRequestDto dto) {
        UUID inst = autorizacionService.institucionDelAdmin(adminId);
        Jornada j = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new RuntimeException("La jornada no existe."));
        if (!inst.equals(j.getInstitucionId())) throw new SecurityException("No puedes editar jornadas de otra institución.");
        j.setCodigo(dto.getCodigo());
        j.setNombre(dto.getNombre());
        j.setDescripcion(dto.getDescripcion());
        j.setHoraInicio(dto.getHoraInicio());
        j.setHoraFin(dto.getHoraFin());
        j.setUpdatedAt(LocalDateTime.now());
        return jornadaRepository.save(j);
    }
}