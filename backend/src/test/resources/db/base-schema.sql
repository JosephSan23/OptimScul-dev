--
-- PostgreSQL database dump
--


-- Dumped from database version 16.14
-- Dumped by pg_dump version 18.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: optimscul; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA optimscul;


--
-- Name: accion_auditoria_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.accion_auditoria_enum AS ENUM (
    'CREAR',
    'ACTUALIZAR',
    'ELIMINAR',
    'ANULAR',
    'APROBAR',
    'RECHAZAR',
    'LOGIN',
    'LOGOUT',
    'OTRA'
);


--
-- Name: canal_notificacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.canal_notificacion_enum AS ENUM (
    'IN_APP',
    'EMAIL',
    'SMS',
    'WHATSAPP'
);


--
-- Name: canal_postulacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.canal_postulacion_enum AS ENUM (
    'WEB',
    'PRESENCIAL',
    'TELEFONICO',
    'REFERIDO',
    'OTRO'
);


--
-- Name: categoria_concepto_cobro_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.categoria_concepto_cobro_enum AS ENUM (
    'MATRICULA',
    'PENSION',
    'TRANSPORTE',
    'ALIMENTACION',
    'UNIFORME',
    'CERTIFICADO',
    'DERECHO_GRADO',
    'MATERIAL',
    'OTRO'
);


--
-- Name: dia_semana_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.dia_semana_enum AS ENUM (
    'LUNES',
    'MARTES',
    'MIERCOLES',
    'JUEVES',
    'VIERNES',
    'SABADO',
    'DOMINGO'
);


--
-- Name: estado_actividad_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_actividad_enum AS ENUM (
    'BORRADOR',
    'PUBLICADA',
    'CERRADA',
    'ANULADA'
);


--
-- Name: estado_acudiente_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_acudiente_enum AS ENUM (
    'ACTIVO',
    'INACTIVO'
);


--
-- Name: estado_anio_lectivo_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_anio_lectivo_enum AS ENUM (
    'PLANEACION',
    'ACTIVO',
    'CERRADO',
    'CANCELADO'
);


--
-- Name: estado_beneficio_estudiante_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_beneficio_estudiante_enum AS ENUM (
    'ACTIVO',
    'SUSPENDIDO',
    'VENCIDO',
    'CANCELADO'
);


--
-- Name: estado_carga_academica_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_carga_academica_enum AS ENUM (
    'ACTIVA',
    'INACTIVA',
    'FINALIZADA',
    'CANCELADA'
);


--
-- Name: estado_cuenta_cobro_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_cuenta_cobro_enum AS ENUM (
    'PENDIENTE',
    'PAGADA',
    'PAGADA_PARCIAL',
    'VENCIDA',
    'ANULADA'
);


--
-- Name: estado_entrega_actividad_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_entrega_actividad_enum AS ENUM (
    'PENDIENTE',
    'ENTREGADA',
    'ENTREGADA_TARDE',
    'NO_ENTREGADA',
    'CALIFICADA'
);


--
-- Name: estado_estudiante_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_estudiante_enum AS ENUM (
    'ACTIVO',
    'RETIRADO',
    'GRADUADO',
    'INACTIVO'
);


--
-- Name: estado_grupo_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_grupo_enum AS ENUM (
    'ACTIVO',
    'INACTIVO',
    'CERRADO'
);


--
-- Name: estado_institucion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_institucion_enum AS ENUM (
    'ACTIVA',
    'INACTIVA',
    'SUSPENDIDA',
    'PRUEBA'
);


--
-- Name: estado_matricula_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_matricula_enum AS ENUM (
    'PREMATRICULA',
    'MATRICULADO',
    'CANCELADO',
    'RETIRADO',
    'FINALIZADO'
);


--
-- Name: estado_notificacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_notificacion_enum AS ENUM (
    'PENDIENTE',
    'ENVIADA',
    'LEIDA',
    'FALLIDA'
);


--
-- Name: estado_observacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_observacion_enum AS ENUM (
    'ABIERTA',
    'EN_SEGUIMIENTO',
    'CERRADA',
    'ANULADA'
);


--
-- Name: estado_periodo_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_periodo_enum AS ENUM (
    'PLANEADO',
    'ACTIVO',
    'CERRADO',
    'ANULADO'
);


--
-- Name: estado_postulacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_postulacion_enum AS ENUM (
    'RECIBIDA',
    'EN_REVISION',
    'DOCUMENTOS_PENDIENTES',
    'ENTREVISTA_PROGRAMADA',
    'APROBADA',
    'RECHAZADA',
    'CONVERTIDA'
);


--
-- Name: estado_profesor_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_profesor_enum AS ENUM (
    'ACTIVO',
    'RETIRADO',
    'INACTIVO'
);


--
-- Name: estado_registro_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_registro_enum AS ENUM (
    'ACTIVO',
    'INACTIVO'
);


--
-- Name: estado_sesion_clase_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_sesion_clase_enum AS ENUM (
    'PROGRAMADA',
    'DICTADA',
    'CANCELADA',
    'REPROGRAMADA'
);


--
-- Name: estado_usuario_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.estado_usuario_enum AS ENUM (
    'ACTIVO',
    'INACTIVO',
    'BLOQUEADO',
    'PENDIENTE_ACTIVACION'
);


--
-- Name: metodo_pago_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.metodo_pago_enum AS ENUM (
    'EFECTIVO',
    'TRANSFERENCIA',
    'TARJETA',
    'PSE',
    'NEQUI',
    'DAVIPLATA',
    'OTRO'
);


--
-- Name: modo_aplicacion_beneficio_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.modo_aplicacion_beneficio_enum AS ENUM (
    'PORCENTAJE',
    'VALOR_FIJO'
);


--
-- Name: modulo_documento_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.modulo_documento_enum AS ENUM (
    'POSTULACION',
    'ESTUDIANTE',
    'ACUDIENTE',
    'MATRICULA',
    'PAGO',
    'CUENTA_COBRO',
    'OBSERVACION',
    'ACTIVIDAD',
    'INSTITUCION',
    'OTRO'
);


--
-- Name: nivel_academico_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.nivel_academico_enum AS ENUM (
    'PREESCOLAR',
    'PRIMARIA',
    'SECUNDARIA',
    'MEDIA',
    'TECNICA',
    'OTRO'
);


--
-- Name: resultado_academico_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.resultado_academico_enum AS ENUM (
    'APROBADO',
    'REPROBADO',
    'PENDIENTE',
    'PROMOVIDO_ANTICIPADO'
);


--
-- Name: severidad_observacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.severidad_observacion_enum AS ENUM (
    'BAJA',
    'MEDIA',
    'ALTA',
    'CRITICA'
);


--
-- Name: sexo_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.sexo_enum AS ENUM (
    'MASCULINO',
    'FEMENINO',
    'OTRO',
    'NO_ESPECIFICA'
);


--
-- Name: tipo_actividad_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_actividad_enum AS ENUM (
    'TAREA',
    'QUIZ',
    'EXAMEN',
    'TRABAJO',
    'EXPOSICION',
    'PROYECTO',
    'RECUPERACION',
    'OTRA'
);


--
-- Name: tipo_asistencia_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_asistencia_enum AS ENUM (
    'PRESENTE',
    'AUSENTE',
    'TARDE',
    'JUSTIFICADA'
);


--
-- Name: tipo_beneficio_financiero_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_beneficio_financiero_enum AS ENUM (
    'DESCUENTO',
    'BECA',
    'SUBSIDIO',
    'EXONERACION'
);


--
-- Name: tipo_contexto_usuario_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_contexto_usuario_enum AS ENUM (
    'PLATAFORMA',
    'INSTITUCION'
);


--
-- Name: tipo_documento_persona_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_documento_persona_enum AS ENUM (
    'RC',
    'TI',
    'CC',
    'CE',
    'PASAPORTE',
    'NIT',
    'OTRO'
);


--
-- Name: tipo_institucion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_institucion_enum AS ENUM (
    'COLEGIO',
    'JARDIN',
    'INSTITUTO',
    'ACADEMIA',
    'OTRA'
);


--
-- Name: tipo_matricula_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_matricula_enum AS ENUM (
    'NUEVA',
    'RENOVACION',
    'TRASLADO',
    'REINTEGRO'
);


--
-- Name: tipo_notificacion_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_notificacion_enum AS ENUM (
    'ACADEMICA',
    'FINANCIERA',
    'ADMISIONES',
    'CONVIVENCIA',
    'SISTEMA',
    'GENERAL'
);


--
-- Name: tipo_parentesco_enum; Type: TYPE; Schema: optimscul; Owner: -
--

