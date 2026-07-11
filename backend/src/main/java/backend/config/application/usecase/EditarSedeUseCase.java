package backend.config.application.usecase;

import backend.config.infrastructure.rest.dto.SedeRequestDto;
import backend.people.application.port.SedeRepository;
import backend.people.domain.model.Sede;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarSedeUseCase {
    private final SedeRepository sedeRepository;
    private final AutorizacionService autorizacionService;

    public EditarSedeUseCase(SedeRepository sedeRepository, AutorizacionService autorizacionService) {
        this.sedeRepository = sedeRepository;
        this.autorizacionService = autorizacionService;
    }

    @Transactional
    public Sede ejecutar(UUID adminId, UUID sedeId, SedeRequestDto dto) {
        UUID institucionId = autorizacionService.institucionDelAdmin(adminId);
        Sede s = sedeRepository.findById(sedeId)
                .orElseThrow(() -> new RuntimeException("La sede no existe."));
        if (!institucionId.equals(s.getInstitucionId())) {
            throw new SecurityException("No puedes editar sedes de otra institución.");
        }
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
        s.setUpdatedAt(LocalDateTime.now());
        return sedeRepository.save(s);
    }
}