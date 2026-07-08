package backend.staff.application.usecase;

import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.domain.model.UsuarioInstitucion;
import backend.staff.application.port.StaffConsultaRepository;
import backend.staff.application.port.StaffResumen;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarStaffUseCase {

    private final StaffConsultaRepository staffConsultaRepository;
    private final AutorizacionService autorizacionService;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;

    public ListarStaffUseCase(StaffConsultaRepository staffConsultaRepository,
                              AutorizacionService autorizacionService,
                              UsuarioInstitucionRepository usuarioInstitucionRepository) {
        this.staffConsultaRepository = staffConsultaRepository;
        this.autorizacionService = autorizacionService;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
    }

    public List<StaffResumen> ejecutar(UUID adminId) {
        UUID institucionId = usuarioInstitucionRepository.findByUsuarioId(adminId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst()
                .map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("No perteneces a ninguna institución."));

        autorizacionService.exigirRolEnInstitucion(adminId, institucionId, "ADMIN_INSTITUCION");
        return staffConsultaRepository.listarPorInstitucion(institucionId);
    }
}