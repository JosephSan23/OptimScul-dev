package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.infrastructure.persistence.ActividadAcademicaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.ActividadAcademicaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ActividadAcademicaRepositoryAdapter implements ActividadAcademicaRepository {
    private final ActividadAcademicaJpaRepository jpa;
    private final ActividadAcademicaMapper mapper;

    public ActividadAcademicaRepositoryAdapter(ActividadAcademicaJpaRepository jpa, ActividadAcademicaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ActividadAcademica save(ActividadAcademica e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<ActividadAcademica> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ActividadAcademica> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<ActividadAcademica> findByCargaYPeriodo(UUID cargaId, UUID periodoId) {
        return jpa.findByCargaAcademicaIdAndPeriodoAcademicoIdOrderByFechaEntregaAsc(cargaId, periodoId)
                .stream().map(mapper::toDomain).toList();
    }
}