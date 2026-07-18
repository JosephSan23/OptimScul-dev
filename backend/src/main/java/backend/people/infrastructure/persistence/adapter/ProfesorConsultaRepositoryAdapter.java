package backend.people.infrastructure.persistence.adapter;

import backend.people.application.port.profesor.ProfesorConsultaRepository;
import backend.people.application.port.profesor.ProfesorResumen;
import backend.people.infrastructure.persistence.ProfesorConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProfesorConsultaRepositoryAdapter implements ProfesorConsultaRepository {
    private final ProfesorConsultaJpaRepository jpa;

    public ProfesorConsultaRepositoryAdapter(ProfesorConsultaJpaRepository jpa) { this.jpa = jpa; }

    @Override
    public List<ProfesorResumen> listarPorInstitucion(UUID inst) {
        return jpa.listarPorInstitucion(inst.toString());
    }
}