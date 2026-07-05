-- Rol para usuarios recién registrados que aún no pertenecen a ninguna institución.
-- Un "visitante" tiene cuenta pero todavía no ha postulado ni solicitado nada.

INSERT INTO optimscul.rol (codigo, nombre, descripcion, es_sistema, activo)
VALUES (
    'VISITANTE',
    'Visitante',
    'Usuario registrado sin institución. Puede postular estudiantes o solicitar vincular una institución.',
    true,
    true
);