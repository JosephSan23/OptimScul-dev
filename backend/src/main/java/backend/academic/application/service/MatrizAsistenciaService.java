package backend.academic.application.service;

import backend.academic.application.port.Asistencia.MatrizAsistenciaConsultaRepository;
import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.MarcaAsistenciaFila;
import backend.academic.application.port.SesionFechaFila;
import backend.academic.domain.model.CargaAcademica;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatrizAsistenciaService {

    public record Sesion(UUID sesionId, String fecha) {
    }

    public record FilaEstudiante(UUID estudianteId, String codigoEstudiante, String nombre,
            String numeroDocumento, List<String> marcas) {
    }

    public record Matriz(List<Sesion> sesiones, List<FilaEstudiante> estudiantes) {
    }

    private final MatrizAsistenciaConsultaRepository consulta;
    private final DocenteConsultaRepository docenteConsulta; // reusa estudiantesDeGrupo

    public MatrizAsistenciaService(MatrizAsistenciaConsultaRepository consulta,
            DocenteConsultaRepository docenteConsulta) {
        this.consulta = consulta;
        this.docenteConsulta = docenteConsulta;
    }

    public Matriz construir(CargaAcademica carga) {
        List<SesionFechaFila> sesionesRaw = consulta.sesionesDeCarga(carga.getId());
        List<Sesion> sesiones = sesionesRaw.stream()
                .map(s -> new Sesion(s.getSesionId(), s.getFecha())).toList();

        // índice estudiante -> (sesion -> tipo)
        Map<UUID, Map<UUID, String>> idx = consulta.marcasDeCarga(carga.getId()).stream()
                .collect(Collectors.groupingBy(MarcaAsistenciaFila::getEstudianteId,
                        Collectors.toMap(MarcaAsistenciaFila::getSesionId, MarcaAsistenciaFila::getTipo, (a, b) -> a)));

        List<FilaEstudiante> filas = docenteConsulta.estudiantesDeGrupo(carga.getGrupoId()).stream()
                .map(e -> {
                    Map<UUID, String> suyas = idx.getOrDefault(e.getEstudianteId(), Map.of());
                    List<String> marcas = sesiones.stream()
                            .map(s -> suyas.getOrDefault(s.sesionId(), "")) // "" = sin registro ese día
                            .toList();
                    return new FilaEstudiante(e.getEstudianteId(), e.getCodigoEstudiante(),
                            e.getNombre(), e.getNumeroDocumento(), marcas);
                }).toList();

        return new Matriz(sesiones, filas);
    }
}