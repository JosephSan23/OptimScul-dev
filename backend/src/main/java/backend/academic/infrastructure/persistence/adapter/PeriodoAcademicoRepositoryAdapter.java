package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.PeriodoAcademicoRepository;
import backend.academic.domain.model.PeriodoAcademico;
import backend.academic.infrastructure.persistence.PeriodoAcademicoJpaRepository;
import backend.academic.infrastructure.persistence.mapper.PeriodoAcademicoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PeriodoAcademicoRepositoryAdapter implements PeriodoAcademicoRepository {
    private final PeriodoAcademicoJpaRepository jpa;
    private final PeriodoAcademicoMapper mapper;

    public PeriodoAcademicoRepositoryAdapter(PeriodoAcademicoJpaRepository jpa, PeriodoAcademicoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public PeriodoAcademico save(PeriodoAcademico e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<PeriodoAcademico> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<PeriodoAcademico> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<PeriodoAcademico> findByAnioLectivoId(UUID anioId) {
        return jpa.findByAnioLectivoId(anioId).stream().map(mapper::toDomain).toList();
    }
}