CREATE TYPE optimscul.tipo_parentesco_enum AS ENUM (
    'MADRE',
    'PADRE',
    'ABUELA',
    'ABUELO',
    'TIA',
    'TIO',
    'HERMANA',
    'HERMANO',
    'PRIMA',
    'PRIMO',
    'ACUDIENTE_LEGAL',
    'OTRO'
);


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: actividad_academica; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.actividad_academica (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    carga_academica_id uuid NOT NULL,
    periodo_academico_id uuid,
    sesion_clase_id uuid,
    tipo optimscul.tipo_actividad_enum DEFAULT 'TAREA'::optimscul.tipo_actividad_enum NOT NULL,
    titulo character varying(150) NOT NULL,
    descripcion text,
    fecha_publicacion timestamp without time zone DEFAULT now() NOT NULL,
    fecha_entrega timestamp without time zone,
    fecha_cierre timestamp without time zone,
    porcentaje numeric(6,2),
    nota_maxima numeric(6,2),
    permite_entrega_tardia boolean DEFAULT false NOT NULL,
    estado optimscul.estado_actividad_enum DEFAULT 'PUBLICADA'::optimscul.estado_actividad_enum NOT NULL,
    creada_por_usuario_id uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: acudiente; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.acudiente (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    persona_id uuid NOT NULL,
    ocupacion character varying(120),
    empresa character varying(150),
    estado optimscul.estado_acudiente_enum DEFAULT 'ACTIVO'::optimscul.estado_acudiente_enum NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: anio_lectivo; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.anio_lectivo (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio smallint NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    fecha_inicio date NOT NULL,
    fecha_fin date NOT NULL,
    estado optimscul.estado_anio_lectivo_enum DEFAULT 'PLANEACION'::optimscul.estado_anio_lectivo_enum NOT NULL,
    es_actual boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_anio_lectivo_fechas CHECK ((fecha_inicio <= fecha_fin))
);


--
-- Name: area_academica; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.area_academica (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion text,
    activa boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: asignatura; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.asignatura (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    area_id uuid,
    codigo character varying(30) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion text,
    intensidad_horaria_semanal smallint,
    requiere_calificacion boolean DEFAULT true NOT NULL,
    requiere_recuperacion boolean DEFAULT true NOT NULL,
    es_comportamiento boolean DEFAULT false NOT NULL,
    activa boolean DEFAULT true NOT NULL,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: asistencia_clase; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.asistencia_clase (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    sesion_clase_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    tipo_asistencia optimscul.tipo_asistencia_enum NOT NULL,
    observacion text,
    justificacion text,
    minutos_tarde smallint,
    registrada_por_usuario_id uuid,
    fecha_registro timestamp without time zone DEFAULT now() NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: auditoria_evento; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.auditoria_evento (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid,
    usuario_id uuid,
    modulo character varying(80) NOT NULL,
    entidad character varying(80) NOT NULL,
    entidad_id uuid,
    accion optimscul.accion_auditoria_enum NOT NULL,
    descripcion text,
    valores_antes jsonb,
    valores_despues jsonb,
    ip character varying(100),
    user_agent text,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: beneficio_financiero; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.beneficio_financiero (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion text,
    tipo optimscul.tipo_beneficio_financiero_enum NOT NULL,
    porcentaje numeric(6,2),
    valor_fijo numeric(12,2),
    concepto_cobro_id uuid,
    activo boolean DEFAULT true NOT NULL,
    requiere_aprobacion boolean DEFAULT false NOT NULL,
    acumulable boolean DEFAULT false NOT NULL,
    prioridad integer DEFAULT 1 NOT NULL,
    modo_aplicacion optimscul.modo_aplicacion_beneficio_enum DEFAULT 'PORCENTAJE'::optimscul.modo_aplicacion_beneficio_enum NOT NULL,
    permite_convivir_con_mejor_beneficio boolean DEFAULT false NOT NULL,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: calificacion_actividad; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.calificacion_actividad (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    actividad_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    entrega_actividad_id uuid,
    nota_obtenida numeric(6,2),
    observacion_docente text,
    calificada_por_usuario_id uuid,
    fecha_calificacion timestamp without time zone,
    es_recuperacion boolean DEFAULT false NOT NULL,
    anulada boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: carga_academica; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.carga_academica (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    profesor_id uuid NOT NULL,
    grupo_id uuid NOT NULL,
    asignatura_id uuid NOT NULL,
    intensidad_horaria_semanal smallint,
    fecha_inicio date,
    fecha_fin date,
    estado optimscul.estado_carga_academica_enum DEFAULT 'ACTIVA'::optimscul.estado_carga_academica_enum NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: concepto_cobro; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.concepto_cobro (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion text,
    categoria optimscul.categoria_concepto_cobro_enum NOT NULL,
    valor_base numeric(12,2),
    es_recurrente boolean DEFAULT false NOT NULL,
    requiere_vencimiento boolean DEFAULT true NOT NULL,
    permite_descuento boolean DEFAULT true NOT NULL,
    permite_recargo boolean DEFAULT true NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: configuracion_academica; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.configuracion_academica (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    usa_periodos boolean DEFAULT true NOT NULL,
    numero_periodos smallint DEFAULT 4 NOT NULL,
    nota_minima_aprobacion numeric(5,2) DEFAULT 3.00 NOT NULL,
    nota_minima numeric(5,2) DEFAULT 0.00 NOT NULL,
    nota_maxima numeric(5,2) DEFAULT 5.00 NOT NULL,
    decimales_nota smallint DEFAULT 2 NOT NULL,
    usa_recuperacion boolean DEFAULT true NOT NULL,
    asistencia_por_clase boolean DEFAULT true NOT NULL,
    maneja_comportamiento boolean DEFAULT true NOT NULL,
    maneja_puestos boolean DEFAULT false NOT NULL,
    porcentaje_inasistencia_reprobacion numeric(5,2),
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: conversacion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.conversacion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    usuario_menor_id uuid NOT NULL,
    usuario_mayor_id uuid NOT NULL,
    created_by uuid NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT ck_conversacion_distintos CHECK ((usuario_menor_id <> usuario_mayor_id))
);


--
-- Name: cuenta_cobro; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.cuenta_cobro (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    concepto_cobro_id uuid NOT NULL,
    anio_lectivo_id uuid,
    periodo_academico_id uuid,
    codigo character varying(40) NOT NULL,
    fecha_emision date NOT NULL,
    fecha_vencimiento date,
    descripcion text,
    valor_base numeric(12,2) NOT NULL,
    descuento numeric(12,2) DEFAULT 0 NOT NULL,
    recargo numeric(12,2) DEFAULT 0 NOT NULL,
    total numeric(12,2) NOT NULL,
    saldo numeric(12,2) NOT NULL,
    estado optimscul.estado_cuenta_cobro_enum DEFAULT 'PENDIENTE'::optimscul.estado_cuenta_cobro_enum NOT NULL,
    observaciones text,
    anulado_por_usuario_id uuid,
    fecha_anulacion timestamp without time zone,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: cuenta_cobro_beneficio; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.cuenta_cobro_beneficio (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    cuenta_cobro_id uuid NOT NULL,
    estudiante_beneficio_financiero_id uuid,
    beneficio_financiero_id uuid,
    tipo optimscul.tipo_beneficio_financiero_enum NOT NULL,
    porcentaje_aplicado numeric(6,2),
    valor_fijo_aplicado numeric(12,2),
    valor_descuento_aplicado numeric(12,2) NOT NULL,
    observaciones text,
    created_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: decision_academica_estudiante; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.decision_academica_estudiante (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    resultado optimscul.resultado_academico_enum DEFAULT 'PENDIENTE'::optimscul.resultado_academico_enum NOT NULL,
    observaciones text,
    fecha_decision timestamp without time zone DEFAULT now() NOT NULL,
    usuario_id uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: documento; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.documento (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid,
    modulo optimscul.modulo_documento_enum NOT NULL,
    entidad_id uuid NOT NULL,
    nombre_archivo character varying(255) NOT NULL,
    nombre_original character varying(255),
    url_archivo text NOT NULL,
    mime_type character varying(120),
    tamano_bytes bigint,
    descripcion text,
    subido_por_usuario_id uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: entrega_actividad; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.entrega_actividad (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    actividad_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    fecha_entrega timestamp without time zone,
    comentario_estudiante text,
    archivo_url text,
    estado optimscul.estado_entrega_actividad_enum DEFAULT 'PENDIENTE'::optimscul.estado_entrega_actividad_enum NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: escala_valorativa; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.escala_valorativa (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    nombre character varying(100) NOT NULL,
    abreviatura character varying(20),
    nota_minima numeric(5,2) NOT NULL,
    nota_maxima numeric(5,2) NOT NULL,
    aprueba boolean DEFAULT false NOT NULL,
    orden smallint NOT NULL,
    activa boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_escala_rango CHECK ((nota_minima <= nota_maxima))
);


--
-- Name: estudiante; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.estudiante (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    persona_id uuid NOT NULL,
    codigo_estudiante character varying(30) NOT NULL,
    fecha_ingreso date,
    fecha_retiro date,
    estado optimscul.estado_estudiante_enum DEFAULT 'ACTIVO'::optimscul.estado_estudiante_enum NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: estudiante_acudiente; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.estudiante_acudiente (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    estudiante_id uuid NOT NULL,
    acudiente_id uuid NOT NULL,
    parentesco optimscul.tipo_parentesco_enum DEFAULT 'OTRO'::optimscul.tipo_parentesco_enum NOT NULL,
    parentesco_id uuid,
    es_principal boolean DEFAULT false NOT NULL,
    autorizado_recogida boolean DEFAULT true NOT NULL,
    autorizado_info_academica boolean DEFAULT true NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: estudiante_beneficio_financiero; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.estudiante_beneficio_financiero (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    beneficio_financiero_id uuid NOT NULL,
    anio_lectivo_id uuid,
    periodo_academico_id uuid,
    fecha_inicio date NOT NULL,
    fecha_fin date,
    estado optimscul.estado_beneficio_estudiante_enum DEFAULT 'ACTIVO'::optimscul.estado_beneficio_estudiante_enum NOT NULL,
    observaciones text,
    aprobado_por_usuario_id uuid,
    fecha_aprobacion timestamp without time zone,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: flyway_schema_history; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


--
-- Name: grado; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.grado (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(100) NOT NULL,
    nivel optimscul.nivel_academico_enum NOT NULL,
    orden smallint NOT NULL,
    estado optimscul.estado_registro_enum DEFAULT 'ACTIVO'::optimscul.estado_registro_enum NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: grupo; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.grupo (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    sede_id uuid,
    jornada_id uuid,
    grado_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(100) NOT NULL,
    cupo_maximo integer,
    estado optimscul.estado_grupo_enum DEFAULT 'ACTIVO'::optimscul.estado_grupo_enum NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: horario_carga; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.horario_carga (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    carga_academica_id uuid NOT NULL,
    sede_id uuid,
    dia_semana optimscul.dia_semana_enum NOT NULL,
    hora_inicio time without time zone NOT NULL,
    hora_fin time without time zone NOT NULL,
    aula character varying(50),
    activo boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_horario_horas CHECK ((hora_inicio < hora_fin))
);


--
-- Name: institucion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.institucion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(200) NOT NULL,
    nombre_corto character varying(100),
    tipo_institucion optimscul.tipo_institucion_enum DEFAULT 'COLEGIO'::optimscul.tipo_institucion_enum NOT NULL,
    nit character varying(30),
    dane character varying(30),
    resolucion_funcionamiento character varying(100),
    descripcion text,
    correo_contacto character varying(150),
    telefono_contacto character varying(50),
    sitio_web character varying(200),
    direccion_principal character varying(200),
    ciudad character varying(100),
    departamento character varying(100),
    pais character varying(100) DEFAULT 'Colombia'::character varying NOT NULL,
    logo_url text,
    zona_horaria character varying(80) DEFAULT 'America/Bogota'::character varying NOT NULL,
    moneda character varying(10) DEFAULT 'COP'::character varying NOT NULL,
    estado optimscul.estado_institucion_enum DEFAULT 'PRUEBA'::optimscul.estado_institucion_enum NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    dominio_correo character varying(150)
);


--
-- Name: jornada; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.jornada (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    hora_inicio time without time zone,
    hora_fin time without time zone,
    estado optimscul.estado_registro_enum DEFAULT 'ACTIVO'::optimscul.estado_registro_enum NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: matricula; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.matricula (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    grupo_id uuid,
    codigo_matricula character varying(40) NOT NULL,
    tipo optimscul.tipo_matricula_enum NOT NULL,
    estado optimscul.estado_matricula_enum DEFAULT 'PREMATRICULA'::optimscul.estado_matricula_enum NOT NULL,
    fecha_matricula date NOT NULL,
    fecha_estado date DEFAULT CURRENT_DATE NOT NULL,
    observaciones text,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: mensaje; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.mensaje (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    conversacion_id uuid NOT NULL,
    remitente_id uuid NOT NULL,
    contenido text NOT NULL,
    leido boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: notificacion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.notificacion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid,
    tipo optimscul.tipo_notificacion_enum DEFAULT 'GENERAL'::optimscul.tipo_notificacion_enum NOT NULL,
    titulo character varying(180) NOT NULL,
    mensaje text NOT NULL,
    modulo_relacionado character varying(80),
    entidad_relacionada_id uuid,
    prioridad smallint DEFAULT 1 NOT NULL,
    programada_para timestamp without time zone,
    created_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: notificacion_destinatario; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.notificacion_destinatario (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    notificacion_id uuid NOT NULL,
    usuario_id uuid NOT NULL,
    canal optimscul.canal_notificacion_enum DEFAULT 'IN_APP'::optimscul.canal_notificacion_enum NOT NULL,
    estado optimscul.estado_notificacion_enum DEFAULT 'PENDIENTE'::optimscul.estado_notificacion_enum NOT NULL,
    enviada_en timestamp without time zone,
    leida_en timestamp without time zone,
    error_envio text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: observacion_estudiante; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.observacion_estudiante (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    anio_lectivo_id uuid,
    periodo_academico_id uuid,
    grupo_id uuid,
    carga_academica_id uuid,
    tipo_observacion_id uuid NOT NULL,
    titulo character varying(150) NOT NULL,
    descripcion text NOT NULL,
    fecha_evento date NOT NULL,
    severidad optimscul.severidad_observacion_enum DEFAULT 'BAJA'::optimscul.severidad_observacion_enum NOT NULL,
    estado optimscul.estado_observacion_enum DEFAULT 'ABIERTA'::optimscul.estado_observacion_enum NOT NULL,
    visible_acudiente boolean DEFAULT true NOT NULL,
    requiere_seguimiento boolean DEFAULT false NOT NULL,
    cerrada_en timestamp without time zone,
    creada_por_usuario_id uuid,
    cerrada_por_usuario_id uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: pago; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.pago (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    cuenta_cobro_id uuid NOT NULL,
    fecha_pago timestamp without time zone DEFAULT now() NOT NULL,
    valor numeric(12,2) NOT NULL,
    metodo optimscul.metodo_pago_enum NOT NULL,
    referencia_pago character varying(80),
    comprobante_url text,
    observaciones text,
    registrado_por_usuario_id uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: parentesco; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.parentesco (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(80) NOT NULL,
    descripcion text,
    activo boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: periodo_academico; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.periodo_academico (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    numero smallint NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    fecha_inicio date NOT NULL,
    fecha_fin date NOT NULL,
    peso numeric(6,3),
    estado optimscul.estado_periodo_enum DEFAULT 'PLANEADO'::optimscul.estado_periodo_enum NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    boletin_habilitado boolean DEFAULT false NOT NULL,
    CONSTRAINT chk_periodo_fechas CHECK ((fecha_inicio <= fecha_fin))
);


--
-- Name: permiso; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.permiso (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    codigo character varying(80) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion text,
    modulo character varying(80) NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: persona; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.persona (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    tipo_documento optimscul.tipo_documento_persona_enum NOT NULL,
    numero_documento character varying(30) NOT NULL,
    primer_nombre character varying(60) NOT NULL,
    segundo_nombre character varying(60),
    primer_apellido character varying(60) NOT NULL,
    segundo_apellido character varying(60),
    fecha_nacimiento date,
    sexo optimscul.sexo_enum,
    nacionalidad character varying(80),
    telefono character varying(50),
    telefono_alternativo character varying(50),
    correo character varying(150),
    direccion character varying(200),
    barrio character varying(100),
    ciudad character varying(100),
    departamento character varying(100),
    pais character varying(100) DEFAULT 'Colombia'::character varying NOT NULL,
    foto_url text,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: postulacion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.postulacion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid,
    sede_id uuid,
    jornada_id uuid,
    grado_aspira_id uuid,
    codigo character varying(40) NOT NULL,
    fecha_postulacion timestamp without time zone DEFAULT now() NOT NULL,
    canal optimscul.canal_postulacion_enum DEFAULT 'WEB'::optimscul.canal_postulacion_enum NOT NULL,
    estado optimscul.estado_postulacion_enum DEFAULT 'RECIBIDA'::optimscul.estado_postulacion_enum NOT NULL,
    observaciones text,
    observaciones_internas text,
    cupo_reservado boolean DEFAULT false NOT NULL,
    aprobada_por_usuario_id uuid,
    fecha_aprobacion timestamp without time zone,
    rechazada_por_usuario_id uuid,
    fecha_rechazo timestamp without time zone,
    motivo_rechazo text,
    convertida_en_estudiante_id uuid,
    convertida_en_matricula_id uuid,
    fecha_conversion timestamp without time zone,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: postulacion_acudiente; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.postulacion_acudiente (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    postulacion_id uuid NOT NULL,
    tipo_documento optimscul.tipo_documento_persona_enum,
    numero_documento character varying(30),
    primer_nombre character varying(60) NOT NULL,
    segundo_nombre character varying(60),
    primer_apellido character varying(60) NOT NULL,
    segundo_apellido character varying(60),
    parentesco optimscul.tipo_parentesco_enum DEFAULT 'OTRO'::optimscul.tipo_parentesco_enum NOT NULL,
    telefono character varying(50),
    telefono_alternativo character varying(50),
    correo character varying(150),
    direccion character varying(200),
    ocupacion character varying(120),
    empresa character varying(150),
    es_principal boolean DEFAULT false NOT NULL,
    autorizado_recogida boolean DEFAULT true NOT NULL,
    autorizado_info_academica boolean DEFAULT true NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: postulacion_persona; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.postulacion_persona (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    postulacion_id uuid NOT NULL,
    tipo_documento optimscul.tipo_documento_persona_enum,
    numero_documento character varying(30),
    primer_nombre character varying(60) NOT NULL,
    segundo_nombre character varying(60),
    primer_apellido character varying(60) NOT NULL,
    segundo_apellido character varying(60),
    fecha_nacimiento date,
    sexo optimscul.sexo_enum,
    nacionalidad character varying(80),
    correo character varying(150),
    telefono character varying(50),
    direccion character varying(200),
    ciudad character varying(100),
    departamento character varying(100),
    pais character varying(100) DEFAULT 'Colombia'::character varying,
    colegio_procedencia character varying(150),
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: profesor; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.profesor (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    persona_id uuid NOT NULL,
    codigo_profesor character varying(30) NOT NULL,
    especialidad character varying(120),
    titulo_profesional character varying(150),
    fecha_vinculacion date,
    fecha_retiro date,
    estado optimscul.estado_profesor_enum DEFAULT 'ACTIVO'::optimscul.estado_profesor_enum NOT NULL,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: profesor_grupo_director; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.profesor_grupo_director (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    grupo_id uuid NOT NULL,
    profesor_id uuid NOT NULL,
    fecha_inicio date,
    fecha_fin date,
    activo boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: resumen_anual_estudiante; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.resumen_anual_estudiante (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    asignatura_id uuid NOT NULL,
    nota_final numeric(6,2),
    aprueba boolean,
    observaciones text,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: resumen_periodo_estudiante; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.resumen_periodo_estudiante (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    anio_lectivo_id uuid NOT NULL,
    periodo_academico_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    carga_academica_id uuid NOT NULL,
    nota_final numeric(6,2),
    observacion text,
    recuperacion_aplica boolean DEFAULT false NOT NULL,
    nota_recuperacion numeric(6,2),
    nota_definitiva numeric(6,2),
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: rol; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.rol (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    codigo character varying(50) NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text,
    es_sistema boolean DEFAULT false NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: rol_permiso; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.rol_permiso (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    rol_id uuid NOT NULL,
    permiso_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: sede; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.sede (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(150) NOT NULL,
    descripcion text,
    direccion character varying(200),
    telefono character varying(50),
    correo character varying(150),
    ciudad character varying(100),
    departamento character varying(100),
    pais character varying(100) DEFAULT 'Colombia'::character varying NOT NULL,
    principal boolean DEFAULT false NOT NULL,
    estado optimscul.estado_registro_enum DEFAULT 'ACTIVO'::optimscul.estado_registro_enum NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: seguimiento_observacion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.seguimiento_observacion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    observacion_id uuid NOT NULL,
    comentario text NOT NULL,
    estado_anterior optimscul.estado_observacion_enum,
    estado_nuevo optimscul.estado_observacion_enum,
    creado_por_usuario_id uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: seguimiento_postulacion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.seguimiento_postulacion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    postulacion_id uuid NOT NULL,
    comentario text NOT NULL,
    estado_anterior optimscul.estado_postulacion_enum,
    estado_nuevo optimscul.estado_postulacion_enum,
    creado_por_usuario_id uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: sesion_clase; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.sesion_clase (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    carga_academica_id uuid NOT NULL,
    horario_carga_id uuid,
    fecha date NOT NULL,
    hora_inicio time without time zone,
    hora_fin time without time zone,
    tema character varying(200),
    descripcion text,
    estado optimscul.estado_sesion_clase_enum DEFAULT 'PROGRAMADA'::optimscul.estado_sesion_clase_enum NOT NULL,
    fue_reprogramada boolean DEFAULT false NOT NULL,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: solicitud_institucion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.solicitud_institucion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    nombre_colegio character varying(200) NOT NULL,
    nit character varying(50),
    ciudad character varying(120),
    direccion character varying(255),
    telefono character varying(50),
    nombre_contacto character varying(200) NOT NULL,
    correo character varying(150) NOT NULL,
    mensaje text,
    enviada_por_usuario_id uuid,
    estado character varying(30) DEFAULT 'PENDIENTE'::character varying NOT NULL,
    revisada_por_usuario_id uuid,
    fecha_revision timestamp without time zone,
    motivo_rechazo text,
    convertida_en_institucion_id uuid,
    fecha_conversion timestamp without time zone,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: tipo_observacion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.tipo_observacion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    codigo character varying(30) NOT NULL,
    nombre character varying(120) NOT NULL,
    descripcion text,
    severidad optimscul.severidad_observacion_enum DEFAULT 'BAJA'::optimscul.severidad_observacion_enum NOT NULL,
    activa boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: usuario; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.usuario (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    persona_id uuid NOT NULL,
    username character varying(50) NOT NULL,
    password_hash text NOT NULL,
    email_login character varying(150),
    tipo_contexto optimscul.tipo_contexto_usuario_enum DEFAULT 'INSTITUCION'::optimscul.tipo_contexto_usuario_enum NOT NULL,
    estado optimscul.estado_usuario_enum DEFAULT 'PENDIENTE_ACTIVACION'::optimscul.estado_usuario_enum NOT NULL,
    requiere_cambio_password boolean DEFAULT true NOT NULL,
    email_verificado boolean DEFAULT false NOT NULL,
    doble_factor_habilitado boolean DEFAULT false NOT NULL,
    intentos_fallidos integer DEFAULT 0 NOT NULL,
    bloqueado_hasta timestamp without time zone,
    ultimo_login timestamp without time zone,
    ultimo_cambio_password timestamp without time zone,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: usuario_institucion; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.usuario_institucion (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    usuario_id uuid NOT NULL,
    institucion_id uuid NOT NULL,
    es_principal boolean DEFAULT false NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: usuario_rol; Type: TABLE; Schema: optimscul; Owner: -
--

CREATE TABLE optimscul.usuario_rol (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    usuario_id uuid NOT NULL,
    institucion_id uuid,
    rol_id uuid NOT NULL,
    activo boolean DEFAULT true NOT NULL,
    fecha_inicio date,
    fecha_fin date,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: actividad_academica actividad_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_pkey PRIMARY KEY (id);


--
-- Name: acudiente acudiente_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT acudiente_pkey PRIMARY KEY (id);


--
-- Name: anio_lectivo anio_lectivo_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.anio_lectivo
    ADD CONSTRAINT anio_lectivo_pkey PRIMARY KEY (id);


--
-- Name: area_academica area_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.area_academica
    ADD CONSTRAINT area_academica_pkey PRIMARY KEY (id);


--
-- Name: asignatura asignatura_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_pkey PRIMARY KEY (id);


--
-- Name: asistencia_clase asistencia_clase_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_pkey PRIMARY KEY (id);


--
-- Name: auditoria_evento auditoria_evento_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.auditoria_evento
    ADD CONSTRAINT auditoria_evento_pkey PRIMARY KEY (id);


--
-- Name: beneficio_financiero beneficio_financiero_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_pkey PRIMARY KEY (id);


--
-- Name: calificacion_actividad calificacion_actividad_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_pkey PRIMARY KEY (id);


--
-- Name: carga_academica carga_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_pkey PRIMARY KEY (id);


--
-- Name: concepto_cobro concepto_cobro_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_pkey PRIMARY KEY (id);


--
-- Name: configuracion_academica configuracion_academica_institucion_id_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.configuracion_academica
    ADD CONSTRAINT configuracion_academica_institucion_id_key UNIQUE (institucion_id);


--
-- Name: configuracion_academica configuracion_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.configuracion_academica
    ADD CONSTRAINT configuracion_academica_pkey PRIMARY KEY (id);


--
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_pkey PRIMARY KEY (id);


--
-- Name: cuenta_cobro cuenta_cobro_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_pkey PRIMARY KEY (id);


--
-- Name: decision_academica_estudiante decision_academica_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_pkey PRIMARY KEY (id);


--
-- Name: documento documento_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- Name: entrega_actividad entrega_actividad_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT entrega_actividad_pkey PRIMARY KEY (id);


--
-- Name: escala_valorativa escala_valorativa_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.escala_valorativa
    ADD CONSTRAINT escala_valorativa_pkey PRIMARY KEY (id);


--
-- Name: estudiante_acudiente estudiante_acudiente_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_pkey PRIMARY KEY (id);


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_pkey PRIMARY KEY (id);


--
-- Name: estudiante estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT estudiante_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: grado grado_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grado
    ADD CONSTRAINT grado_pkey PRIMARY KEY (id);


--
-- Name: grupo grupo_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_pkey PRIMARY KEY (id);


--
-- Name: horario_carga horario_carga_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_pkey PRIMARY KEY (id);


--
-- Name: institucion institucion_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.institucion
    ADD CONSTRAINT institucion_codigo_key UNIQUE (codigo);


--
-- Name: institucion institucion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id);


--
-- Name: jornada jornada_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.jornada
    ADD CONSTRAINT jornada_pkey PRIMARY KEY (id);


--
-- Name: matricula matricula_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_pkey PRIMARY KEY (id);


--
-- Name: notificacion_destinatario notificacion_destinatario_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT notificacion_destinatario_pkey PRIMARY KEY (id);


--
-- Name: notificacion notificacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion
    ADD CONSTRAINT notificacion_pkey PRIMARY KEY (id);


--
-- Name: observacion_estudiante observacion_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_pkey PRIMARY KEY (id);


--
-- Name: pago pago_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_pkey PRIMARY KEY (id);


--
-- Name: parentesco parentesco_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.parentesco
    ADD CONSTRAINT parentesco_codigo_key UNIQUE (codigo);


--
-- Name: parentesco parentesco_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.parentesco
    ADD CONSTRAINT parentesco_pkey PRIMARY KEY (id);


--
-- Name: periodo_academico periodo_academico_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT periodo_academico_pkey PRIMARY KEY (id);


--
-- Name: permiso permiso_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.permiso
    ADD CONSTRAINT permiso_codigo_key UNIQUE (codigo);


--
-- Name: permiso permiso_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.permiso
    ADD CONSTRAINT permiso_pkey PRIMARY KEY (id);


--
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- Name: conversacion pk_conversacion; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.conversacion
    ADD CONSTRAINT pk_conversacion PRIMARY KEY (id);


--
-- Name: mensaje pk_mensaje; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.mensaje
    ADD CONSTRAINT pk_mensaje PRIMARY KEY (id);


--
-- Name: postulacion_acudiente postulacion_acudiente_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion_acudiente
    ADD CONSTRAINT postulacion_acudiente_pkey PRIMARY KEY (id);


--
-- Name: postulacion_persona postulacion_persona_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion_persona
    ADD CONSTRAINT postulacion_persona_pkey PRIMARY KEY (id);


--
-- Name: postulacion_persona postulacion_persona_postulacion_id_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion_persona
    ADD CONSTRAINT postulacion_persona_postulacion_id_key UNIQUE (postulacion_id);


--
-- Name: postulacion postulacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_pkey PRIMARY KEY (id);


--
-- Name: profesor_grupo_director profesor_grupo_director_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT profesor_grupo_director_pkey PRIMARY KEY (id);


--
-- Name: profesor profesor_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT profesor_pkey PRIMARY KEY (id);


--
-- Name: resumen_anual_estudiante resumen_anual_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_pkey PRIMARY KEY (id);


--
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_pkey PRIMARY KEY (id);


--
-- Name: rol rol_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.rol
    ADD CONSTRAINT rol_codigo_key UNIQUE (codigo);


--
-- Name: rol_permiso rol_permiso_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT rol_permiso_pkey PRIMARY KEY (id);


--
-- Name: rol rol_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);


--
-- Name: sede sede_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sede
    ADD CONSTRAINT sede_pkey PRIMARY KEY (id);


--
-- Name: seguimiento_observacion seguimiento_observacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.seguimiento_observacion
    ADD CONSTRAINT seguimiento_observacion_pkey PRIMARY KEY (id);


--
-- Name: seguimiento_postulacion seguimiento_postulacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.seguimiento_postulacion
    ADD CONSTRAINT seguimiento_postulacion_pkey PRIMARY KEY (id);


--
-- Name: sesion_clase sesion_clase_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_pkey PRIMARY KEY (id);


--
-- Name: solicitud_institucion solicitud_institucion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.solicitud_institucion
    ADD CONSTRAINT solicitud_institucion_pkey PRIMARY KEY (id);


--
-- Name: tipo_observacion tipo_observacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.tipo_observacion
    ADD CONSTRAINT tipo_observacion_pkey PRIMARY KEY (id);


--
-- Name: acudiente uq_acudiente_institucion_persona; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT uq_acudiente_institucion_persona UNIQUE (institucion_id, persona_id);


--
-- Name: anio_lectivo uq_anio_lectivo_institucion_anio; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.anio_lectivo
    ADD CONSTRAINT uq_anio_lectivo_institucion_anio UNIQUE (institucion_id, anio);


--
-- Name: area_academica uq_area_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.area_academica
    ADD CONSTRAINT uq_area_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: asignatura uq_asignatura_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT uq_asignatura_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: asistencia_clase uq_asistencia_sesion_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT uq_asistencia_sesion_estudiante UNIQUE (sesion_clase_id, estudiante_id);


--
-- Name: beneficio_financiero uq_beneficio_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT uq_beneficio_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: calificacion_actividad uq_calificacion_actividad_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT uq_calificacion_actividad_estudiante UNIQUE (actividad_id, estudiante_id);


--
-- Name: concepto_cobro uq_concepto_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT uq_concepto_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: conversacion uq_conversacion_par; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.conversacion
    ADD CONSTRAINT uq_conversacion_par UNIQUE (institucion_id, usuario_menor_id, usuario_mayor_id);


--
-- Name: cuenta_cobro uq_cuenta_cobro_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT uq_cuenta_cobro_codigo UNIQUE (institucion_id, codigo);


--
-- Name: decision_academica_estudiante uq_decision_anual_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT uq_decision_anual_estudiante UNIQUE (anio_lectivo_id, estudiante_id);


--
-- Name: entrega_actividad uq_entrega_actividad_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT uq_entrega_actividad_estudiante UNIQUE (actividad_id, estudiante_id);


--
-- Name: estudiante_acudiente uq_estudiante_acudiente; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT uq_estudiante_acudiente UNIQUE (estudiante_id, acudiente_id);


--
-- Name: estudiante uq_estudiante_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT uq_estudiante_institucion_codigo UNIQUE (institucion_id, codigo_estudiante);


--
-- Name: estudiante uq_estudiante_institucion_persona; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT uq_estudiante_institucion_persona UNIQUE (institucion_id, persona_id);


--
-- Name: grado uq_grado_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grado
    ADD CONSTRAINT uq_grado_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: grupo uq_grupo_institucion_anio_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT uq_grupo_institucion_anio_codigo UNIQUE (institucion_id, anio_lectivo_id, codigo);


--
-- Name: jornada uq_jornada_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.jornada
    ADD CONSTRAINT uq_jornada_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: matricula uq_matricula_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT uq_matricula_codigo UNIQUE (institucion_id, codigo_matricula);


--
-- Name: matricula uq_matricula_estudiante_anio; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT uq_matricula_estudiante_anio UNIQUE (institucion_id, estudiante_id, anio_lectivo_id);


--
-- Name: notificacion_destinatario uq_notificacion_destinatario; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT uq_notificacion_destinatario UNIQUE (notificacion_id, usuario_id, canal);


--
-- Name: periodo_academico uq_periodo_institucion_anio_numero; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT uq_periodo_institucion_anio_numero UNIQUE (institucion_id, anio_lectivo_id, numero);


--
-- Name: persona uq_persona_documento; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.persona
    ADD CONSTRAINT uq_persona_documento UNIQUE (tipo_documento, numero_documento);


--
-- Name: postulacion uq_postulacion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT uq_postulacion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: profesor_grupo_director uq_profesor_grupo_director; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT uq_profesor_grupo_director UNIQUE (grupo_id, profesor_id, fecha_inicio);


--
-- Name: profesor uq_profesor_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT uq_profesor_institucion_codigo UNIQUE (institucion_id, codigo_profesor);


--
-- Name: profesor uq_profesor_institucion_persona; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT uq_profesor_institucion_persona UNIQUE (institucion_id, persona_id);


--
-- Name: resumen_anual_estudiante uq_resumen_anual; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT uq_resumen_anual UNIQUE (anio_lectivo_id, estudiante_id, asignatura_id);


--
-- Name: resumen_periodo_estudiante uq_resumen_periodo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT uq_resumen_periodo UNIQUE (periodo_academico_id, estudiante_id, carga_academica_id);


--
-- Name: rol_permiso uq_rol_permiso; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT uq_rol_permiso UNIQUE (rol_id, permiso_id);


--
-- Name: sede uq_sede_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sede
    ADD CONSTRAINT uq_sede_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: tipo_observacion uq_tipo_observacion_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.tipo_observacion
    ADD CONSTRAINT uq_tipo_observacion_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- Name: usuario uq_usuario_email_login; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT uq_usuario_email_login UNIQUE (email_login);


--
-- Name: usuario_institucion uq_usuario_institucion; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT uq_usuario_institucion UNIQUE (usuario_id, institucion_id);


--
-- Name: usuario_rol uq_usuario_rol; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT uq_usuario_rol UNIQUE (usuario_id, institucion_id, rol_id);


--
-- Name: usuario_institucion usuario_institucion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT usuario_institucion_pkey PRIMARY KEY (id);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: usuario_rol usuario_rol_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_pkey PRIMARY KEY (id);


--
-- Name: usuario usuario_username_key; Type: CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX flyway_schema_history_s_idx ON optimscul.flyway_schema_history USING btree (success);


--
-- Name: idx_actividad_carga; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_actividad_carga ON optimscul.actividad_academica USING btree (carga_academica_id);


--
-- Name: idx_actividad_carga_periodo; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_actividad_carga_periodo ON optimscul.actividad_academica USING btree (carga_academica_id, periodo_academico_id);


--
-- Name: idx_actividad_periodo; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_actividad_periodo ON optimscul.actividad_academica USING btree (periodo_academico_id);


--
-- Name: idx_acudiente_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_acudiente_institucion ON optimscul.acudiente USING btree (institucion_id);


--
-- Name: idx_acudiente_persona; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_acudiente_persona ON optimscul.acudiente USING btree (persona_id);


--
-- Name: idx_anio_lectivo_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_anio_lectivo_institucion ON optimscul.anio_lectivo USING btree (institucion_id);


--
-- Name: idx_area_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_area_institucion ON optimscul.area_academica USING btree (institucion_id);


--
-- Name: idx_asignatura_area; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_asignatura_area ON optimscul.asignatura USING btree (area_id);


--
-- Name: idx_asignatura_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_asignatura_institucion ON optimscul.asignatura USING btree (institucion_id);


--
-- Name: idx_asistencia_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_asistencia_estudiante ON optimscul.asistencia_clase USING btree (estudiante_id);


--
-- Name: idx_asistencia_sesion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_asistencia_sesion ON optimscul.asistencia_clase USING btree (sesion_clase_id);


--
-- Name: idx_auditoria_entidad; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_auditoria_entidad ON optimscul.auditoria_evento USING btree (entidad, entidad_id);


--
-- Name: idx_auditoria_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_auditoria_institucion ON optimscul.auditoria_evento USING btree (institucion_id);


--
-- Name: idx_auditoria_usuario; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_auditoria_usuario ON optimscul.auditoria_evento USING btree (usuario_id);


--
-- Name: idx_beneficio_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_beneficio_institucion ON optimscul.beneficio_financiero USING btree (institucion_id);


--
-- Name: idx_calificacion_actividad; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_calificacion_actividad ON optimscul.calificacion_actividad USING btree (actividad_id);


--
-- Name: idx_calificacion_actividad_id; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_calificacion_actividad_id ON optimscul.calificacion_actividad USING btree (actividad_id);


--
-- Name: idx_calificacion_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_calificacion_estudiante ON optimscul.calificacion_actividad USING btree (estudiante_id);


--
-- Name: idx_calificacion_estudiante_actividad; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_calificacion_estudiante_actividad ON optimscul.calificacion_actividad USING btree (estudiante_id, actividad_id);


--
-- Name: idx_carga_anio; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_carga_anio ON optimscul.carga_academica USING btree (anio_lectivo_id);


--
-- Name: idx_carga_asignatura; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_carga_asignatura ON optimscul.carga_academica USING btree (asignatura_id);


--
-- Name: idx_carga_grupo; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_carga_grupo ON optimscul.carga_academica USING btree (grupo_id);


--
-- Name: idx_carga_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_carga_institucion ON optimscul.carga_academica USING btree (institucion_id);


--
-- Name: idx_concepto_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_concepto_institucion ON optimscul.concepto_cobro USING btree (institucion_id);


--
-- Name: idx_cuenta_beneficio_cuenta; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_cuenta_beneficio_cuenta ON optimscul.cuenta_cobro_beneficio USING btree (cuenta_cobro_id);


--
-- Name: idx_cuenta_cobro_estado; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_cuenta_cobro_estado ON optimscul.cuenta_cobro USING btree (estado);


--
-- Name: idx_cuenta_cobro_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_cuenta_cobro_estudiante ON optimscul.cuenta_cobro USING btree (estudiante_id);


--
-- Name: idx_decision_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_decision_estudiante ON optimscul.decision_academica_estudiante USING btree (estudiante_id);


--
-- Name: idx_documento_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_documento_institucion ON optimscul.documento USING btree (institucion_id);


--
-- Name: idx_documento_modulo_entidad; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_documento_modulo_entidad ON optimscul.documento USING btree (modulo, entidad_id);


--
-- Name: idx_entrega_actividad; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_entrega_actividad ON optimscul.entrega_actividad USING btree (actividad_id);


--
-- Name: idx_entrega_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_entrega_estudiante ON optimscul.entrega_actividad USING btree (estudiante_id);


--
-- Name: idx_escala_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_escala_institucion ON optimscul.escala_valorativa USING btree (institucion_id);


--
-- Name: idx_est_beneficio_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_est_beneficio_estudiante ON optimscul.estudiante_beneficio_financiero USING btree (estudiante_id);


--
-- Name: idx_estudiante_acudiente_acudiente; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_estudiante_acudiente_acudiente ON optimscul.estudiante_acudiente USING btree (acudiente_id);


--
-- Name: idx_estudiante_acudiente_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_estudiante_acudiente_estudiante ON optimscul.estudiante_acudiente USING btree (estudiante_id);


--
-- Name: idx_estudiante_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_estudiante_institucion ON optimscul.estudiante USING btree (institucion_id);


--
-- Name: idx_estudiante_persona; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_estudiante_persona ON optimscul.estudiante USING btree (persona_id);


--
-- Name: idx_grado_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_grado_institucion ON optimscul.grado USING btree (institucion_id);


--
-- Name: idx_grupo_anio; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_grupo_anio ON optimscul.grupo USING btree (anio_lectivo_id);


--
-- Name: idx_grupo_grado; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_grupo_grado ON optimscul.grupo USING btree (grado_id);


--
-- Name: idx_grupo_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_grupo_institucion ON optimscul.grupo USING btree (institucion_id);


--
-- Name: idx_horario_carga; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_horario_carga ON optimscul.horario_carga USING btree (carga_academica_id);


--
-- Name: idx_jornada_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_jornada_institucion ON optimscul.jornada USING btree (institucion_id);


--
-- Name: idx_matricula_anio; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_matricula_anio ON optimscul.matricula USING btree (anio_lectivo_id);


--
-- Name: idx_matricula_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_matricula_estudiante ON optimscul.matricula USING btree (estudiante_id);


--
-- Name: idx_matricula_grupo; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_matricula_grupo ON optimscul.matricula USING btree (grupo_id);


--
-- Name: idx_matricula_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_matricula_institucion ON optimscul.matricula USING btree (institucion_id);


--
-- Name: idx_mensaje_conversacion_fecha; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_mensaje_conversacion_fecha ON optimscul.mensaje USING btree (conversacion_id, created_at DESC);


--
-- Name: idx_mensaje_no_leidos; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_mensaje_no_leidos ON optimscul.mensaje USING btree (conversacion_id, leido, remitente_id);


--
-- Name: idx_notif_dest_estado; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_notif_dest_estado ON optimscul.notificacion_destinatario USING btree (estado);


--
-- Name: idx_notif_dest_usuario; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_notif_dest_usuario ON optimscul.notificacion_destinatario USING btree (usuario_id);


--
-- Name: idx_notificacion_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_notificacion_institucion ON optimscul.notificacion USING btree (institucion_id);


--
-- Name: idx_observacion_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_observacion_estudiante ON optimscul.observacion_estudiante USING btree (estudiante_id);


--
-- Name: idx_observacion_periodo; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_observacion_periodo ON optimscul.observacion_estudiante USING btree (periodo_academico_id);


--
-- Name: idx_pago_cuenta; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_pago_cuenta ON optimscul.pago USING btree (cuenta_cobro_id);


--
-- Name: idx_pago_fecha; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_pago_fecha ON optimscul.pago USING btree (fecha_pago);


--
-- Name: idx_periodo_anio; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_periodo_anio ON optimscul.periodo_academico USING btree (anio_lectivo_id);


--
-- Name: idx_periodo_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_periodo_institucion ON optimscul.periodo_academico USING btree (institucion_id);


--
-- Name: idx_persona_apellidos; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_persona_apellidos ON optimscul.persona USING btree (primer_apellido, segundo_apellido);


--
-- Name: idx_persona_nombres; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_persona_nombres ON optimscul.persona USING btree (primer_nombre, segundo_nombre);


--
-- Name: idx_postulacion_acudiente_postulacion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_postulacion_acudiente_postulacion ON optimscul.postulacion_acudiente USING btree (postulacion_id);


--
-- Name: idx_postulacion_anio; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_postulacion_anio ON optimscul.postulacion USING btree (anio_lectivo_id);


--
-- Name: idx_postulacion_estado; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_postulacion_estado ON optimscul.postulacion USING btree (estado);


--
-- Name: idx_postulacion_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_postulacion_institucion ON optimscul.postulacion USING btree (institucion_id);


--
-- Name: idx_profesor_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_profesor_institucion ON optimscul.profesor USING btree (institucion_id);


--
-- Name: idx_profesor_persona; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_profesor_persona ON optimscul.profesor USING btree (persona_id);


--
-- Name: idx_resumen_anual_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_resumen_anual_estudiante ON optimscul.resumen_anual_estudiante USING btree (estudiante_id);


--
-- Name: idx_resumen_periodo_carga; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_resumen_periodo_carga ON optimscul.resumen_periodo_estudiante USING btree (carga_academica_id);


--
-- Name: idx_resumen_periodo_estudiante; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_resumen_periodo_estudiante ON optimscul.resumen_periodo_estudiante USING btree (estudiante_id);


--
-- Name: idx_rol_permiso_permiso; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_rol_permiso_permiso ON optimscul.rol_permiso USING btree (permiso_id);


--
-- Name: idx_rol_permiso_rol; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_rol_permiso_rol ON optimscul.rol_permiso USING btree (rol_id);


--
-- Name: idx_sede_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_sede_institucion ON optimscul.sede USING btree (institucion_id);


--
-- Name: idx_seguimiento_observacion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_seguimiento_observacion ON optimscul.seguimiento_observacion USING btree (observacion_id);


--
-- Name: idx_seguimiento_postulacion_postulacion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_seguimiento_postulacion_postulacion ON optimscul.seguimiento_postulacion USING btree (postulacion_id);


--
-- Name: idx_sesion_carga; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_sesion_carga ON optimscul.sesion_clase USING btree (carga_academica_id);


--
-- Name: idx_sesion_fecha; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_sesion_fecha ON optimscul.sesion_clase USING btree (fecha);


--
-- Name: idx_tipo_observacion_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_tipo_observacion_institucion ON optimscul.tipo_observacion USING btree (institucion_id);


--
-- Name: idx_usuario_institucion_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_usuario_institucion_institucion ON optimscul.usuario_institucion USING btree (institucion_id);


--
-- Name: idx_usuario_institucion_usuario; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_usuario_institucion_usuario ON optimscul.usuario_institucion USING btree (usuario_id);


--
-- Name: idx_usuario_persona; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_usuario_persona ON optimscul.usuario USING btree (persona_id);


--
-- Name: idx_usuario_rol_institucion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_usuario_rol_institucion ON optimscul.usuario_rol USING btree (institucion_id);


--
-- Name: idx_usuario_rol_rol; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_usuario_rol_rol ON optimscul.usuario_rol USING btree (rol_id);


--
-- Name: idx_usuario_rol_usuario; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX idx_usuario_rol_usuario ON optimscul.usuario_rol USING btree (usuario_id);


--
-- Name: ix_conversacion_mayor; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX ix_conversacion_mayor ON optimscul.conversacion USING btree (usuario_mayor_id);


--
-- Name: ix_conversacion_menor; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX ix_conversacion_menor ON optimscul.conversacion USING btree (usuario_menor_id);


--
-- Name: ix_mensaje_conversacion; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX ix_mensaje_conversacion ON optimscul.mensaje USING btree (conversacion_id, created_at);


--
-- Name: ix_notif_dest_usuario; Type: INDEX; Schema: optimscul; Owner: -
--

CREATE INDEX ix_notif_dest_usuario ON optimscul.notificacion_destinatario USING btree (usuario_id, estado);


--
-- Name: actividad_academica actividad_academica_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- Name: actividad_academica actividad_academica_creada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_creada_por_usuario_id_fkey FOREIGN KEY (creada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: actividad_academica actividad_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: actividad_academica actividad_academica_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- Name: actividad_academica actividad_academica_sesion_clase_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_sesion_clase_id_fkey FOREIGN KEY (sesion_clase_id) REFERENCES optimscul.sesion_clase(id) ON DELETE SET NULL;


--
-- Name: actividad_academica actividad_academica_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: acudiente acudiente_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT acudiente_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: acudiente acudiente_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT acudiente_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- Name: anio_lectivo anio_lectivo_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.anio_lectivo
    ADD CONSTRAINT anio_lectivo_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: area_academica area_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.area_academica
    ADD CONSTRAINT area_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: asignatura asignatura_area_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_area_id_fkey FOREIGN KEY (area_id) REFERENCES optimscul.area_academica(id) ON DELETE SET NULL;


--
-- Name: asignatura asignatura_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: asignatura asignatura_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: asignatura asignatura_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: asistencia_clase asistencia_clase_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: asistencia_clase asistencia_clase_registrada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_registrada_por_usuario_id_fkey FOREIGN KEY (registrada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: asistencia_clase asistencia_clase_sesion_clase_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_sesion_clase_id_fkey FOREIGN KEY (sesion_clase_id) REFERENCES optimscul.sesion_clase(id) ON DELETE CASCADE;


--
-- Name: auditoria_evento auditoria_evento_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.auditoria_evento
    ADD CONSTRAINT auditoria_evento_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: auditoria_evento auditoria_evento_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.auditoria_evento
    ADD CONSTRAINT auditoria_evento_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: beneficio_financiero beneficio_financiero_concepto_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_concepto_cobro_id_fkey FOREIGN KEY (concepto_cobro_id) REFERENCES optimscul.concepto_cobro(id) ON DELETE SET NULL;


--
-- Name: beneficio_financiero beneficio_financiero_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: beneficio_financiero beneficio_financiero_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: beneficio_financiero beneficio_financiero_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: calificacion_actividad calificacion_actividad_actividad_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_actividad_id_fkey FOREIGN KEY (actividad_id) REFERENCES optimscul.actividad_academica(id) ON DELETE CASCADE;


--
-- Name: calificacion_actividad calificacion_actividad_calificada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_calificada_por_usuario_id_fkey FOREIGN KEY (calificada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: calificacion_actividad calificacion_actividad_entrega_actividad_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_entrega_actividad_id_fkey FOREIGN KEY (entrega_actividad_id) REFERENCES optimscul.entrega_actividad(id) ON DELETE SET NULL;


--
-- Name: calificacion_actividad calificacion_actividad_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: carga_academica carga_academica_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- Name: carga_academica carga_academica_asignatura_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_asignatura_id_fkey FOREIGN KEY (asignatura_id) REFERENCES optimscul.asignatura(id) ON DELETE RESTRICT;


--
-- Name: carga_academica carga_academica_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE RESTRICT;


--
-- Name: carga_academica carga_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: concepto_cobro concepto_cobro_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: concepto_cobro concepto_cobro_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: concepto_cobro concepto_cobro_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: configuracion_academica configuracion_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.configuracion_academica
    ADD CONSTRAINT configuracion_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: cuenta_cobro cuenta_cobro_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro cuenta_cobro_anulado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_anulado_por_usuario_id_fkey FOREIGN KEY (anulado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_beneficio_financiero_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_beneficio_financiero_id_fkey FOREIGN KEY (beneficio_financiero_id) REFERENCES optimscul.beneficio_financiero(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_cuenta_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_cuenta_cobro_id_fkey FOREIGN KEY (cuenta_cobro_id) REFERENCES optimscul.cuenta_cobro(id) ON DELETE CASCADE;


--
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_estudiante_beneficio_financiero_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_estudiante_beneficio_financiero_id_fkey FOREIGN KEY (estudiante_beneficio_financiero_id) REFERENCES optimscul.estudiante_beneficio_financiero(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: cuenta_cobro cuenta_cobro_concepto_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_concepto_cobro_id_fkey FOREIGN KEY (concepto_cobro_id) REFERENCES optimscul.concepto_cobro(id) ON DELETE RESTRICT;


--
-- Name: cuenta_cobro cuenta_cobro_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro cuenta_cobro_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE RESTRICT;


--
-- Name: cuenta_cobro cuenta_cobro_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: cuenta_cobro cuenta_cobro_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- Name: cuenta_cobro cuenta_cobro_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: decision_academica_estudiante decision_academica_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- Name: decision_academica_estudiante decision_academica_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: decision_academica_estudiante decision_academica_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: decision_academica_estudiante decision_academica_estudiante_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: documento documento_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.documento
    ADD CONSTRAINT documento_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: documento documento_subido_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.documento
    ADD CONSTRAINT documento_subido_por_usuario_id_fkey FOREIGN KEY (subido_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: entrega_actividad entrega_actividad_actividad_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT entrega_actividad_actividad_id_fkey FOREIGN KEY (actividad_id) REFERENCES optimscul.actividad_academica(id) ON DELETE CASCADE;


--
-- Name: entrega_actividad entrega_actividad_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT entrega_actividad_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: escala_valorativa escala_valorativa_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.escala_valorativa
    ADD CONSTRAINT escala_valorativa_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: estudiante_acudiente estudiante_acudiente_acudiente_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_acudiente_id_fkey FOREIGN KEY (acudiente_id) REFERENCES optimscul.acudiente(id) ON DELETE CASCADE;


--
-- Name: estudiante_acudiente estudiante_acudiente_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: estudiante_acudiente estudiante_acudiente_parentesco_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_parentesco_id_fkey FOREIGN KEY (parentesco_id) REFERENCES optimscul.parentesco(id) ON DELETE SET NULL;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_aprobado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_aprobado_por_usuario_id_fkey FOREIGN KEY (aprobado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_beneficio_financiero_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_beneficio_financiero_id_fkey FOREIGN KEY (beneficio_financiero_id) REFERENCES optimscul.beneficio_financiero(id) ON DELETE CASCADE;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: estudiante estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: estudiante estudiante_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT estudiante_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- Name: carga_academica fk_carga_academica_profesor; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT fk_carga_academica_profesor FOREIGN KEY (profesor_id) REFERENCES optimscul.profesor(id) ON DELETE RESTRICT;


--
-- Name: mensaje fk_mensaje_conversacion; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.mensaje
    ADD CONSTRAINT fk_mensaje_conversacion FOREIGN KEY (conversacion_id) REFERENCES optimscul.conversacion(id) ON DELETE CASCADE;


--
-- Name: profesor_grupo_director fk_profesor_grupo_director_profesor; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT fk_profesor_grupo_director_profesor FOREIGN KEY (profesor_id) REFERENCES optimscul.profesor(id) ON DELETE CASCADE;


--
-- Name: grado grado_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grado
    ADD CONSTRAINT grado_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: grupo grupo_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- Name: grupo grupo_grado_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_grado_id_fkey FOREIGN KEY (grado_id) REFERENCES optimscul.grado(id) ON DELETE RESTRICT;


--
-- Name: grupo grupo_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: grupo grupo_jornada_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_jornada_id_fkey FOREIGN KEY (jornada_id) REFERENCES optimscul.jornada(id) ON DELETE SET NULL;


--
-- Name: grupo grupo_sede_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_sede_id_fkey FOREIGN KEY (sede_id) REFERENCES optimscul.sede(id) ON DELETE SET NULL;


--
-- Name: horario_carga horario_carga_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- Name: horario_carga horario_carga_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: horario_carga horario_carga_sede_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_sede_id_fkey FOREIGN KEY (sede_id) REFERENCES optimscul.sede(id) ON DELETE SET NULL;


--
-- Name: jornada jornada_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.jornada
    ADD CONSTRAINT jornada_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: matricula matricula_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE RESTRICT;


--
-- Name: matricula matricula_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: matricula matricula_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE RESTRICT;


--
-- Name: matricula matricula_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE SET NULL;


--
-- Name: matricula matricula_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: matricula matricula_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: notificacion notificacion_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion
    ADD CONSTRAINT notificacion_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: notificacion_destinatario notificacion_destinatario_notificacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT notificacion_destinatario_notificacion_id_fkey FOREIGN KEY (notificacion_id) REFERENCES optimscul.notificacion(id) ON DELETE CASCADE;


--
-- Name: notificacion_destinatario notificacion_destinatario_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT notificacion_destinatario_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE CASCADE;


--
-- Name: notificacion notificacion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.notificacion
    ADD CONSTRAINT notificacion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: observacion_estudiante observacion_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- Name: observacion_estudiante observacion_estudiante_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE SET NULL;


--
-- Name: observacion_estudiante observacion_estudiante_cerrada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_cerrada_por_usuario_id_fkey FOREIGN KEY (cerrada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: observacion_estudiante observacion_estudiante_creada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_creada_por_usuario_id_fkey FOREIGN KEY (creada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: observacion_estudiante observacion_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: observacion_estudiante observacion_estudiante_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE SET NULL;


--
-- Name: observacion_estudiante observacion_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: observacion_estudiante observacion_estudiante_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- Name: observacion_estudiante observacion_estudiante_tipo_observacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_tipo_observacion_id_fkey FOREIGN KEY (tipo_observacion_id) REFERENCES optimscul.tipo_observacion(id) ON DELETE RESTRICT;


--
-- Name: pago pago_cuenta_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_cuenta_cobro_id_fkey FOREIGN KEY (cuenta_cobro_id) REFERENCES optimscul.cuenta_cobro(id) ON DELETE CASCADE;


--
-- Name: pago pago_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: pago pago_registrado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_registrado_por_usuario_id_fkey FOREIGN KEY (registrado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: periodo_academico periodo_academico_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT periodo_academico_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- Name: periodo_academico periodo_academico_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT periodo_academico_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: postulacion_acudiente postulacion_acudiente_postulacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion_acudiente
    ADD CONSTRAINT postulacion_acudiente_postulacion_id_fkey FOREIGN KEY (postulacion_id) REFERENCES optimscul.postulacion(id) ON DELETE CASCADE;


--
-- Name: postulacion postulacion_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_aprobada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_aprobada_por_usuario_id_fkey FOREIGN KEY (aprobada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_convertida_en_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_convertida_en_estudiante_id_fkey FOREIGN KEY (convertida_en_estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_convertida_en_matricula_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_convertida_en_matricula_id_fkey FOREIGN KEY (convertida_en_matricula_id) REFERENCES optimscul.matricula(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_grado_aspira_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_grado_aspira_id_fkey FOREIGN KEY (grado_aspira_id) REFERENCES optimscul.grado(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: postulacion postulacion_jornada_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_jornada_id_fkey FOREIGN KEY (jornada_id) REFERENCES optimscul.jornada(id) ON DELETE SET NULL;


--
-- Name: postulacion_persona postulacion_persona_postulacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion_persona
    ADD CONSTRAINT postulacion_persona_postulacion_id_fkey FOREIGN KEY (postulacion_id) REFERENCES optimscul.postulacion(id) ON DELETE CASCADE;


--
-- Name: postulacion postulacion_rechazada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_rechazada_por_usuario_id_fkey FOREIGN KEY (rechazada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_sede_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_sede_id_fkey FOREIGN KEY (sede_id) REFERENCES optimscul.sede(id) ON DELETE SET NULL;


--
-- Name: postulacion postulacion_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: profesor_grupo_director profesor_grupo_director_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT profesor_grupo_director_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE CASCADE;


--
-- Name: profesor profesor_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT profesor_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: profesor profesor_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT profesor_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- Name: resumen_anual_estudiante resumen_anual_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- Name: resumen_anual_estudiante resumen_anual_estudiante_asignatura_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_asignatura_id_fkey FOREIGN KEY (asignatura_id) REFERENCES optimscul.asignatura(id) ON DELETE RESTRICT;


--
-- Name: resumen_anual_estudiante resumen_anual_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: resumen_anual_estudiante resumen_anual_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE CASCADE;


--
-- Name: rol_permiso rol_permiso_permiso_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT rol_permiso_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES optimscul.permiso(id) ON DELETE CASCADE;


--
-- Name: rol_permiso rol_permiso_rol_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT rol_permiso_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES optimscul.rol(id) ON DELETE CASCADE;


--
-- Name: sede sede_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sede
    ADD CONSTRAINT sede_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: seguimiento_observacion seguimiento_observacion_creado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.seguimiento_observacion
    ADD CONSTRAINT seguimiento_observacion_creado_por_usuario_id_fkey FOREIGN KEY (creado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: seguimiento_observacion seguimiento_observacion_observacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.seguimiento_observacion
    ADD CONSTRAINT seguimiento_observacion_observacion_id_fkey FOREIGN KEY (observacion_id) REFERENCES optimscul.observacion_estudiante(id) ON DELETE CASCADE;


--
-- Name: seguimiento_postulacion seguimiento_postulacion_creado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.seguimiento_postulacion
    ADD CONSTRAINT seguimiento_postulacion_creado_por_usuario_id_fkey FOREIGN KEY (creado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: seguimiento_postulacion seguimiento_postulacion_postulacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.seguimiento_postulacion
    ADD CONSTRAINT seguimiento_postulacion_postulacion_id_fkey FOREIGN KEY (postulacion_id) REFERENCES optimscul.postulacion(id) ON DELETE CASCADE;


--
-- Name: sesion_clase sesion_clase_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- Name: sesion_clase sesion_clase_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: sesion_clase sesion_clase_horario_carga_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_horario_carga_id_fkey FOREIGN KEY (horario_carga_id) REFERENCES optimscul.horario_carga(id) ON DELETE SET NULL;


--
-- Name: sesion_clase sesion_clase_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: sesion_clase sesion_clase_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- Name: tipo_observacion tipo_observacion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.tipo_observacion
    ADD CONSTRAINT tipo_observacion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: usuario_institucion usuario_institucion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT usuario_institucion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: usuario_institucion usuario_institucion_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT usuario_institucion_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE CASCADE;


--
-- Name: usuario usuario_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT usuario_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- Name: usuario_rol usuario_rol_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- Name: usuario_rol usuario_rol_rol_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES optimscul.rol(id) ON DELETE CASCADE;


--
-- Name: usuario_rol usuario_rol_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: -
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--


