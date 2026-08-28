-- ============================================================================
-- SEED COMPLETO MULTIINSTITUCIÓN (para pruebas de carga)
--
-- Crea DESDE CERO 2 instituciones completas, cada una con:
--   sede · año lectivo · periodo · grado · grupo · N estudiantes (login con Load1234)
-- y matricula a los estudiantes. Al final imprime los anioId/periodoId que debes
-- copiar en pruebas-carga/lib/instituciones.json.
--
-- Los usernames quedan: loadA-00001..  y  loadB-00001..  (ya coinciden con el JSON).
--
-- CÓMO USARLO:
--   1) DRY-RUN (probar sin guardar): cambia al final COMMIT; por ROLLBACK; y ejecútalo.
--      Si no hay error, vas bien. (El SELECT final saldrá vacío en dry-run: es normal.)
--   2) REAL: deja COMMIT; y ejecútalo. Copia los IDs que muestra el SELECT final.
--   Es re-ejecutable: no duplica instituciones ni usuarios que ya existan.
-- ============================================================================
BEGIN;

DO $$
DECLARE
  -- ── PARÁMETROS EDITABLES ────────────────────────────────────────────────────
  v_estudiantes_por_inst int := 250;  -- estudiantes por institución
  v_anio                 int := 2026; -- año lectivo
  -- ────────────────────────────────────────────────────────────────────────────
  v_hash text := '$2b$10$/AO8EU8pzDK8MI3KA7VLPOP29jGKBJECracC9UpVktCt12mN.tBq.'; -- BCrypt de 'Load1234'
  v_rol_estudiante uuid;
  inst RECORD;
  v_inst_id uuid; v_sede_id uuid; v_anio_id uuid; v_periodo_id uuid; v_grado_id uuid; v_grupo_id uuid;
  i int; v_persona_id uuid; v_usuario_id uuid; v_estudiante_id uuid;
  v_user text; v_doc text; v_cod text;
