package backend.busqueda.application;

import java.util.UUID;

/** Proyección de un estudiante para el buscador global (estudiante + persona). */
public interface EstudianteBusquedaResumen {
    UUID getId();
    String getNombre();
    String getCodigoEstudiante();
    String getNumeroDocumento();
}
