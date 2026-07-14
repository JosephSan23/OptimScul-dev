package backend.config.application.usecase.sede;

import backend.config.infrastructure.rest.dto.SedeRequestDto;
import backend.people.application.port.SedeRepository;
import backend.people.domain.model.EstadoRegistro;
import backend.people.domain.model.Sede;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearSedeUseCase {
    private final SedeRepository sedeRepository;
    private final AutorizacionService autorizacionService;

    public CrearSedeUseCase(SedeRepository sedeRepository, AutorizacionService autorizacionService) {
        this.sedeRepository = sedeRepository;
        this.autorizacionService = autorizacionService;
    }

    @Transactional
    public Sede ejecutar(UUID adminId, SedeRequestDto dto) {
        UUID institucionId = autorizacionService.institucionDelAdmin(adminId);
        LocalDateTime ahora = LocalDateTime.now();

        Sede s = new Sede();
        s.setId(UUID.randomUUID());
        s.setInstitucionId(institucionId);
        s.setCodigo(dto.getCodigo());
        s.setNombre(dto.getNombre());
        s.setDescripcion(dto.getDescripcion());
        s.setDireccion(dto.getDireccion());
        s.setTelefono(dto.getTelefono());
        s.setCorreo(dto.getCorreo());
        s.setCiudad(dto.getCiudad());
        s.setDepartamento(dto.getDepartamento());
        s.setPais(dto.getPais());
        s.setPrincipal(dto.getPrincipal() != null && dto.getPrincipal());
        s.setEstado(EstadoRegistro.ACTIVO);
        s.setCreatedAt(ahora);
        s.setUpdatedAt(ahora);
        return sedeRepository.save(s);
    }
}