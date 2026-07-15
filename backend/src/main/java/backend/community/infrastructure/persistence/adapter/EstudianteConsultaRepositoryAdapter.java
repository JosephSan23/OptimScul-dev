package backend.community.infrastructure.persistence.adapter;

import backend.community.application.port.EstudianteConsultaRepository;
import backend.community.application.port.EstudianteResumen;
import backend.community.infrastructure.persistence.EstudianteConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EstudianteConsultaRepositoryAdapter implements EstudianteConsultaRepository {
    private final EstudianteConsultaJpaRepository jpa;

    public EstudianteConsultaRepositoryAdapter(EstudianteConsultaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<EstudianteResumen> listarPorInstitucion(UUID inst) {
        return jpa.listarPorInstitucion(inst.toString());
    }
}