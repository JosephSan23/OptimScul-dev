package backend.community.application.port;

import java.util.UUID;

public interface AcudienteDeEstudiante {
    UUID getVinculoId();
    UUID getAcudienteId();
    String getParentesco();
    Boolean getEsPrincipal();
    Boolean getAutorizadoRecogida();
    Boolean getAutorizadoInfoAcademica();
    String getPrimerNombre();
    String getPrimerApellido();
    String getNumeroDocumento();
    String getCorreo();
    String getUsername();
    String getOcupacion();
    String getEmpresa();
    String getEstado();
}