package backend.config.application.usecase;

import backend.config.infrastructure.rest.dto.InstitucionConfigRequestDto;
import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarMiInstitucionUseCase {
    private final InstitucionRepository institucionRepository;
    private final AutorizacionService autorizacionService;
    public EditarMiInstitucionUseCase(InstitucionRepository institucionRepository, AutorizacionService autorizacionService) {
        this.institucionRepository = institucionRepository; this.autorizacionService = autorizacionService;
    }
    @Transactional
    public Institucion ejecutar(UUID adminId, InstitucionConfigRequestDto dto) {
        UUID inst = autorizacionService.institucionDelAdmin(adminId);
        Institucion i = institucionRepository.findById(inst)
                .orElseThrow(() -> new RuntimeException("Institución no encontrada."));
        i.setNombre(dto.getNombre());
        i.setNombreCorto(dto.getNombreCorto());
        i.setDescripcion(dto.getDescripcion());
        i.setNit(dto.getNit());
        i.setDane(dto.getDane());
        i.setResolucionFuncionamiento(dto.getResolucionFuncionamiento());
        i.setCorreoContacto(dto.getCorreoContacto());
        i.setTelefonoContacto(dto.getTelefonoContacto());
        i.setSitioWeb(dto.getSitioWeb());
        i.setDireccionPrincipal(dto.getDireccionPrincipal());
        i.setCiudad(dto.getCiudad());
        i.setDepartamento(dto.getDepartamento());
        i.setPais(dto.getPais());
        i.setZonaHoraria(dto.getZonaHoraria());
        i.setMoneda(dto.getMoneda());
        i.setUpdatedAt(LocalDateTime.now());
        return institucionRepository.save(i);
    }
}