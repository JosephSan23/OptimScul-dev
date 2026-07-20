package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.Horario.HorarioConsultaRepository;
import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.infrastructure.persistence.HorarioConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class HorarioConsultaRepositoryAdapter implements HorarioConsultaRepository {
    private final HorarioConsultaJpaRepository jpa;

    public HorarioConsultaRepositoryAdapter(HorarioConsultaJpaRepository jpa) { this.jpa = jpa; }

    @Override
    public List<HorarioResumen> listarPorGrupo(UUID inst, UUID grupoId) {
        return jpa.listarPorGrupo(inst.toString(), grupoId.toString());
    }

    @Override
    public boolean existeChoqueProfesor(UUID profesorId, UUID anioId, String dia,
                                        String horaInicio, String horaFin, UUID excluirId) {
        return jpa.contarChoquesProfesor(profesorId.toString(), anioId.toString(), dia,
                horaInicio, horaFin, excluirId.toString()) > 0;
    }

    @Override
    public boolean existeChoqueGrupo(UUID grupoId, UUID anioId, String dia,
                                     String horaInicio, String horaFin, UUID excluirId) {
        return jpa.contarChoquesGrupo(grupoId.toString(), anioId.toString(), dia,
                horaInicio, horaFin, excluirId.toString()) > 0;
    }
}