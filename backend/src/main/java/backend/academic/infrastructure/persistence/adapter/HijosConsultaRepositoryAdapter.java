package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.Acudiente.*;
import backend.academic.infrastructure.persistence.HijosConsultaJpaRepository;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class HijosConsultaRepositoryAdapter implements HijosConsultaRepository {
    private final HijosConsultaJpaRepository jpa;

    public HijosConsultaRepositoryAdapter(HijosConsultaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<HijoResumen> hijosDeAcudiente(UUID acudienteId) {
        return jpa.hijosDeAcudiente(acudienteId.toString());
    }
}