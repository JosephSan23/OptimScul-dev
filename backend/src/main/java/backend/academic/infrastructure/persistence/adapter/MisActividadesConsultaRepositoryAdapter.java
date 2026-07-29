package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.MisActividadesConsultaRepository;
import backend.academic.infrastructure.persistence.MisActividadesJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MisActividadesConsultaRepositoryAdapter implements MisActividadesConsultaRepository {

    private final MisActividadesJpaRepository jpa;

    public MisActividadesConsultaRepositoryAdapter(MisActividadesJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<MiActividadFila> listar(UUID estudianteId, UUID anioId) {
        return jpa.misActividades(estudianteId, anioId).stream()
                .map(r -> new MiActividadFila(
                        r.getActividadId(),
                        r.getTitulo(),
                        r.getTipo(),
                        r.getFechaEntrega(),
                        r.getFechaCierre(),
                        r.getNotaMaxima(),
                        r.getAsignatura(),
                        r.getCargaId(),
                        r.getEstadoEntrega() != null ? r.getEstadoEntrega() : "PENDIENTE",
                        r.getNotaObtenida()))
                .toList();
    }
}