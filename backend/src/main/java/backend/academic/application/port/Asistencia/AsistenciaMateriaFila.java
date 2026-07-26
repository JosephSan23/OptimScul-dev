package backend.academic.application.port.Asistencia;

public interface AsistenciaMateriaFila {
    String getAsignaturaNombre();
    Long getPresente();
    Long getAusente();
    Long getTarde();
    Long getJustificada();
    Long getTotal();
}