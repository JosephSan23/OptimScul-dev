package backend.community.infrastructure.persistence.adapter;

import backend.community.application.port.AcudienteConsultaRepository;
import backend.community.application.port.AcudienteDeEstudiante;
import backend.community.infrastructure.persistence.AcudienteConsultaJpaRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class AcudienteConsultaRepositoryAdapter implements AcudienteConsultaRepository {
    private final AcudienteConsultaJpaRepository jpa;

    public AcudienteConsultaRepositoryAdapter(AcudienteConsultaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<AcudienteDeEstudiante> listarPorEstudiante(UUID estudianteId) {
        return jpa.listarPorEstudiante(estudianteId.toString());
    }
}