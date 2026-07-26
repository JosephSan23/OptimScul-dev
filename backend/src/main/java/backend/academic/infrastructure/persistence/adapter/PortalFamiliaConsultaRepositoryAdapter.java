package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.Asistencia.AsistenciaMateriaFila;
import backend.academic.application.port.Asistencia.PortalFamiliaConsultaRepository;
import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.infrastructure.persistence.PortalFamiliaConsultaJpaRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class PortalFamiliaConsultaRepositoryAdapter implements PortalFamiliaConsultaRepository {
    private final PortalFamiliaConsultaJpaRepository jpa;

    public PortalFamiliaConsultaRepositoryAdapter(PortalFamiliaConsultaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<HorarioResumen> horarioDeGrupo(UUID grupoId, UUID anioId) {
        return jpa.horarioDeGrupo(grupoId.toString(), anioId.toString());
    }

    @Override
    public List<AsistenciaMateriaFila> asistenciaPorMateria(UUID estudianteId, UUID anioId) {
        return jpa.asistenciaPorMateria(estudianteId.toString(), anioId.toString());
    }
}