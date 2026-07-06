package backend.onboarding.application.usecase;

import backend.onboarding.application.port.AdministradorConsultaRepository;
import backend.onboarding.application.port.AdministradorResumen;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarAdministradoresUseCase {

    private final AdministradorConsultaRepository administradorConsultaRepository;
    private final AutorizacionService autorizacionService;

    public ListarAdministradoresUseCase(AdministradorConsultaRepository administradorConsultaRepository,
                                        AutorizacionService autorizacionService) {
        this.administradorConsultaRepository = administradorConsultaRepository;
        this.autorizacionService = autorizacionService;
    }

    public List<AdministradorResumen> ejecutar(UUID superAdminId) {
        autorizacionService.exigirSuperAdmin(superAdminId);
        return administradorConsultaRepository.listarTodos();
    }
}