package backend.academic.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import backend.academic.application.port.SesionClaseRepository;
import backend.academic.domain.model.SesionClase;
import backend.academic.infrastructure.persistence.mapper.SesionClaseMapper;
import backend.academic.infrastructure.persistence.SesionClaseJpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SesionClaseRepositoryAdapter implements SesionClaseRepository {
    private final SesionClaseJpaRepository jpa;
    private final SesionClaseMapper mapper;

    public SesionClaseRepositoryAdapter(SesionClaseJpaRepository jpa, SesionClaseMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public SesionClase save(SesionClase s) {
        return mapper.toDomain(jpa.save(mapper.toEntity(s)));
    }

    @Override
    public Optional<SesionClase> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<SesionClase> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public Optional<SesionClase> findByCargaAcademicaIdAndFecha(UUID cargaId, LocalDate fecha) {
        return jpa.findByCargaAcademicaIdAndFecha(cargaId, fecha).map(mapper::toDomain);
    }
}