BEGIN
  SELECT id INTO v_rol_estudiante FROM optimscul.rol WHERE codigo = 'ESTUDIANTE' LIMIT 1;
  IF v_rol_estudiante IS NULL THEN
    RAISE EXCEPTION 'No existe el rol con codigo ESTUDIANTE (revisa la tabla optimscul.rol)';
  END IF;

  -- Definición de las instituciones a crear: (codigo, nombre, prefijo_username)
  FOR inst IN SELECT * FROM (VALUES
        ('LOADTEST-A', 'Institucion Carga A', 'loadA-'),
        ('LOADTEST-B', 'Institucion Carga B', 'loadB-')
      ) AS t(codigo, nombre, prefijo)
  LOOP
    -- ── Institución ──
    SELECT id INTO v_inst_id FROM optimscul.institucion WHERE codigo = inst.codigo;
    IF v_inst_id IS NULL THEN
      v_inst_id := gen_random_uuid();
      INSERT INTO optimscul.institucion (id, codigo, nombre)
        VALUES (v_inst_id, inst.codigo, inst.nombre);
    END IF;

    -- ── Sede principal ──
    SELECT id INTO v_sede_id FROM optimscul.sede WHERE institucion_id = v_inst_id LIMIT 1;
    IF v_sede_id IS NULL THEN
      v_sede_id := gen_random_uuid();
      INSERT INTO optimscul.sede (id, institucion_id, codigo, nombre, principal)
        VALUES (v_sede_id, v_inst_id, 'SEDE-1', 'Sede Principal', true);
    END IF;

    -- ── Configuración académica (la exige el endpoint de notas; usa valores por defecto) ──
    IF NOT EXISTS (SELECT 1 FROM optimscul.configuracion_academica WHERE institucion_id = v_inst_id) THEN
      INSERT INTO optimscul.configuracion_academica (id, institucion_id)
        VALUES (gen_random_uuid(), v_inst_id);
    END IF;

    -- ── Año lectivo (marcado como actual) ──
    SELECT id INTO v_anio_id FROM optimscul.anio_lectivo
      WHERE institucion_id = v_inst_id AND anio = v_anio LIMIT 1;
    IF v_anio_id IS NULL THEN
      v_anio_id := gen_random_uuid();
      INSERT INTO optimscul.anio_lectivo (id, institucion_id, anio, nombre, fecha_inicio, fecha_fin, es_actual)
        VALUES (v_anio_id, v_inst_id, v_anio, 'Año ' || v_anio,
                make_date(v_anio, 1, 15), make_date(v_anio, 11, 30), true);
    END IF;

    -- ── Periodo 1 ──
    SELECT id INTO v_periodo_id FROM optimscul.periodo_academico
      WHERE anio_lectivo_id = v_anio_id ORDER BY numero LIMIT 1;
    IF v_periodo_id IS NULL THEN
      v_periodo_id := gen_random_uuid();
      INSERT INTO optimscul.periodo_academico (id, institucion_id, anio_lectivo_id, numero, nombre, fecha_inicio, fecha_fin)
        VALUES (v_periodo_id, v_inst_id, v_anio_id, 1, 'Periodo 1',
                make_date(v_anio, 1, 15), make_date(v_anio, 4, 15));
    END IF;

    -- ── Grado ──
    SELECT id INTO v_grado_id FROM optimscul.grado WHERE institucion_id = v_inst_id LIMIT 1;
    IF v_grado_id IS NULL THEN
      v_grado_id := gen_random_uuid();
      INSERT INTO optimscul.grado (id, institucion_id, codigo, nombre, nivel, orden)
        VALUES (v_grado_id, v_inst_id, 'G-6', 'Sexto', 'SECUNDARIA', 6);
    END IF;

    -- ── Grupo ──
    SELECT id INTO v_grupo_id FROM optimscul.grupo
      WHERE institucion_id = v_inst_id AND anio_lectivo_id = v_anio_id LIMIT 1;
    IF v_grupo_id IS NULL THEN
      v_grupo_id := gen_random_uuid();
      INSERT INTO optimscul.grupo (id, institucion_id, anio_lectivo_id, sede_id, grado_id, codigo, nombre)
        VALUES (v_grupo_id, v_inst_id, v_anio_id, v_sede_id, v_grado_id, 'G6-A', 'Sexto A');
    END IF;

    -- ── Estudiantes ──
    FOR i IN 1..v_estudiantes_por_inst LOOP
      v_user := inst.prefijo || lpad(i::text, 5, '0');
      CONTINUE WHEN EXISTS (SELECT 1 FROM optimscul.usuario WHERE username = v_user);

      v_doc := 'LT' || substr(inst.codigo, 10) || lpad(i::text, 6, '0'); -- único entre instituciones
      v_cod := upper(inst.prefijo) || 'EST' || lpad(i::text, 5, '0');
      v_persona_id    := gen_random_uuid();
      v_usuario_id    := gen_random_uuid();
      v_estudiante_id := gen_random_uuid();

      INSERT INTO optimscul.persona (id, tipo_documento, numero_documento, primer_nombre, primer_apellido, pais)
        VALUES (v_persona_id, 'TI', v_doc, 'Carga', 'Est' || i, 'Colombia');

      INSERT INTO optimscul.usuario (id, persona_id, username, password_hash, tipo_contexto, estado,
                                     requiere_cambio_password, email_verificado)
        VALUES (v_usuario_id, v_persona_id, v_user, v_hash, 'INSTITUCION', 'ACTIVO', false, true);

      INSERT INTO optimscul.usuario_institucion (id, usuario_id, institucion_id, es_principal, activo)
        VALUES (gen_random_uuid(), v_usuario_id, v_inst_id, true, true);

      INSERT INTO optimscul.usuario_rol (id, usuario_id, institucion_id, rol_id, activo)
        VALUES (gen_random_uuid(), v_usuario_id, v_inst_id, v_rol_estudiante, true);

      INSERT INTO optimscul.estudiante (id, institucion_id, persona_id, codigo_estudiante, estado)
        VALUES (v_estudiante_id, v_inst_id, v_persona_id, v_cod, 'ACTIVO');

      INSERT INTO optimscul.matricula (id, institucion_id, estudiante_id, anio_lectivo_id, grupo_id, codigo_matricula, tipo, fecha_matricula)
        VALUES (gen_random_uuid(), v_inst_id, v_estudiante_id, v_anio_id, v_grupo_id, v_cod, 'NUEVA', CURRENT_DATE);
    END LOOP;

    RAISE NOTICE 'OK % (%): anio_id=%  periodo_id=%', inst.nombre, inst.codigo, v_anio_id, v_periodo_id;
  END LOOP;
END $$;

-- Cambia a ROLLBACK; para un dry-run de prueba; deja COMMIT; para guardar de verdad.
COMMIT;

-- ── IDs para copiar en pruebas-carga/lib/instituciones.json ──
SELECT
    i.codigo,
    i.nombre,
    al.id AS anio_id,
    (SELECT p.id FROM optimscul.periodo_academico p
      WHERE p.anio_lectivo_id = al.id ORDER BY p.numero LIMIT 1) AS periodo_id
FROM optimscul.institucion i
JOIN optimscul.anio_lectivo al ON al.institucion_id = i.id
WHERE i.codigo LIKE 'LOADTEST-%'
ORDER BY i.codigo;
