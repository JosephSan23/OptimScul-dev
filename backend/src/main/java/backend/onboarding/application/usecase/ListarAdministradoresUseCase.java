package backend.onboarding.application.usecase;

import backend.onboarding.application.port.AdministradorConsultaRepository;
import backend.onboarding.application.port.AdministradorResumen;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarAdministradoresUseCase {

    private final AdministradorConsultaRepository administradorConsultaRepository;
    private final UsuarioRepository usuarioRepository;

    public ListarAdministradoresUseCase(AdministradorConsultaRepository administradorConsultaRepository,
                                        UsuarioRepository usuarioRepository) {
        this.administradorConsultaRepository = administradorConsultaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<AdministradorResumen> ejecutar(UUID superAdminId) {
        Usuario solicitante = usuarioRepository.findById(superAdminId)
                .orElseThrow(() -> new SecurityException("Usuario no autorizado."));
        List<String> roles = usuarioRepository.findRolesByUsuarioId(superAdminId);
        boolean esSuperAdmin = solicitante.getTipoContexto() == TipoContextoUsuario.PLATAFORMA
                && !roles.contains("VISITANTE");
        if (!esSuperAdmin) {
            throw new SecurityException("Solo el super administrador puede consultar los administradores.");
        }
        return administradorConsultaRepository.listarTodos();
    }
}