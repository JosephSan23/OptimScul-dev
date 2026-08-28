-- Acelera la búsqueda de calificaciones por estudiante + actividades (el nuevo query batch)
CREATE INDEX IF NOT EXISTS idx_calificacion_estudiante_actividad
    ON optimscul.calificacion_actividad (estudiante_id, actividad_id);

-- Acelera la búsqueda de actividades por carga + periodo
CREATE INDEX IF NOT EXISTS idx_actividad_carga_periodo
    ON optimscul.actividad_academica (carga_academica_id, periodo_academico_id);