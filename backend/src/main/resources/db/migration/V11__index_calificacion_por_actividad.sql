-- Soporta la consulta batch findByActividadIdIn (consolidado del docente):
-- traer todas las calificaciones de un conjunto de actividades de una sola vez.
CREATE INDEX IF NOT EXISTS idx_calificacion_actividad_id
    ON optimscul.calificacion_actividad (actividad_id);
