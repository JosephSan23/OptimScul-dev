package backend.enrollment.infrastructure.persistence.adapter;

import backend.enrollment.application.port.MatriculaConsultaRepository;
import backend.enrollment.application.port.MatriculaResumen;
import backend.enrollment.infrastructure.persistence.MatriculaConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MatriculaConsultaRepositoryAdapter implements MatriculaConsultaRepository {
    private final MatriculaConsultaJpaRepository jpa;

    public MatriculaConsultaRepositoryAdapter(MatriculaConsultaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<MatriculaResumen> listarPorAnio(UUID inst, UUID anioId) {
        return jpa.listarPorAnio(inst.toString(), anioId.toString());
    }
}