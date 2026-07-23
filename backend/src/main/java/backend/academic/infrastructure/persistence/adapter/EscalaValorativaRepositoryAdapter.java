package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.EscalaValorativaRepository;
import backend.academic.domain.model.EscalaValorativa;
import backend.academic.infrastructure.persistence.EscalaValorativaJpaRepository;
import backend.academic.infrastructure.persistence.mapper.EscalaValorativaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EscalaValorativaRepositoryAdapter implements EscalaValorativaRepository {
    private final EscalaValorativaJpaRepository jpa;
    private final EscalaValorativaMapper mapper;

    public EscalaValorativaRepositoryAdapter(EscalaValorativaJpaRepository jpa, EscalaValorativaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public EscalaValorativa save(EscalaValorativa e) {
        return mapper.toDomain(jpa.save(mapper.toEntity(e)));
    }

    @Override
    public Optional<EscalaValorativa> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<EscalaValorativa> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<EscalaValorativa> findByInstitucionId(UUID inst) {
        return jpa.findByInstitucionIdOrderByOrdenAsc(inst).stream().map(mapper::toDomain).toList();
    }
    
}
