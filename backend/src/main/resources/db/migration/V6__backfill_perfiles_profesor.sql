INSERT INTO optimscul.profesor (institucion_id, persona_id, codigo_profesor, fecha_vinculacion, estado)
SELECT ur.institucion_id,
       u.persona_id,
       'DOC-' || p.numero_documento,
       CURRENT_DATE,
       'ACTIVO'::optimscul.estado_profesor_enum
FROM optimscul.usuario_rol ur
JOIN optimscul.rol r     ON r.id = ur.rol_id AND r.codigo = 'DOCENTE'
JOIN optimscul.usuario u ON u.id = ur.usuario_id
JOIN optimscul.persona p ON p.id = u.persona_id
WHERE ur.activo = true
  AND NOT EXISTS (
      SELECT 1 FROM optimscul.profesor pr
      WHERE pr.institucion_id = ur.institucion_id
        AND pr.persona_id = u.persona_id
  );