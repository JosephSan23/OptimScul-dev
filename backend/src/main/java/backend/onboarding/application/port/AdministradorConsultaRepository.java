package backend.onboarding.application.port;

import java.util.List;

public interface AdministradorConsultaRepository {
    List<AdministradorResumen> listarTodos();
}