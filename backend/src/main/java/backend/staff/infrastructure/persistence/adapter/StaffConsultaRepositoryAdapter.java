package backend.staff.infrastructure.persistence.adapter;

import backend.staff.application.port.StaffConsultaRepository;
import backend.staff.application.port.StaffResumen;
import backend.staff.infrastructure.persistence.StaffConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class StaffConsultaRepositoryAdapter implements StaffConsultaRepository {

    private final StaffConsultaJpaRepository jpaRepository;

    public StaffConsultaRepositoryAdapter(StaffConsultaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<StaffResumen> listarPorInstitucion(UUID institucionId) {
        return jpaRepository.listarPorInstitucion(institucionId.toString());
    }
}