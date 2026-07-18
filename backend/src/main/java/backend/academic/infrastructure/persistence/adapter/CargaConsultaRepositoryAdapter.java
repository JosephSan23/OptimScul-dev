package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.CargaAcademica.CargaConsultaRepository;
import backend.academic.application.port.CargaAcademica.CargaResumen;
import backend.academic.infrastructure.persistence.CargaConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CargaConsultaRepositoryAdapter implements CargaConsultaRepository {
    private final CargaConsultaJpaRepository jpa;

    public CargaConsultaRepositoryAdapter(CargaConsultaJpaRepository jpa) { this.jpa = jpa; }

    @Override
    public List<CargaResumen> listarPorAnio(UUID inst, UUID anioId) {
        return jpa.listarPorAnio(inst.toString(), anioId.toString());
    }
}