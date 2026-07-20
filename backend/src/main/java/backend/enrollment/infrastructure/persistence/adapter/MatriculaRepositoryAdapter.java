package backend.enrollment.infrastructure.persistence.adapter;

import backend.enrollment.application.port.MatriculaRepository;
import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.domain.model.Matricula;
import backend.enrollment.infrastructure.persistence.MatriculaJpaRepository;
import backend.enrollment.infrastructure.persistence.mapper.MatriculaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MatriculaRepositoryAdapter implements MatriculaRepository {
    private final MatriculaJpaRepository jpa;
    private final MatriculaMapper mapper;

    public MatriculaRepositoryAdapter(MatriculaJpaRepository jpa, MatriculaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Matricula save(Matricula e) { return mapper.toDomain(jpa.save(mapper.toEntity(e))); }

    @Override
    public Optional<Matricula> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }

    @Override
    public List<Matricula> findAll() { return jpa.findAll().stream().map(mapper::toDomain).toList(); }

    @Override
    public void deleteById(UUID id) { jpa.deleteById(id); }

    @Override
    public Optional<Matricula> findByInstitucionIdAndEstudianteIdAndAnioLectivoId(UUID inst, UUID est, UUID anio) {
        return jpa.findByInstitucionIdAndEstudianteIdAndAnioLectivoId(inst, est, anio).map(mapper::toDomain);
    }

    @Override
    public long contarMatriculadosEnGrupo(UUID grupoId) {
        return jpa.countByGrupoIdAndEstado(grupoId, EstadoMatricula.MATRICULADO);
    }
}