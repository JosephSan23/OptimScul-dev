package backend.academic.application.port.Asistencia;

import backend.academic.application.port.MarcaAsistenciaFila;
import backend.academic.application.port.SesionFechaFila;
import java.util.List;
import java.util.UUID;

public interface MatrizAsistenciaConsultaRepository {
    List<SesionFechaFila> sesionesDeCarga(UUID cargaId);
    List<MarcaAsistenciaFila> marcasDeCarga(UUID cargaId);
}