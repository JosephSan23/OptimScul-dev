package backend.config.application.usecase;

import backend.config.infrastructure.rest.dto.JornadaRequestDto;
import backend.people.application.port.JornadaRepository;
import backend.people.domain.model.EstadoRegistro;
import backend.people.domain.model.Jornada;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearJornadaUseCase {
    private final JornadaRepository jornadaRepository;
    private final AutorizacionService autorizacionService;
    public CrearJornadaUseCase(JornadaRepository jornadaRepository, AutorizacionService autorizacionService) {
        this.jornadaRepository = jornadaRepository; this.autorizacionService = autorizacionService;
    }
    @Transactional
    public Jornada ejecutar(UUID adminId, JornadaRequestDto dto) {
        UUID inst = autorizacionService.institucionDelAdmin(adminId);
        LocalDateTime ahora = LocalDateTime.now();
        Jornada j = new Jornada();
        j.setId(UUID.randomUUID());
        j.setInstitucionId(inst);
        j.setCodigo(dto.getCodigo());
        j.setNombre(dto.getNombre());
        j.setDescripcion(dto.getDescripcion());
        j.setHoraInicio(dto.getHoraInicio());
        j.setHoraFin(dto.getHoraFin());
        j.setEstado(EstadoRegistro.ACTIVO);
        j.setCreatedAt(ahora);
        j.setUpdatedAt(ahora);
        return jornadaRepository.save(j);
    }
}