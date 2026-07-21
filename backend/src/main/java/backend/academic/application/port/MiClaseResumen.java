package backend.academic.application.port;

import java.util.UUID;

public interface MiClaseResumen {
    UUID getCargaId();
    UUID getGrupoId();
    UUID getAnioLectivoId();
    String getAsignaturaNombre();
    String getAsignaturaCodigo();
    String getGrupoNombre();
    String getGrupoCodigo();
    String getGradoNombre();
    Short getIntensidadHorariaSemanal();
    Long getTotalEstudiantes();
}