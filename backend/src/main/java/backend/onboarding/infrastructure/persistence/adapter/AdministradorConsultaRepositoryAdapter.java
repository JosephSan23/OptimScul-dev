package backend.onboarding.infrastructure.persistence.adapter;

import backend.onboarding.application.port.AdministradorConsultaRepository;
import backend.onboarding.application.port.AdministradorResumen;
import backend.onboarding.infrastructure.persistence.AdministradorConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdministradorConsultaRepositoryAdapter implements AdministradorConsultaRepository {

    private final AdministradorConsultaJpaRepository jpaRepository;

    public AdministradorConsultaRepositoryAdapter(AdministradorConsultaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<AdministradorResumen> listarTodos() {
        return jpaRepository.listarAdministradores();
    }
}