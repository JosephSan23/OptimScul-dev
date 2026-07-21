package backend.academic.infrastructure.persistence.adapter;

import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.EstudianteDeClase;
import backend.academic.application.port.MiClaseResumen;
import backend.academic.application.port.Asistencia.ReporteAsistenciaFila;
import backend.academic.infrastructure.persistence.DocenteConsultaJpaRepository;
import backend.academic.application.port.Horario.HorarioResumen;


import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class DocenteConsultaRepositoryAdapter implements DocenteConsultaRepository {
    private final DocenteConsultaJpaRepository dojpa;


    public DocenteConsultaRepositoryAdapter(DocenteConsultaJpaRepository dojpa) 
    { this.dojpa = dojpa;}

    @Override
    public List<MiClaseResumen> misClases(UUID profesorId, UUID anioId) {
        return dojpa.misClases(profesorId.toString(), anioId.toString());
    }

    @Override
    public List<HorarioResumen> miHorario(UUID profesorId, UUID anioId) {
        return dojpa.miHorario(profesorId.toString(), anioId.toString());
    }

    @Override
    public List<EstudianteDeClase> estudiantesDeGrupo(UUID grupoId) {
        return dojpa.estudiantesDeGrupo(grupoId.toString());
    }

    @Override
    public List<ReporteAsistenciaFila> reporteAsistencia(UUID cargaId) {
        return dojpa.reporteAsistencia(cargaId.toString());
    }
}