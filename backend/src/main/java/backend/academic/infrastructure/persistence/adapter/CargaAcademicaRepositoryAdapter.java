package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.EstadoCargaAcademica;
import backend.academic.infrastructure.persistence.CargaAcademicaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.CargaAcademicaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CargaAcademicaRepositoryAdapter implements CargaAcademicaRepository {
    private final CargaAcademicaJpaRepository jpa;
    private final CargaAcademicaMapper mapper;

    public CargaAcademicaRepositoryAdapter(CargaAcademicaJpaRepository jpa, CargaAcademicaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public CargaAcademica save(CargaAcademica e) { return mapper.toDomain(jpa.save(mapper.toEntity(e))); }

    @Override
    public Optional<CargaAcademica> findById(UUID id) { return jpa.findById(id).map(mapper::toDomain); }

    @Override
    public List<CargaAcademica> findAll() { return jpa.findAll().stream().map(mapper::toDomain).toList(); }

    @Override
    public void deleteById(UUID id) { jpa.deleteById(id); }

    @Override
    public boolean existsActiva(UUID inst, UUID anioId, UUID grupoId, UUID asignaturaId) {
        return jpa.existsByInstitucionIdAndAnioLectivoIdAndGrupoIdAndAsignaturaIdAndEstado(
                inst, anioId, grupoId, asignaturaId, EstadoCargaAcademica.ACTIVA);
    }
}