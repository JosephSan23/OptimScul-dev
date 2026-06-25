--
-- PostgreSQL database dump
--

\restrict 9zFTh2G7tucwNIwg3MuUL43743DEZ2W6miAn5UezxAQfXuLC9Ey3Xpu2uyvGTiO

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

-- Started on 2026-06-25 17:21:14

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

DO $$ BEGIN
  IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'postgres') THEN
    CREATE ROLE postgres SUPERUSER;
  END IF;
END $$;

--
-- TOC entry 7 (class 2615 OID 23088)
-- Name: optimscul; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA optimscul;


ALTER SCHEMA optimscul OWNER TO postgres;

--
-- TOC entry 2 (class 3079 OID 23050)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 6094 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 1059 (class 1247 OID 23548)
-- Name: accion_auditoria_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.accion_auditoria_enum OWNER TO postgres;

--
-- TOC entry 1053 (class 1247 OID 23528)
-- Name: canal_notificacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.canal_notificacion_enum AS ENUM (
    'IN_APP',
    'EMAIL',
    'SMS',
    'WHATSAPP'
);


ALTER TYPE optimscul.canal_notificacion_enum OWNER TO postgres;

--
-- TOC entry 1017 (class 1247 OID 23370)
-- Name: canal_postulacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.canal_postulacion_enum AS ENUM (
    'WEB',
    'PRESENCIAL',
    'TELEFONICO',
    'REFERIDO',
    'OTRO'
);


ALTER TYPE optimscul.canal_postulacion_enum OWNER TO postgres;

--
-- TOC entry 1029 (class 1247 OID 23418)
-- Name: categoria_concepto_cobro_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.categoria_concepto_cobro_enum OWNER TO postgres;

--
-- TOC entry 966 (class 1247 OID 23174)
-- Name: dia_semana_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.dia_semana_enum OWNER TO postgres;

--
-- TOC entry 990 (class 1247 OID 23266)
-- Name: estado_actividad_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_actividad_enum AS ENUM (
    'BORRADOR',
    'PUBLICADA',
    'CERRADA',
    'ANULADA'
);


ALTER TYPE optimscul.estado_actividad_enum OWNER TO postgres;

--
-- TOC entry 1005 (class 1247 OID 23316)
-- Name: estado_acudiente_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_acudiente_enum AS ENUM (
    'ACTIVO',
    'INACTIVO'
);


ALTER TYPE optimscul.estado_acudiente_enum OWNER TO postgres;

--
-- TOC entry 969 (class 1247 OID 23190)
-- Name: estado_anio_lectivo_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_anio_lectivo_enum AS ENUM (
    'PLANEACION',
    'ACTIVO',
    'CERRADO',
    'CANCELADO'
);


ALTER TYPE optimscul.estado_anio_lectivo_enum OWNER TO postgres;

--
-- TOC entry 1038 (class 1247 OID 23454)
-- Name: estado_beneficio_estudiante_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_beneficio_estudiante_enum AS ENUM (
    'ACTIVO',
    'SUSPENDIDO',
    'VENCIDO',
    'CANCELADO'
);


ALTER TYPE optimscul.estado_beneficio_estudiante_enum OWNER TO postgres;

--
-- TOC entry 978 (class 1247 OID 23218)
-- Name: estado_carga_academica_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_carga_academica_enum AS ENUM (
    'ACTIVA',
    'INACTIVA',
    'FINALIZADA',
    'CANCELADA'
);


ALTER TYPE optimscul.estado_carga_academica_enum OWNER TO postgres;

--
-- TOC entry 1041 (class 1247 OID 23464)
-- Name: estado_cuenta_cobro_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_cuenta_cobro_enum AS ENUM (
    'PENDIENTE',
    'PAGADA',
    'PAGADA_PARCIAL',
    'VENCIDA',
    'ANULADA'
);


ALTER TYPE optimscul.estado_cuenta_cobro_enum OWNER TO postgres;

--
-- TOC entry 993 (class 1247 OID 23276)
-- Name: estado_entrega_actividad_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_entrega_actividad_enum AS ENUM (
    'PENDIENTE',
    'ENTREGADA',
    'ENTREGADA_TARDE',
    'NO_ENTREGADA',
    'CALIFICADA'
);


ALTER TYPE optimscul.estado_entrega_actividad_enum OWNER TO postgres;

--
-- TOC entry 999 (class 1247 OID 23298)
-- Name: estado_estudiante_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_estudiante_enum AS ENUM (
    'ACTIVO',
    'RETIRADO',
    'GRADUADO',
    'INACTIVO'
);


ALTER TYPE optimscul.estado_estudiante_enum OWNER TO postgres;

--
-- TOC entry 975 (class 1247 OID 23210)
-- Name: estado_grupo_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_grupo_enum AS ENUM (
    'ACTIVO',
    'INACTIVO',
    'CERRADO'
);


ALTER TYPE optimscul.estado_grupo_enum OWNER TO postgres;

--
-- TOC entry 957 (class 1247 OID 23138)
-- Name: estado_institucion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_institucion_enum AS ENUM (
    'ACTIVA',
    'INACTIVA',
    'SUSPENDIDA',
    'PRUEBA'
);


ALTER TYPE optimscul.estado_institucion_enum OWNER TO postgres;

--
-- TOC entry 1011 (class 1247 OID 23332)
-- Name: estado_matricula_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_matricula_enum AS ENUM (
    'PREMATRICULA',
    'MATRICULADO',
    'CANCELADO',
    'RETIRADO',
    'FINALIZADO'
);


ALTER TYPE optimscul.estado_matricula_enum OWNER TO postgres;

--
-- TOC entry 1056 (class 1247 OID 23538)
-- Name: estado_notificacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_notificacion_enum AS ENUM (
    'PENDIENTE',
    'ENVIADA',
    'LEIDA',
    'FALLIDA'
);


ALTER TYPE optimscul.estado_notificacion_enum OWNER TO postgres;

--
-- TOC entry 1026 (class 1247 OID 23408)
-- Name: estado_observacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_observacion_enum AS ENUM (
    'ABIERTA',
    'EN_SEGUIMIENTO',
    'CERRADA',
    'ANULADA'
);


ALTER TYPE optimscul.estado_observacion_enum OWNER TO postgres;

--
-- TOC entry 972 (class 1247 OID 23200)
-- Name: estado_periodo_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_periodo_enum AS ENUM (
    'PLANEADO',
    'ACTIVO',
    'CERRADO',
    'ANULADO'
);


ALTER TYPE optimscul.estado_periodo_enum OWNER TO postgres;

--
-- TOC entry 1020 (class 1247 OID 23382)
-- Name: estado_postulacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.estado_postulacion_enum OWNER TO postgres;

--
-- TOC entry 1002 (class 1247 OID 23308)
-- Name: estado_profesor_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_profesor_enum AS ENUM (
    'ACTIVO',
    'RETIRADO',
    'INACTIVO'
);


ALTER TYPE optimscul.estado_profesor_enum OWNER TO postgres;

--
-- TOC entry 942 (class 1247 OID 23090)
-- Name: estado_registro_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_registro_enum AS ENUM (
    'ACTIVO',
    'INACTIVO'
);


ALTER TYPE optimscul.estado_registro_enum OWNER TO postgres;

--
-- TOC entry 981 (class 1247 OID 23228)
-- Name: estado_sesion_clase_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_sesion_clase_enum AS ENUM (
    'PROGRAMADA',
    'DICTADA',
    'CANCELADA',
    'REPROGRAMADA'
);


ALTER TYPE optimscul.estado_sesion_clase_enum OWNER TO postgres;

--
-- TOC entry 954 (class 1247 OID 23128)
-- Name: estado_usuario_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.estado_usuario_enum AS ENUM (
    'ACTIVO',
    'INACTIVO',
    'BLOQUEADO',
    'PENDIENTE_ACTIVACION'
);


ALTER TYPE optimscul.estado_usuario_enum OWNER TO postgres;

--
-- TOC entry 1044 (class 1247 OID 23476)
-- Name: metodo_pago_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.metodo_pago_enum OWNER TO postgres;

--
-- TOC entry 1035 (class 1247 OID 23448)
-- Name: modo_aplicacion_beneficio_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.modo_aplicacion_beneficio_enum AS ENUM (
    'PORCENTAJE',
    'VALOR_FIJO'
);


ALTER TYPE optimscul.modo_aplicacion_beneficio_enum OWNER TO postgres;

--
-- TOC entry 1047 (class 1247 OID 23492)
-- Name: modulo_documento_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.modulo_documento_enum OWNER TO postgres;

--
-- TOC entry 963 (class 1247 OID 23160)
-- Name: nivel_academico_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.nivel_academico_enum AS ENUM (
    'PREESCOLAR',
    'PRIMARIA',
    'SECUNDARIA',
    'MEDIA',
    'TECNICA',
    'OTRO'
);


ALTER TYPE optimscul.nivel_academico_enum OWNER TO postgres;

--
-- TOC entry 996 (class 1247 OID 23288)
-- Name: resultado_academico_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.resultado_academico_enum AS ENUM (
    'APROBADO',
    'REPROBADO',
    'PENDIENTE',
    'PROMOVIDO_ANTICIPADO'
);


ALTER TYPE optimscul.resultado_academico_enum OWNER TO postgres;

--
-- TOC entry 1023 (class 1247 OID 23398)
-- Name: severidad_observacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.severidad_observacion_enum AS ENUM (
    'BAJA',
    'MEDIA',
    'ALTA',
    'CRITICA'
);


ALTER TYPE optimscul.severidad_observacion_enum OWNER TO postgres;

--
-- TOC entry 945 (class 1247 OID 23096)
-- Name: sexo_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.sexo_enum AS ENUM (
    'MASCULINO',
    'FEMENINO',
    'OTRO',
    'NO_ESPECIFICA'
);


ALTER TYPE optimscul.sexo_enum OWNER TO postgres;

--
-- TOC entry 987 (class 1247 OID 23248)
-- Name: tipo_actividad_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.tipo_actividad_enum OWNER TO postgres;

--
-- TOC entry 984 (class 1247 OID 23238)
-- Name: tipo_asistencia_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.tipo_asistencia_enum AS ENUM (
    'PRESENTE',
    'AUSENTE',
    'TARDE',
    'JUSTIFICADA'
);


ALTER TYPE optimscul.tipo_asistencia_enum OWNER TO postgres;

--
-- TOC entry 1032 (class 1247 OID 23438)
-- Name: tipo_beneficio_financiero_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.tipo_beneficio_financiero_enum AS ENUM (
    'DESCUENTO',
    'BECA',
    'SUBSIDIO',
    'EXONERACION'
);


ALTER TYPE optimscul.tipo_beneficio_financiero_enum OWNER TO postgres;

--
-- TOC entry 951 (class 1247 OID 23122)
-- Name: tipo_contexto_usuario_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.tipo_contexto_usuario_enum AS ENUM (
    'PLATAFORMA',
    'INSTITUCION'
);


ALTER TYPE optimscul.tipo_contexto_usuario_enum OWNER TO postgres;

--
-- TOC entry 948 (class 1247 OID 23106)
-- Name: tipo_documento_persona_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.tipo_documento_persona_enum OWNER TO postgres;

--
-- TOC entry 960 (class 1247 OID 23148)
-- Name: tipo_institucion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.tipo_institucion_enum AS ENUM (
    'COLEGIO',
    'JARDIN',
    'INSTITUTO',
    'ACADEMIA',
    'OTRA'
);


ALTER TYPE optimscul.tipo_institucion_enum OWNER TO postgres;

--
-- TOC entry 1008 (class 1247 OID 23322)
-- Name: tipo_matricula_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.tipo_matricula_enum AS ENUM (
    'NUEVA',
    'RENOVACION',
    'TRASLADO',
    'REINTEGRO'
);


ALTER TYPE optimscul.tipo_matricula_enum OWNER TO postgres;

--
-- TOC entry 1050 (class 1247 OID 23514)
-- Name: tipo_notificacion_enum; Type: TYPE; Schema: optimscul; Owner: postgres
--

CREATE TYPE optimscul.tipo_notificacion_enum AS ENUM (
    'ACADEMICA',
    'FINANCIERA',
    'ADMISIONES',
    'CONVIVENCIA',
    'SISTEMA',
    'GENERAL'
);


ALTER TYPE optimscul.tipo_notificacion_enum OWNER TO postgres;

--
-- TOC entry 1014 (class 1247 OID 23344)
-- Name: tipo_parentesco_enum; Type: TYPE; Schema: optimscul; Owner: postgres
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


ALTER TYPE optimscul.tipo_parentesco_enum OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 254 (class 1259 OID 24703)
-- Name: actividad_academica; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.actividad_academica OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 24341)
-- Name: acudiente; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.acudiente OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 23910)
-- Name: anio_lectivo; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.anio_lectivo OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 24053)
-- Name: area_academica; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.area_academica OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 24079)
-- Name: asignatura; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.asignatura OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 24666)
-- Name: asistencia_clase; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.asistencia_clase OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 25424)
-- Name: auditoria_evento; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.auditoria_evento OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 25137)
-- Name: beneficio_financiero; Type: TABLE; Schema: optimscul; Owner: postgres
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
    permite_convivir_con_mejor_beneficio boolean DEFAULT false CONSTRAINT beneficio_financiero_permite_convivir_con_mejor_benefi_not_null NOT NULL,
    created_by uuid,
    updated_by uuid,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE optimscul.beneficio_financiero OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 24790)
-- Name: calificacion_actividad; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.calificacion_actividad OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 24147)
-- Name: carga_academica; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.carga_academica OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 25092)
-- Name: concepto_cobro; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.concepto_cobro OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 23843)
-- Name: configuracion_academica; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.configuracion_academica OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 25249)
-- Name: cuenta_cobro; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.cuenta_cobro OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 25320)
-- Name: cuenta_cobro_beneficio; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.cuenta_cobro_beneficio OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 24922)
-- Name: decision_academica_estudiante; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.decision_academica_estudiante OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 25397)
-- Name: documento; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.documento OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 24759)
-- Name: entrega_actividad; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.entrega_actividad OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 23882)
-- Name: escala_valorativa; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.escala_valorativa OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 24307)
-- Name: estudiante; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.estudiante OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 24391)
-- Name: estudiante_acudiente; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.estudiante_acudiente OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 25189)
-- Name: estudiante_beneficio_financiero; Type: TABLE; Schema: optimscul; Owner: postgres
--

CREATE TABLE optimscul.estudiante_beneficio_financiero (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id uuid NOT NULL,
    estudiante_id uuid NOT NULL,
    beneficio_financiero_id uuid CONSTRAINT estudiante_beneficio_financier_beneficio_financiero_id_not_null NOT NULL,
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


ALTER TABLE optimscul.estudiante_beneficio_financiero OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 23977)
-- Name: grado; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.grado OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 24003)
-- Name: grupo; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.grupo OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 24191)
-- Name: horario_carga; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.horario_carga OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 23567)
-- Name: institucion; Type: TABLE; Schema: optimscul; Owner: postgres
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
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE optimscul.institucion OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 23624)
-- Name: jornada; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.jornada OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 24433)
-- Name: matricula; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.matricula OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 25451)
-- Name: notificacion; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.notificacion OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 25479)
-- Name: notificacion_destinatario; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.notificacion_destinatario OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 24993)
-- Name: observacion_estudiante; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.observacion_estudiante OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 25361)
-- Name: pago; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.pago OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 24372)
-- Name: parentesco; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.parentesco OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 23941)
-- Name: periodo_academico; Type: TABLE; Schema: optimscul; Owner: postgres
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
    CONSTRAINT chk_periodo_fechas CHECK ((fecha_inicio <= fecha_fin))
);


ALTER TABLE optimscul.periodo_academico OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 23732)
-- Name: permiso; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.permiso OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 23650)
-- Name: persona; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.persona OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 24505)
-- Name: postulacion; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.postulacion OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 24612)
-- Name: postulacion_acudiente; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.postulacion_acudiente OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 24588)
-- Name: postulacion_persona; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.postulacion_persona OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 24273)
-- Name: profesor; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.profesor OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 24127)
-- Name: profesor_grupo_director; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.profesor_grupo_director OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 24882)
-- Name: resumen_anual_estudiante; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.resumen_anual_estudiante OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 24833)
-- Name: resumen_periodo_estudiante; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.resumen_periodo_estudiante OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 23711)
-- Name: rol; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.rol OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 23752)
-- Name: rol_permiso; Type: TABLE; Schema: optimscul; Owner: postgres
--

CREATE TABLE optimscul.rol_permiso (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    rol_id uuid NOT NULL,
    permiso_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE optimscul.rol_permiso OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 23594)
-- Name: sede; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.sede OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 25067)
-- Name: seguimiento_observacion; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.seguimiento_observacion OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 24642)
-- Name: seguimiento_postulacion; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.seguimiento_postulacion OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 24226)
-- Name: sesion_clase; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.sesion_clase OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 24965)
-- Name: tipo_observacion; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.tipo_observacion OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 23673)
-- Name: usuario; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.usuario OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 23777)
-- Name: usuario_institucion; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.usuario_institucion OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 23808)
-- Name: usuario_rol; Type: TABLE; Schema: optimscul; Owner: postgres
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


ALTER TABLE optimscul.usuario_rol OWNER TO postgres;

--
-- TOC entry 6070 (class 0 OID 24703)
-- Dependencies: 254
-- Data for Name: actividad_academica; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.actividad_academica (id, institucion_id, carga_academica_id, periodo_academico_id, sesion_clase_id, tipo, titulo, descripcion, fecha_publicacion, fecha_entrega, fecha_cierre, porcentaje, nota_maxima, permite_entrega_tardia, estado, creada_por_usuario_id, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6061 (class 0 OID 24341)
-- Dependencies: 245
-- Data for Name: acudiente; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.acudiente (id, institucion_id, persona_id, ocupacion, empresa, estado, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6049 (class 0 OID 23910)
-- Dependencies: 233
-- Data for Name: anio_lectivo; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.anio_lectivo (id, institucion_id, anio, nombre, descripcion, fecha_inicio, fecha_fin, estado, es_actual, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6053 (class 0 OID 24053)
-- Dependencies: 237
-- Data for Name: area_academica; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.area_academica (id, institucion_id, codigo, nombre, descripcion, activa, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6054 (class 0 OID 24079)
-- Dependencies: 238
-- Data for Name: asignatura; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.asignatura (id, institucion_id, area_id, codigo, nombre, descripcion, intensidad_horaria_semanal, requiere_calificacion, requiere_recuperacion, es_comportamiento, activa, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6069 (class 0 OID 24666)
-- Dependencies: 253
-- Data for Name: asistencia_clase; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.asistencia_clase (id, sesion_clase_id, estudiante_id, tipo_asistencia, observacion, justificacion, minutos_tarde, registrada_por_usuario_id, fecha_registro, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6086 (class 0 OID 25424)
-- Dependencies: 270
-- Data for Name: auditoria_evento; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.auditoria_evento (id, institucion_id, usuario_id, modulo, entidad, entidad_id, accion, descripcion, valores_antes, valores_despues, ip, user_agent, created_at) FROM stdin;
\.


--
-- TOC entry 6080 (class 0 OID 25137)
-- Dependencies: 264
-- Data for Name: beneficio_financiero; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.beneficio_financiero (id, institucion_id, codigo, nombre, descripcion, tipo, porcentaje, valor_fijo, concepto_cobro_id, activo, requiere_aprobacion, acumulable, prioridad, modo_aplicacion, permite_convivir_con_mejor_beneficio, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6072 (class 0 OID 24790)
-- Dependencies: 256
-- Data for Name: calificacion_actividad; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.calificacion_actividad (id, actividad_id, estudiante_id, entrega_actividad_id, nota_obtenida, observacion_docente, calificada_por_usuario_id, fecha_calificacion, es_recuperacion, anulada, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6056 (class 0 OID 24147)
-- Dependencies: 240
-- Data for Name: carga_academica; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.carga_academica (id, institucion_id, anio_lectivo_id, profesor_id, grupo_id, asignatura_id, intensidad_horaria_semanal, fecha_inicio, fecha_fin, estado, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6079 (class 0 OID 25092)
-- Dependencies: 263
-- Data for Name: concepto_cobro; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.concepto_cobro (id, institucion_id, codigo, nombre, descripcion, categoria, valor_base, es_recurrente, requiere_vencimiento, permite_descuento, permite_recargo, activo, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6047 (class 0 OID 23843)
-- Dependencies: 231
-- Data for Name: configuracion_academica; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.configuracion_academica (id, institucion_id, usa_periodos, numero_periodos, nota_minima_aprobacion, nota_minima, nota_maxima, decimales_nota, usa_recuperacion, asistencia_por_clase, maneja_comportamiento, maneja_puestos, porcentaje_inasistencia_reprobacion, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6082 (class 0 OID 25249)
-- Dependencies: 266
-- Data for Name: cuenta_cobro; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.cuenta_cobro (id, institucion_id, estudiante_id, concepto_cobro_id, anio_lectivo_id, periodo_academico_id, codigo, fecha_emision, fecha_vencimiento, descripcion, valor_base, descuento, recargo, total, saldo, estado, observaciones, anulado_por_usuario_id, fecha_anulacion, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6083 (class 0 OID 25320)
-- Dependencies: 267
-- Data for Name: cuenta_cobro_beneficio; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.cuenta_cobro_beneficio (id, institucion_id, cuenta_cobro_id, estudiante_beneficio_financiero_id, beneficio_financiero_id, tipo, porcentaje_aplicado, valor_fijo_aplicado, valor_descuento_aplicado, observaciones, created_by, created_at) FROM stdin;
\.


--
-- TOC entry 6075 (class 0 OID 24922)
-- Dependencies: 259
-- Data for Name: decision_academica_estudiante; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.decision_academica_estudiante (id, institucion_id, anio_lectivo_id, estudiante_id, resultado, observaciones, fecha_decision, usuario_id, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6085 (class 0 OID 25397)
-- Dependencies: 269
-- Data for Name: documento; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.documento (id, institucion_id, modulo, entidad_id, nombre_archivo, nombre_original, url_archivo, mime_type, tamano_bytes, descripcion, subido_por_usuario_id, created_at) FROM stdin;
\.


--
-- TOC entry 6071 (class 0 OID 24759)
-- Dependencies: 255
-- Data for Name: entrega_actividad; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.entrega_actividad (id, actividad_id, estudiante_id, fecha_entrega, comentario_estudiante, archivo_url, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6048 (class 0 OID 23882)
-- Dependencies: 232
-- Data for Name: escala_valorativa; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.escala_valorativa (id, institucion_id, nombre, abreviatura, nota_minima, nota_maxima, aprueba, orden, activa, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6060 (class 0 OID 24307)
-- Dependencies: 244
-- Data for Name: estudiante; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.estudiante (id, institucion_id, persona_id, codigo_estudiante, fecha_ingreso, fecha_retiro, estado, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6063 (class 0 OID 24391)
-- Dependencies: 247
-- Data for Name: estudiante_acudiente; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.estudiante_acudiente (id, estudiante_id, acudiente_id, parentesco, parentesco_id, es_principal, autorizado_recogida, autorizado_info_academica, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6081 (class 0 OID 25189)
-- Dependencies: 265
-- Data for Name: estudiante_beneficio_financiero; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.estudiante_beneficio_financiero (id, institucion_id, estudiante_id, beneficio_financiero_id, anio_lectivo_id, periodo_academico_id, fecha_inicio, fecha_fin, estado, observaciones, aprobado_por_usuario_id, fecha_aprobacion, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6051 (class 0 OID 23977)
-- Dependencies: 235
-- Data for Name: grado; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.grado (id, institucion_id, codigo, nombre, nivel, orden, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6052 (class 0 OID 24003)
-- Dependencies: 236
-- Data for Name: grupo; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.grupo (id, institucion_id, anio_lectivo_id, sede_id, jornada_id, grado_id, codigo, nombre, cupo_maximo, estado, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6057 (class 0 OID 24191)
-- Dependencies: 241
-- Data for Name: horario_carga; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.horario_carga (id, institucion_id, carga_academica_id, sede_id, dia_semana, hora_inicio, hora_fin, aula, activo, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6037 (class 0 OID 23567)
-- Dependencies: 221
-- Data for Name: institucion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.institucion (id, codigo, nombre, nombre_corto, tipo_institucion, nit, dane, resolucion_funcionamiento, descripcion, correo_contacto, telefono_contacto, sitio_web, direccion_principal, ciudad, departamento, pais, logo_url, zona_horaria, moneda, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6039 (class 0 OID 23624)
-- Dependencies: 223
-- Data for Name: jornada; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.jornada (id, institucion_id, codigo, nombre, descripcion, hora_inicio, hora_fin, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6064 (class 0 OID 24433)
-- Dependencies: 248
-- Data for Name: matricula; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.matricula (id, institucion_id, estudiante_id, anio_lectivo_id, grupo_id, codigo_matricula, tipo, estado, fecha_matricula, fecha_estado, observaciones, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6087 (class 0 OID 25451)
-- Dependencies: 271
-- Data for Name: notificacion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.notificacion (id, institucion_id, tipo, titulo, mensaje, modulo_relacionado, entidad_relacionada_id, prioridad, programada_para, created_by, created_at) FROM stdin;
\.


--
-- TOC entry 6088 (class 0 OID 25479)
-- Dependencies: 272
-- Data for Name: notificacion_destinatario; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.notificacion_destinatario (id, notificacion_id, usuario_id, canal, estado, enviada_en, leida_en, error_envio, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6077 (class 0 OID 24993)
-- Dependencies: 261
-- Data for Name: observacion_estudiante; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.observacion_estudiante (id, institucion_id, estudiante_id, anio_lectivo_id, periodo_academico_id, grupo_id, carga_academica_id, tipo_observacion_id, titulo, descripcion, fecha_evento, severidad, estado, visible_acudiente, requiere_seguimiento, cerrada_en, creada_por_usuario_id, cerrada_por_usuario_id, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6084 (class 0 OID 25361)
-- Dependencies: 268
-- Data for Name: pago; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.pago (id, institucion_id, cuenta_cobro_id, fecha_pago, valor, metodo, referencia_pago, comprobante_url, observaciones, registrado_por_usuario_id, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6062 (class 0 OID 24372)
-- Dependencies: 246
-- Data for Name: parentesco; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.parentesco (id, codigo, nombre, descripcion, activo, created_at, updated_at) FROM stdin;
5d049343-acf5-481d-b174-51f425153d9f	MADRE	Madre	Madre del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
927b5567-9fd3-444a-9005-0490706955ab	PADRE	Padre	Padre del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
8d5fd4f0-42f0-4e62-a3f9-46b33c16fce6	ABUELA	Abuela	Abuela del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
5e1fe199-2dac-4f1c-b488-ff4a11c045fa	ABUELO	Abuelo	Abuelo del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
c41d85ee-7445-4517-b381-fbdb74feb8f7	TIA	Tía	Tía del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
d685db35-bdaf-46e2-8155-c6de307dae7e	TIO	Tío	Tío del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
5d17b799-4ac5-407c-9820-cdca7562ef49	HERMANA	Hermana	Hermana del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
31d3db20-3abf-4c5f-b4c5-742bdb50d1ca	HERMANO	Hermano	Hermano del estudiante	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
30aef351-5ff5-43a9-9828-c1a63f2cdfc7	ACUDIENTE_LEGAL	Acudiente legal	Acudiente legal autorizado	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
688ed549-39a1-4193-8495-3cad4d2bdc2f	OTRO	Otro	Otro parentesco	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
\.


--
-- TOC entry 6050 (class 0 OID 23941)
-- Dependencies: 234
-- Data for Name: periodo_academico; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.periodo_academico (id, institucion_id, anio_lectivo_id, numero, nombre, descripcion, fecha_inicio, fecha_fin, peso, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6043 (class 0 OID 23732)
-- Dependencies: 227
-- Data for Name: permiso; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.permiso (id, codigo, nombre, descripcion, modulo, activo, created_at, updated_at) FROM stdin;
7a29a4b4-aadc-47ed-bc0e-d24c09852105	institucion.ver	Ver institución	Consultar información de la institución	NUCLEO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
72e8c827-f49d-4a3a-a645-c6137b1ac857	institucion.editar	Editar institución	Modificar información de la institución	NUCLEO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
59352aee-e6ed-426c-a0d7-02ffce749e11	admision.ver	Ver postulaciones	Consultar postulaciones	ADMISIONES	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
827cb60b-b7da-4665-be48-ad7722a93389	admision.crear	Crear postulaciones	Registrar postulaciones	ADMISIONES	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
589afa0a-719b-47fc-a4d9-0e406fb7a2ab	admision.aprobar	Aprobar postulaciones	Aprobar postulaciones	ADMISIONES	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
42d529e6-c71b-4ae8-9198-028d76518b8a	admision.rechazar	Rechazar postulaciones	Rechazar postulaciones	ADMISIONES	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
5a3a2fc4-6c6f-4821-a77e-e2e606becb69	matricula.ver	Ver matrículas	Consultar matrículas	MATRICULA	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
be677572-fb1e-4928-b793-6be9123f42f4	matricula.crear	Crear matrículas	Registrar matrículas	MATRICULA	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
716e4ab1-93fd-4a40-aa64-1e0a64c6995c	matricula.editar	Editar matrículas	Editar matrículas	MATRICULA	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
666bee0c-13a5-4a9f-9316-35bfa90f71d5	academico.ver	Ver académico	Consultar módulos académicos	ACADEMICO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
834251d8-dfba-46ea-8045-b65095be8b8a	academico.calificar	Calificar actividades	Registrar notas	ACADEMICO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
38c56b14-b380-4373-9858-beb0cd9cd091	academico.asistencia	Registrar asistencia	Registrar asistencia por clase	ACADEMICO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
2136a1fc-7c69-4d9b-a3ca-68e81ef59794	convivencia.ver	Ver observaciones	Consultar observaciones	CONVIVENCIA	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
be6b4506-3ab9-4360-a124-435a9107a126	convivencia.crear	Crear observaciones	Registrar observaciones	CONVIVENCIA	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
42b1d666-4a6e-4d00-9201-90bc8303d933	convivencia.cerrar	Cerrar observaciones	Cerrar observaciones	CONVIVENCIA	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
7a473ccf-e235-4d4b-8bfd-8d4c2ae6e98f	financiero.ver	Ver financiero	Consultar información financiera	FINANCIERO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
34d95d3b-b2ce-43bf-a54b-605cb23f2aa0	financiero.cuenta_cobro	Gestionar cuentas de cobro	Crear/editar cuentas de cobro	FINANCIERO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
63305812-2955-467c-91e9-783792a1bb7c	financiero.pago	Registrar pagos	Registrar pagos	FINANCIERO	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
e83ca48e-f62a-4905-9a7a-52e8347b2379	usuarios.ver	Ver usuarios	Consultar usuarios	SEGURIDAD	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
e20c127d-472e-4f29-9b8f-a273eb453a15	usuarios.editar	Editar usuarios	Gestionar usuarios	SEGURIDAD	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
00cad538-8f6b-447d-989c-0965b657547e	roles.asignar	Asignar roles	Asignar roles a usuarios	SEGURIDAD	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
\.


--
-- TOC entry 6040 (class 0 OID 23650)
-- Dependencies: 224
-- Data for Name: persona; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.persona (id, tipo_documento, numero_documento, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, fecha_nacimiento, sexo, nacionalidad, telefono, telefono_alternativo, correo, direccion, barrio, ciudad, departamento, pais, foto_url, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6065 (class 0 OID 24505)
-- Dependencies: 249
-- Data for Name: postulacion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.postulacion (id, institucion_id, anio_lectivo_id, sede_id, jornada_id, grado_aspira_id, codigo, fecha_postulacion, canal, estado, observaciones, observaciones_internas, cupo_reservado, aprobada_por_usuario_id, fecha_aprobacion, rechazada_por_usuario_id, fecha_rechazo, motivo_rechazo, convertida_en_estudiante_id, convertida_en_matricula_id, fecha_conversion, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6067 (class 0 OID 24612)
-- Dependencies: 251
-- Data for Name: postulacion_acudiente; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.postulacion_acudiente (id, postulacion_id, tipo_documento, numero_documento, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, parentesco, telefono, telefono_alternativo, correo, direccion, ocupacion, empresa, es_principal, autorizado_recogida, autorizado_info_academica, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6066 (class 0 OID 24588)
-- Dependencies: 250
-- Data for Name: postulacion_persona; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.postulacion_persona (id, postulacion_id, tipo_documento, numero_documento, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, fecha_nacimiento, sexo, nacionalidad, correo, telefono, direccion, ciudad, departamento, pais, colegio_procedencia, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6059 (class 0 OID 24273)
-- Dependencies: 243
-- Data for Name: profesor; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.profesor (id, institucion_id, persona_id, codigo_profesor, especialidad, titulo_profesional, fecha_vinculacion, fecha_retiro, estado, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6055 (class 0 OID 24127)
-- Dependencies: 239
-- Data for Name: profesor_grupo_director; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.profesor_grupo_director (id, grupo_id, profesor_id, fecha_inicio, fecha_fin, activo, created_at) FROM stdin;
\.


--
-- TOC entry 6074 (class 0 OID 24882)
-- Dependencies: 258
-- Data for Name: resumen_anual_estudiante; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.resumen_anual_estudiante (id, institucion_id, anio_lectivo_id, estudiante_id, asignatura_id, nota_final, aprueba, observaciones, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6073 (class 0 OID 24833)
-- Dependencies: 257
-- Data for Name: resumen_periodo_estudiante; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.resumen_periodo_estudiante (id, institucion_id, anio_lectivo_id, periodo_academico_id, estudiante_id, carga_academica_id, nota_final, observacion, recuperacion_aplica, nota_recuperacion, nota_definitiva, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6042 (class 0 OID 23711)
-- Dependencies: 226
-- Data for Name: rol; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.rol (id, codigo, nombre, descripcion, es_sistema, activo, created_at, updated_at) FROM stdin;
bd1c7d48-1401-4764-9d1c-fcbb65c3d7bf	SUPER_ADMIN	Super Administrador	Administrador global de la plataforma	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
ad54d611-d1b8-4560-b885-ac968011fe44	ADMIN_INSTITUCION	Administrador de Institución	Administra configuración y operación de la institución	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
db263826-b8b8-4ef2-8803-c7438632c172	RECTOR	Rector	Rol directivo de la institución	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
3993c8af-f275-4c8a-908b-87b381533365	SECRETARIA	Secretaría	Gestiona matrícula, admisiones y soporte académico	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
d1442090-3cad-40f8-840b-a916d6a779c5	DOCENTE	Docente	Gestiona clases, asistencia, actividades y notas	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
391c29f7-4274-4a24-8c94-41dc80eea756	ACUDIENTE	Acudiente	Consulta información académica y financiera del estudiante	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
934ad7bf-455e-4c88-941c-653867f42dea	ESTUDIANTE	Estudiante	Consulta su información académica y financiera	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
7ba6a1ab-0764-4ee4-b05d-3cbea08647fb	TESORERIA	Tesorería	Gestiona cuentas de cobro, pagos y beneficios	t	t	2026-06-25 16:31:47.396931	2026-06-25 16:31:47.396931
\.


--
-- TOC entry 6044 (class 0 OID 23752)
-- Dependencies: 228
-- Data for Name: rol_permiso; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.rol_permiso (id, rol_id, permiso_id, created_at) FROM stdin;
\.


--
-- TOC entry 6038 (class 0 OID 23594)
-- Dependencies: 222
-- Data for Name: sede; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.sede (id, institucion_id, codigo, nombre, descripcion, direccion, telefono, correo, ciudad, departamento, pais, principal, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6078 (class 0 OID 25067)
-- Dependencies: 262
-- Data for Name: seguimiento_observacion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.seguimiento_observacion (id, observacion_id, comentario, estado_anterior, estado_nuevo, creado_por_usuario_id, created_at) FROM stdin;
\.


--
-- TOC entry 6068 (class 0 OID 24642)
-- Dependencies: 252
-- Data for Name: seguimiento_postulacion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.seguimiento_postulacion (id, postulacion_id, comentario, estado_anterior, estado_nuevo, creado_por_usuario_id, created_at) FROM stdin;
\.


--
-- TOC entry 6058 (class 0 OID 24226)
-- Dependencies: 242
-- Data for Name: sesion_clase; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.sesion_clase (id, institucion_id, carga_academica_id, horario_carga_id, fecha, hora_inicio, hora_fin, tema, descripcion, estado, fue_reprogramada, created_by, updated_by, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6076 (class 0 OID 24965)
-- Dependencies: 260
-- Data for Name: tipo_observacion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.tipo_observacion (id, institucion_id, codigo, nombre, descripcion, severidad, activa, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6041 (class 0 OID 23673)
-- Dependencies: 225
-- Data for Name: usuario; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.usuario (id, persona_id, username, password_hash, email_login, tipo_contexto, estado, requiere_cambio_password, email_verificado, doble_factor_habilitado, intentos_fallidos, bloqueado_hasta, ultimo_login, ultimo_cambio_password, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6045 (class 0 OID 23777)
-- Dependencies: 229
-- Data for Name: usuario_institucion; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.usuario_institucion (id, usuario_id, institucion_id, es_principal, activo, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 6046 (class 0 OID 23808)
-- Dependencies: 230
-- Data for Name: usuario_rol; Type: TABLE DATA; Schema: optimscul; Owner: postgres
--

COPY optimscul.usuario_rol (id, usuario_id, institucion_id, rol_id, activo, fecha_inicio, fecha_fin, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 5653 (class 2606 OID 24726)
-- Name: actividad_academica actividad_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_pkey PRIMARY KEY (id);


--
-- TOC entry 5604 (class 2606 OID 24357)
-- Name: acudiente acudiente_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT acudiente_pkey PRIMARY KEY (id);


--
-- TOC entry 5537 (class 2606 OID 23932)
-- Name: anio_lectivo anio_lectivo_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.anio_lectivo
    ADD CONSTRAINT anio_lectivo_pkey PRIMARY KEY (id);


--
-- TOC entry 5560 (class 2606 OID 24070)
-- Name: area_academica area_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.area_academica
    ADD CONSTRAINT area_academica_pkey PRIMARY KEY (id);


--
-- TOC entry 5565 (class 2606 OID 24102)
-- Name: asignatura asignatura_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_pkey PRIMARY KEY (id);


--
-- TOC entry 5647 (class 2606 OID 24683)
-- Name: asistencia_clase asistencia_clase_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_pkey PRIMARY KEY (id);


--
-- TOC entry 5727 (class 2606 OID 25437)
-- Name: auditoria_evento auditoria_evento_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.auditoria_evento
    ADD CONSTRAINT auditoria_evento_pkey PRIMARY KEY (id);


--
-- TOC entry 5702 (class 2606 OID 25165)
-- Name: beneficio_financiero beneficio_financiero_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_pkey PRIMARY KEY (id);


--
-- TOC entry 5663 (class 2606 OID 24808)
-- Name: calificacion_actividad calificacion_actividad_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_pkey PRIMARY KEY (id);


--
-- TOC entry 5575 (class 2606 OID 24166)
-- Name: carga_academica carga_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_pkey PRIMARY KEY (id);


--
-- TOC entry 5697 (class 2606 OID 25118)
-- Name: concepto_cobro concepto_cobro_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 5530 (class 2606 OID 23876)
-- Name: configuracion_academica configuracion_academica_institucion_id_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.configuracion_academica
    ADD CONSTRAINT configuracion_academica_institucion_id_key UNIQUE (institucion_id);


--
-- TOC entry 5532 (class 2606 OID 23874)
-- Name: configuracion_academica configuracion_academica_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.configuracion_academica
    ADD CONSTRAINT configuracion_academica_pkey PRIMARY KEY (id);


--
-- TOC entry 5716 (class 2606 OID 25334)
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_pkey PRIMARY KEY (id);


--
-- TOC entry 5710 (class 2606 OID 25275)
-- Name: cuenta_cobro cuenta_cobro_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_pkey PRIMARY KEY (id);


--
-- TOC entry 5680 (class 2606 OID 24941)
-- Name: decision_academica_estudiante decision_academica_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_pkey PRIMARY KEY (id);


--
-- TOC entry 5723 (class 2606 OID 25411)
-- Name: documento documento_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- TOC entry 5657 (class 2606 OID 24775)
-- Name: entrega_actividad entrega_actividad_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT entrega_actividad_pkey PRIMARY KEY (id);


--
-- TOC entry 5534 (class 2606 OID 23902)
-- Name: escala_valorativa escala_valorativa_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.escala_valorativa
    ADD CONSTRAINT escala_valorativa_pkey PRIMARY KEY (id);


--
-- TOC entry 5614 (class 2606 OID 24413)
-- Name: estudiante_acudiente estudiante_acudiente_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_pkey PRIMARY KEY (id);


--
-- TOC entry 5707 (class 2606 OID 25207)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_pkey PRIMARY KEY (id);


--
-- TOC entry 5596 (class 2606 OID 24324)
-- Name: estudiante estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT estudiante_pkey PRIMARY KEY (id);


--
-- TOC entry 5548 (class 2606 OID 23994)
-- Name: grado grado_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grado
    ADD CONSTRAINT grado_pkey PRIMARY KEY (id);


--
-- TOC entry 5553 (class 2606 OID 24022)
-- Name: grupo grupo_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_pkey PRIMARY KEY (id);


--
-- TOC entry 5581 (class 2606 OID 24209)
-- Name: horario_carga horario_carga_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_pkey PRIMARY KEY (id);


--
-- TOC entry 5476 (class 2606 OID 23593)
-- Name: institucion institucion_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.institucion
    ADD CONSTRAINT institucion_codigo_key UNIQUE (codigo);


--
-- TOC entry 5478 (class 2606 OID 23591)
-- Name: institucion institucion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.institucion
    ADD CONSTRAINT institucion_pkey PRIMARY KEY (id);


--
-- TOC entry 5486 (class 2606 OID 23641)
-- Name: jornada jornada_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.jornada
    ADD CONSTRAINT jornada_pkey PRIMARY KEY (id);


--
-- TOC entry 5624 (class 2606 OID 24455)
-- Name: matricula matricula_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_pkey PRIMARY KEY (id);


--
-- TOC entry 5737 (class 2606 OID 25497)
-- Name: notificacion_destinatario notificacion_destinatario_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT notificacion_destinatario_pkey PRIMARY KEY (id);


--
-- TOC entry 5733 (class 2606 OID 25467)
-- Name: notificacion notificacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion
    ADD CONSTRAINT notificacion_pkey PRIMARY KEY (id);


--
-- TOC entry 5692 (class 2606 OID 25019)
-- Name: observacion_estudiante observacion_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_pkey PRIMARY KEY (id);


--
-- TOC entry 5721 (class 2606 OID 25379)
-- Name: pago pago_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_pkey PRIMARY KEY (id);


--
-- TOC entry 5610 (class 2606 OID 24390)
-- Name: parentesco parentesco_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.parentesco
    ADD CONSTRAINT parentesco_codigo_key UNIQUE (codigo);


--
-- TOC entry 5612 (class 2606 OID 24388)
-- Name: parentesco parentesco_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.parentesco
    ADD CONSTRAINT parentesco_pkey PRIMARY KEY (id);


--
-- TOC entry 5544 (class 2606 OID 23962)
-- Name: periodo_academico periodo_academico_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT periodo_academico_pkey PRIMARY KEY (id);


--
-- TOC entry 5507 (class 2606 OID 23751)
-- Name: permiso permiso_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.permiso
    ADD CONSTRAINT permiso_codigo_key UNIQUE (codigo);


--
-- TOC entry 5509 (class 2606 OID 23749)
-- Name: permiso permiso_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.permiso
    ADD CONSTRAINT permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 5492 (class 2606 OID 23668)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (id);


--
-- TOC entry 5642 (class 2606 OID 24635)
-- Name: postulacion_acudiente postulacion_acudiente_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion_acudiente
    ADD CONSTRAINT postulacion_acudiente_pkey PRIMARY KEY (id);


--
-- TOC entry 5637 (class 2606 OID 24604)
-- Name: postulacion_persona postulacion_persona_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion_persona
    ADD CONSTRAINT postulacion_persona_pkey PRIMARY KEY (id);


--
-- TOC entry 5639 (class 2606 OID 24606)
-- Name: postulacion_persona postulacion_persona_postulacion_id_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion_persona
    ADD CONSTRAINT postulacion_persona_postulacion_id_key UNIQUE (postulacion_id);


--
-- TOC entry 5633 (class 2606 OID 24527)
-- Name: postulacion postulacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_pkey PRIMARY KEY (id);


--
-- TOC entry 5571 (class 2606 OID 24139)
-- Name: profesor_grupo_director profesor_grupo_director_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT profesor_grupo_director_pkey PRIMARY KEY (id);


--
-- TOC entry 5590 (class 2606 OID 24290)
-- Name: profesor profesor_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT profesor_pkey PRIMARY KEY (id);


--
-- TOC entry 5676 (class 2606 OID 24898)
-- Name: resumen_anual_estudiante resumen_anual_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_pkey PRIMARY KEY (id);


--
-- TOC entry 5671 (class 2606 OID 24852)
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_pkey PRIMARY KEY (id);


--
-- TOC entry 5503 (class 2606 OID 23731)
-- Name: rol rol_codigo_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.rol
    ADD CONSTRAINT rol_codigo_key UNIQUE (codigo);


--
-- TOC entry 5513 (class 2606 OID 23762)
-- Name: rol_permiso rol_permiso_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT rol_permiso_pkey PRIMARY KEY (id);


--
-- TOC entry 5505 (class 2606 OID 23729)
-- Name: rol rol_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id);


--
-- TOC entry 5481 (class 2606 OID 23615)
-- Name: sede sede_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sede
    ADD CONSTRAINT sede_pkey PRIMARY KEY (id);


--
-- TOC entry 5695 (class 2606 OID 25079)
-- Name: seguimiento_observacion seguimiento_observacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.seguimiento_observacion
    ADD CONSTRAINT seguimiento_observacion_pkey PRIMARY KEY (id);


--
-- TOC entry 5645 (class 2606 OID 24654)
-- Name: seguimiento_postulacion seguimiento_postulacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.seguimiento_postulacion
    ADD CONSTRAINT seguimiento_postulacion_pkey PRIMARY KEY (id);


--
-- TOC entry 5586 (class 2606 OID 24245)
-- Name: sesion_clase sesion_clase_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_pkey PRIMARY KEY (id);


--
-- TOC entry 5686 (class 2606 OID 24984)
-- Name: tipo_observacion tipo_observacion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.tipo_observacion
    ADD CONSTRAINT tipo_observacion_pkey PRIMARY KEY (id);


--
-- TOC entry 5608 (class 2606 OID 24359)
-- Name: acudiente uq_acudiente_institucion_persona; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT uq_acudiente_institucion_persona UNIQUE (institucion_id, persona_id);


--
-- TOC entry 5540 (class 2606 OID 23934)
-- Name: anio_lectivo uq_anio_lectivo_institucion_anio; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.anio_lectivo
    ADD CONSTRAINT uq_anio_lectivo_institucion_anio UNIQUE (institucion_id, anio);


--
-- TOC entry 5563 (class 2606 OID 24072)
-- Name: area_academica uq_area_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.area_academica
    ADD CONSTRAINT uq_area_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5569 (class 2606 OID 24104)
-- Name: asignatura uq_asignatura_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT uq_asignatura_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5651 (class 2606 OID 24685)
-- Name: asistencia_clase uq_asistencia_sesion_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT uq_asistencia_sesion_estudiante UNIQUE (sesion_clase_id, estudiante_id);


--
-- TOC entry 5705 (class 2606 OID 25167)
-- Name: beneficio_financiero uq_beneficio_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT uq_beneficio_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5667 (class 2606 OID 24810)
-- Name: calificacion_actividad uq_calificacion_actividad_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT uq_calificacion_actividad_estudiante UNIQUE (actividad_id, estudiante_id);


--
-- TOC entry 5700 (class 2606 OID 25120)
-- Name: concepto_cobro uq_concepto_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT uq_concepto_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5714 (class 2606 OID 25277)
-- Name: cuenta_cobro uq_cuenta_cobro_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT uq_cuenta_cobro_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5683 (class 2606 OID 24943)
-- Name: decision_academica_estudiante uq_decision_anual_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT uq_decision_anual_estudiante UNIQUE (anio_lectivo_id, estudiante_id);


--
-- TOC entry 5661 (class 2606 OID 24777)
-- Name: entrega_actividad uq_entrega_actividad_estudiante; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT uq_entrega_actividad_estudiante UNIQUE (actividad_id, estudiante_id);


--
-- TOC entry 5618 (class 2606 OID 24415)
-- Name: estudiante_acudiente uq_estudiante_acudiente; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT uq_estudiante_acudiente UNIQUE (estudiante_id, acudiente_id);


--
-- TOC entry 5600 (class 2606 OID 24326)
-- Name: estudiante uq_estudiante_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT uq_estudiante_institucion_codigo UNIQUE (institucion_id, codigo_estudiante);


--
-- TOC entry 5602 (class 2606 OID 24328)
-- Name: estudiante uq_estudiante_institucion_persona; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT uq_estudiante_institucion_persona UNIQUE (institucion_id, persona_id);


--
-- TOC entry 5551 (class 2606 OID 23996)
-- Name: grado uq_grado_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grado
    ADD CONSTRAINT uq_grado_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5558 (class 2606 OID 24024)
-- Name: grupo uq_grupo_institucion_anio_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT uq_grupo_institucion_anio_codigo UNIQUE (institucion_id, anio_lectivo_id, codigo);


--
-- TOC entry 5488 (class 2606 OID 23643)
-- Name: jornada uq_jornada_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.jornada
    ADD CONSTRAINT uq_jornada_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5626 (class 2606 OID 24457)
-- Name: matricula uq_matricula_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT uq_matricula_codigo UNIQUE (institucion_id, codigo_matricula);


--
-- TOC entry 5628 (class 2606 OID 24459)
-- Name: matricula uq_matricula_estudiante_anio; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT uq_matricula_estudiante_anio UNIQUE (institucion_id, estudiante_id, anio_lectivo_id);


--
-- TOC entry 5739 (class 2606 OID 25499)
-- Name: notificacion_destinatario uq_notificacion_destinatario; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT uq_notificacion_destinatario UNIQUE (notificacion_id, usuario_id, canal);


--
-- TOC entry 5546 (class 2606 OID 23964)
-- Name: periodo_academico uq_periodo_institucion_anio_numero; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT uq_periodo_institucion_anio_numero UNIQUE (institucion_id, anio_lectivo_id, numero);


--
-- TOC entry 5494 (class 2606 OID 23670)
-- Name: persona uq_persona_documento; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.persona
    ADD CONSTRAINT uq_persona_documento UNIQUE (tipo_documento, numero_documento);


--
-- TOC entry 5635 (class 2606 OID 24529)
-- Name: postulacion uq_postulacion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT uq_postulacion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5573 (class 2606 OID 24141)
-- Name: profesor_grupo_director uq_profesor_grupo_director; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT uq_profesor_grupo_director UNIQUE (grupo_id, profesor_id, fecha_inicio);


--
-- TOC entry 5592 (class 2606 OID 24292)
-- Name: profesor uq_profesor_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT uq_profesor_institucion_codigo UNIQUE (institucion_id, codigo_profesor);


--
-- TOC entry 5594 (class 2606 OID 24294)
-- Name: profesor uq_profesor_institucion_persona; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT uq_profesor_institucion_persona UNIQUE (institucion_id, persona_id);


--
-- TOC entry 5678 (class 2606 OID 24900)
-- Name: resumen_anual_estudiante uq_resumen_anual; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT uq_resumen_anual UNIQUE (anio_lectivo_id, estudiante_id, asignatura_id);


--
-- TOC entry 5673 (class 2606 OID 24854)
-- Name: resumen_periodo_estudiante uq_resumen_periodo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT uq_resumen_periodo UNIQUE (periodo_academico_id, estudiante_id, carga_academica_id);


--
-- TOC entry 5515 (class 2606 OID 23764)
-- Name: rol_permiso uq_rol_permiso; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT uq_rol_permiso UNIQUE (rol_id, permiso_id);


--
-- TOC entry 5483 (class 2606 OID 23617)
-- Name: sede uq_sede_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sede
    ADD CONSTRAINT uq_sede_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5688 (class 2606 OID 24986)
-- Name: tipo_observacion uq_tipo_observacion_institucion_codigo; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.tipo_observacion
    ADD CONSTRAINT uq_tipo_observacion_institucion_codigo UNIQUE (institucion_id, codigo);


--
-- TOC entry 5497 (class 2606 OID 23704)
-- Name: usuario uq_usuario_email_login; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT uq_usuario_email_login UNIQUE (email_login);


--
-- TOC entry 5519 (class 2606 OID 23795)
-- Name: usuario_institucion uq_usuario_institucion; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT uq_usuario_institucion UNIQUE (usuario_id, institucion_id);


--
-- TOC entry 5526 (class 2606 OID 23824)
-- Name: usuario_rol uq_usuario_rol; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT uq_usuario_rol UNIQUE (usuario_id, institucion_id, rol_id);


--
-- TOC entry 5521 (class 2606 OID 23793)
-- Name: usuario_institucion usuario_institucion_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT usuario_institucion_pkey PRIMARY KEY (id);


--
-- TOC entry 5499 (class 2606 OID 23700)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 5528 (class 2606 OID 23822)
-- Name: usuario_rol usuario_rol_pkey; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_pkey PRIMARY KEY (id);


--
-- TOC entry 5501 (class 2606 OID 23702)
-- Name: usuario usuario_username_key; Type: CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- TOC entry 5654 (class 1259 OID 24757)
-- Name: idx_actividad_carga; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_actividad_carga ON optimscul.actividad_academica USING btree (carga_academica_id);


--
-- TOC entry 5655 (class 1259 OID 24758)
-- Name: idx_actividad_periodo; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_actividad_periodo ON optimscul.actividad_academica USING btree (periodo_academico_id);


--
-- TOC entry 5605 (class 1259 OID 24370)
-- Name: idx_acudiente_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_acudiente_institucion ON optimscul.acudiente USING btree (institucion_id);


--
-- TOC entry 5606 (class 1259 OID 24371)
-- Name: idx_acudiente_persona; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_acudiente_persona ON optimscul.acudiente USING btree (persona_id);


--
-- TOC entry 5538 (class 1259 OID 23940)
-- Name: idx_anio_lectivo_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_anio_lectivo_institucion ON optimscul.anio_lectivo USING btree (institucion_id);


--
-- TOC entry 5561 (class 1259 OID 24078)
-- Name: idx_area_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_area_institucion ON optimscul.area_academica USING btree (institucion_id);


--
-- TOC entry 5566 (class 1259 OID 24126)
-- Name: idx_asignatura_area; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_asignatura_area ON optimscul.asignatura USING btree (area_id);


--
-- TOC entry 5567 (class 1259 OID 24125)
-- Name: idx_asignatura_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_asignatura_institucion ON optimscul.asignatura USING btree (institucion_id);


--
-- TOC entry 5648 (class 1259 OID 24702)
-- Name: idx_asistencia_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_asistencia_estudiante ON optimscul.asistencia_clase USING btree (estudiante_id);


--
-- TOC entry 5649 (class 1259 OID 24701)
-- Name: idx_asistencia_sesion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_asistencia_sesion ON optimscul.asistencia_clase USING btree (sesion_clase_id);


--
-- TOC entry 5728 (class 1259 OID 25450)
-- Name: idx_auditoria_entidad; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_auditoria_entidad ON optimscul.auditoria_evento USING btree (entidad, entidad_id);


--
-- TOC entry 5729 (class 1259 OID 25448)
-- Name: idx_auditoria_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_auditoria_institucion ON optimscul.auditoria_evento USING btree (institucion_id);


--
-- TOC entry 5730 (class 1259 OID 25449)
-- Name: idx_auditoria_usuario; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_auditoria_usuario ON optimscul.auditoria_evento USING btree (usuario_id);


--
-- TOC entry 5703 (class 1259 OID 25188)
-- Name: idx_beneficio_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_beneficio_institucion ON optimscul.beneficio_financiero USING btree (institucion_id);


--
-- TOC entry 5664 (class 1259 OID 24831)
-- Name: idx_calificacion_actividad; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_calificacion_actividad ON optimscul.calificacion_actividad USING btree (actividad_id);


--
-- TOC entry 5665 (class 1259 OID 24832)
-- Name: idx_calificacion_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_calificacion_estudiante ON optimscul.calificacion_actividad USING btree (estudiante_id);


--
-- TOC entry 5576 (class 1259 OID 24188)
-- Name: idx_carga_anio; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_carga_anio ON optimscul.carga_academica USING btree (anio_lectivo_id);


--
-- TOC entry 5577 (class 1259 OID 24190)
-- Name: idx_carga_asignatura; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_carga_asignatura ON optimscul.carga_academica USING btree (asignatura_id);


--
-- TOC entry 5578 (class 1259 OID 24189)
-- Name: idx_carga_grupo; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_carga_grupo ON optimscul.carga_academica USING btree (grupo_id);


--
-- TOC entry 5579 (class 1259 OID 24187)
-- Name: idx_carga_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_carga_institucion ON optimscul.carga_academica USING btree (institucion_id);


--
-- TOC entry 5698 (class 1259 OID 25136)
-- Name: idx_concepto_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_concepto_institucion ON optimscul.concepto_cobro USING btree (institucion_id);


--
-- TOC entry 5717 (class 1259 OID 25360)
-- Name: idx_cuenta_beneficio_cuenta; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_cuenta_beneficio_cuenta ON optimscul.cuenta_cobro_beneficio USING btree (cuenta_cobro_id);


--
-- TOC entry 5711 (class 1259 OID 25319)
-- Name: idx_cuenta_cobro_estado; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_cuenta_cobro_estado ON optimscul.cuenta_cobro USING btree (estado);


--
-- TOC entry 5712 (class 1259 OID 25318)
-- Name: idx_cuenta_cobro_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_cuenta_cobro_estudiante ON optimscul.cuenta_cobro USING btree (estudiante_id);


--
-- TOC entry 5681 (class 1259 OID 24964)
-- Name: idx_decision_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_decision_estudiante ON optimscul.decision_academica_estudiante USING btree (estudiante_id);


--
-- TOC entry 5724 (class 1259 OID 25423)
-- Name: idx_documento_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_documento_institucion ON optimscul.documento USING btree (institucion_id);


--
-- TOC entry 5725 (class 1259 OID 25422)
-- Name: idx_documento_modulo_entidad; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_documento_modulo_entidad ON optimscul.documento USING btree (modulo, entidad_id);


--
-- TOC entry 5658 (class 1259 OID 24788)
-- Name: idx_entrega_actividad; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_entrega_actividad ON optimscul.entrega_actividad USING btree (actividad_id);


--
-- TOC entry 5659 (class 1259 OID 24789)
-- Name: idx_entrega_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_entrega_estudiante ON optimscul.entrega_actividad USING btree (estudiante_id);


--
-- TOC entry 5535 (class 1259 OID 23908)
-- Name: idx_escala_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_escala_institucion ON optimscul.escala_valorativa USING btree (institucion_id);


--
-- TOC entry 5708 (class 1259 OID 25248)
-- Name: idx_est_beneficio_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_est_beneficio_estudiante ON optimscul.estudiante_beneficio_financiero USING btree (estudiante_id);


--
-- TOC entry 5615 (class 1259 OID 24432)
-- Name: idx_estudiante_acudiente_acudiente; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_estudiante_acudiente_acudiente ON optimscul.estudiante_acudiente USING btree (acudiente_id);


--
-- TOC entry 5616 (class 1259 OID 24431)
-- Name: idx_estudiante_acudiente_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_estudiante_acudiente_estudiante ON optimscul.estudiante_acudiente USING btree (estudiante_id);


--
-- TOC entry 5597 (class 1259 OID 24339)
-- Name: idx_estudiante_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_estudiante_institucion ON optimscul.estudiante USING btree (institucion_id);


--
-- TOC entry 5598 (class 1259 OID 24340)
-- Name: idx_estudiante_persona; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_estudiante_persona ON optimscul.estudiante USING btree (persona_id);


--
-- TOC entry 5549 (class 1259 OID 24002)
-- Name: idx_grado_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_grado_institucion ON optimscul.grado USING btree (institucion_id);


--
-- TOC entry 5554 (class 1259 OID 24051)
-- Name: idx_grupo_anio; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_grupo_anio ON optimscul.grupo USING btree (anio_lectivo_id);


--
-- TOC entry 5555 (class 1259 OID 24052)
-- Name: idx_grupo_grado; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_grupo_grado ON optimscul.grupo USING btree (grado_id);


--
-- TOC entry 5556 (class 1259 OID 24050)
-- Name: idx_grupo_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_grupo_institucion ON optimscul.grupo USING btree (institucion_id);


--
-- TOC entry 5582 (class 1259 OID 24225)
-- Name: idx_horario_carga; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_horario_carga ON optimscul.horario_carga USING btree (carga_academica_id);


--
-- TOC entry 5484 (class 1259 OID 23649)
-- Name: idx_jornada_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_jornada_institucion ON optimscul.jornada USING btree (institucion_id);


--
-- TOC entry 5619 (class 1259 OID 24492)
-- Name: idx_matricula_anio; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_matricula_anio ON optimscul.matricula USING btree (anio_lectivo_id);


--
-- TOC entry 5620 (class 1259 OID 24491)
-- Name: idx_matricula_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_matricula_estudiante ON optimscul.matricula USING btree (estudiante_id);


--
-- TOC entry 5621 (class 1259 OID 24493)
-- Name: idx_matricula_grupo; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_matricula_grupo ON optimscul.matricula USING btree (grupo_id);


--
-- TOC entry 5622 (class 1259 OID 24490)
-- Name: idx_matricula_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_matricula_institucion ON optimscul.matricula USING btree (institucion_id);


--
-- TOC entry 5734 (class 1259 OID 25511)
-- Name: idx_notif_dest_estado; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_notif_dest_estado ON optimscul.notificacion_destinatario USING btree (estado);


--
-- TOC entry 5735 (class 1259 OID 25510)
-- Name: idx_notif_dest_usuario; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_notif_dest_usuario ON optimscul.notificacion_destinatario USING btree (usuario_id);


--
-- TOC entry 5731 (class 1259 OID 25478)
-- Name: idx_notificacion_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_notificacion_institucion ON optimscul.notificacion USING btree (institucion_id);


--
-- TOC entry 5689 (class 1259 OID 25065)
-- Name: idx_observacion_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_observacion_estudiante ON optimscul.observacion_estudiante USING btree (estudiante_id);


--
-- TOC entry 5690 (class 1259 OID 25066)
-- Name: idx_observacion_periodo; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_observacion_periodo ON optimscul.observacion_estudiante USING btree (periodo_academico_id);


--
-- TOC entry 5718 (class 1259 OID 25395)
-- Name: idx_pago_cuenta; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_pago_cuenta ON optimscul.pago USING btree (cuenta_cobro_id);


--
-- TOC entry 5719 (class 1259 OID 25396)
-- Name: idx_pago_fecha; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_pago_fecha ON optimscul.pago USING btree (fecha_pago);


--
-- TOC entry 5541 (class 1259 OID 23976)
-- Name: idx_periodo_anio; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_periodo_anio ON optimscul.periodo_academico USING btree (anio_lectivo_id);


--
-- TOC entry 5542 (class 1259 OID 23975)
-- Name: idx_periodo_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_periodo_institucion ON optimscul.periodo_academico USING btree (institucion_id);


--
-- TOC entry 5489 (class 1259 OID 23671)
-- Name: idx_persona_apellidos; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_persona_apellidos ON optimscul.persona USING btree (primer_apellido, segundo_apellido);


--
-- TOC entry 5490 (class 1259 OID 23672)
-- Name: idx_persona_nombres; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_persona_nombres ON optimscul.persona USING btree (primer_nombre, segundo_nombre);


--
-- TOC entry 5640 (class 1259 OID 24641)
-- Name: idx_postulacion_acudiente_postulacion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_postulacion_acudiente_postulacion ON optimscul.postulacion_acudiente USING btree (postulacion_id);


--
-- TOC entry 5629 (class 1259 OID 24586)
-- Name: idx_postulacion_anio; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_postulacion_anio ON optimscul.postulacion USING btree (anio_lectivo_id);


--
-- TOC entry 5630 (class 1259 OID 24587)
-- Name: idx_postulacion_estado; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_postulacion_estado ON optimscul.postulacion USING btree (estado);


--
-- TOC entry 5631 (class 1259 OID 24585)
-- Name: idx_postulacion_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_postulacion_institucion ON optimscul.postulacion USING btree (institucion_id);


--
-- TOC entry 5587 (class 1259 OID 24305)
-- Name: idx_profesor_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_profesor_institucion ON optimscul.profesor USING btree (institucion_id);


--
-- TOC entry 5588 (class 1259 OID 24306)
-- Name: idx_profesor_persona; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_profesor_persona ON optimscul.profesor USING btree (persona_id);


--
-- TOC entry 5674 (class 1259 OID 24921)
-- Name: idx_resumen_anual_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_resumen_anual_estudiante ON optimscul.resumen_anual_estudiante USING btree (estudiante_id);


--
-- TOC entry 5668 (class 1259 OID 24881)
-- Name: idx_resumen_periodo_carga; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_resumen_periodo_carga ON optimscul.resumen_periodo_estudiante USING btree (carga_academica_id);


--
-- TOC entry 5669 (class 1259 OID 24880)
-- Name: idx_resumen_periodo_estudiante; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_resumen_periodo_estudiante ON optimscul.resumen_periodo_estudiante USING btree (estudiante_id);


--
-- TOC entry 5510 (class 1259 OID 23776)
-- Name: idx_rol_permiso_permiso; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_rol_permiso_permiso ON optimscul.rol_permiso USING btree (permiso_id);


--
-- TOC entry 5511 (class 1259 OID 23775)
-- Name: idx_rol_permiso_rol; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_rol_permiso_rol ON optimscul.rol_permiso USING btree (rol_id);


--
-- TOC entry 5479 (class 1259 OID 23623)
-- Name: idx_sede_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_sede_institucion ON optimscul.sede USING btree (institucion_id);


--
-- TOC entry 5693 (class 1259 OID 25090)
-- Name: idx_seguimiento_observacion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_seguimiento_observacion ON optimscul.seguimiento_observacion USING btree (observacion_id);


--
-- TOC entry 5643 (class 1259 OID 24665)
-- Name: idx_seguimiento_postulacion_postulacion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_seguimiento_postulacion_postulacion ON optimscul.seguimiento_postulacion USING btree (postulacion_id);


--
-- TOC entry 5583 (class 1259 OID 24271)
-- Name: idx_sesion_carga; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_sesion_carga ON optimscul.sesion_clase USING btree (carga_academica_id);


--
-- TOC entry 5584 (class 1259 OID 24272)
-- Name: idx_sesion_fecha; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_sesion_fecha ON optimscul.sesion_clase USING btree (fecha);


--
-- TOC entry 5684 (class 1259 OID 24992)
-- Name: idx_tipo_observacion_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_tipo_observacion_institucion ON optimscul.tipo_observacion USING btree (institucion_id);


--
-- TOC entry 5516 (class 1259 OID 23807)
-- Name: idx_usuario_institucion_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_usuario_institucion_institucion ON optimscul.usuario_institucion USING btree (institucion_id);


--
-- TOC entry 5517 (class 1259 OID 23806)
-- Name: idx_usuario_institucion_usuario; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_usuario_institucion_usuario ON optimscul.usuario_institucion USING btree (usuario_id);


--
-- TOC entry 5495 (class 1259 OID 23710)
-- Name: idx_usuario_persona; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_usuario_persona ON optimscul.usuario USING btree (persona_id);


--
-- TOC entry 5522 (class 1259 OID 23842)
-- Name: idx_usuario_rol_institucion; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_usuario_rol_institucion ON optimscul.usuario_rol USING btree (institucion_id);


--
-- TOC entry 5523 (class 1259 OID 23841)
-- Name: idx_usuario_rol_rol; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_usuario_rol_rol ON optimscul.usuario_rol USING btree (rol_id);


--
-- TOC entry 5524 (class 1259 OID 23840)
-- Name: idx_usuario_rol_usuario; Type: INDEX; Schema: optimscul; Owner: postgres
--

CREATE INDEX idx_usuario_rol_usuario ON optimscul.usuario_rol USING btree (usuario_id);


--
-- TOC entry 5814 (class 2606 OID 24732)
-- Name: actividad_academica actividad_academica_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- TOC entry 5815 (class 2606 OID 24747)
-- Name: actividad_academica actividad_academica_creada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_creada_por_usuario_id_fkey FOREIGN KEY (creada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5816 (class 2606 OID 24727)
-- Name: actividad_academica actividad_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5817 (class 2606 OID 24737)
-- Name: actividad_academica actividad_academica_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- TOC entry 5818 (class 2606 OID 24742)
-- Name: actividad_academica actividad_academica_sesion_clase_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_sesion_clase_id_fkey FOREIGN KEY (sesion_clase_id) REFERENCES optimscul.sesion_clase(id) ON DELETE SET NULL;


--
-- TOC entry 5819 (class 2606 OID 24752)
-- Name: actividad_academica actividad_academica_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.actividad_academica
    ADD CONSTRAINT actividad_academica_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5785 (class 2606 OID 24360)
-- Name: acudiente acudiente_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT acudiente_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5786 (class 2606 OID 24365)
-- Name: acudiente acudiente_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.acudiente
    ADD CONSTRAINT acudiente_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- TOC entry 5752 (class 2606 OID 23935)
-- Name: anio_lectivo anio_lectivo_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.anio_lectivo
    ADD CONSTRAINT anio_lectivo_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5761 (class 2606 OID 24073)
-- Name: area_academica area_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.area_academica
    ADD CONSTRAINT area_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5762 (class 2606 OID 24110)
-- Name: asignatura asignatura_area_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_area_id_fkey FOREIGN KEY (area_id) REFERENCES optimscul.area_academica(id) ON DELETE SET NULL;


--
-- TOC entry 5763 (class 2606 OID 24115)
-- Name: asignatura asignatura_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5764 (class 2606 OID 24105)
-- Name: asignatura asignatura_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5765 (class 2606 OID 24120)
-- Name: asignatura asignatura_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asignatura
    ADD CONSTRAINT asignatura_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5811 (class 2606 OID 24691)
-- Name: asistencia_clase asistencia_clase_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5812 (class 2606 OID 24696)
-- Name: asistencia_clase asistencia_clase_registrada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_registrada_por_usuario_id_fkey FOREIGN KEY (registrada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5813 (class 2606 OID 24686)
-- Name: asistencia_clase asistencia_clase_sesion_clase_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.asistencia_clase
    ADD CONSTRAINT asistencia_clase_sesion_clase_id_fkey FOREIGN KEY (sesion_clase_id) REFERENCES optimscul.sesion_clase(id) ON DELETE CASCADE;


--
-- TOC entry 5884 (class 2606 OID 25438)
-- Name: auditoria_evento auditoria_evento_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.auditoria_evento
    ADD CONSTRAINT auditoria_evento_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5885 (class 2606 OID 25443)
-- Name: auditoria_evento auditoria_evento_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.auditoria_evento
    ADD CONSTRAINT auditoria_evento_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5854 (class 2606 OID 25173)
-- Name: beneficio_financiero beneficio_financiero_concepto_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_concepto_cobro_id_fkey FOREIGN KEY (concepto_cobro_id) REFERENCES optimscul.concepto_cobro(id) ON DELETE SET NULL;


--
-- TOC entry 5855 (class 2606 OID 25178)
-- Name: beneficio_financiero beneficio_financiero_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5856 (class 2606 OID 25168)
-- Name: beneficio_financiero beneficio_financiero_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5857 (class 2606 OID 25183)
-- Name: beneficio_financiero beneficio_financiero_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.beneficio_financiero
    ADD CONSTRAINT beneficio_financiero_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5822 (class 2606 OID 24811)
-- Name: calificacion_actividad calificacion_actividad_actividad_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_actividad_id_fkey FOREIGN KEY (actividad_id) REFERENCES optimscul.actividad_academica(id) ON DELETE CASCADE;


--
-- TOC entry 5823 (class 2606 OID 24826)
-- Name: calificacion_actividad calificacion_actividad_calificada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_calificada_por_usuario_id_fkey FOREIGN KEY (calificada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5824 (class 2606 OID 24821)
-- Name: calificacion_actividad calificacion_actividad_entrega_actividad_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_entrega_actividad_id_fkey FOREIGN KEY (entrega_actividad_id) REFERENCES optimscul.entrega_actividad(id) ON DELETE SET NULL;


--
-- TOC entry 5825 (class 2606 OID 24816)
-- Name: calificacion_actividad calificacion_actividad_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.calificacion_actividad
    ADD CONSTRAINT calificacion_actividad_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5768 (class 2606 OID 24172)
-- Name: carga_academica carga_academica_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- TOC entry 5769 (class 2606 OID 24182)
-- Name: carga_academica carga_academica_asignatura_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_asignatura_id_fkey FOREIGN KEY (asignatura_id) REFERENCES optimscul.asignatura(id) ON DELETE RESTRICT;


--
-- TOC entry 5770 (class 2606 OID 24177)
-- Name: carga_academica carga_academica_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE RESTRICT;


--
-- TOC entry 5771 (class 2606 OID 24167)
-- Name: carga_academica carga_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT carga_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5851 (class 2606 OID 25126)
-- Name: concepto_cobro concepto_cobro_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5852 (class 2606 OID 25121)
-- Name: concepto_cobro concepto_cobro_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5853 (class 2606 OID 25131)
-- Name: concepto_cobro concepto_cobro_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.concepto_cobro
    ADD CONSTRAINT concepto_cobro_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5750 (class 2606 OID 23877)
-- Name: configuracion_academica configuracion_academica_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.configuracion_academica
    ADD CONSTRAINT configuracion_academica_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5866 (class 2606 OID 25293)
-- Name: cuenta_cobro cuenta_cobro_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- TOC entry 5867 (class 2606 OID 25303)
-- Name: cuenta_cobro cuenta_cobro_anulado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_anulado_por_usuario_id_fkey FOREIGN KEY (anulado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5874 (class 2606 OID 25350)
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_beneficio_financiero_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_beneficio_financiero_id_fkey FOREIGN KEY (beneficio_financiero_id) REFERENCES optimscul.beneficio_financiero(id) ON DELETE SET NULL;


--
-- TOC entry 5875 (class 2606 OID 25355)
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5876 (class 2606 OID 25340)
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_cuenta_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_cuenta_cobro_id_fkey FOREIGN KEY (cuenta_cobro_id) REFERENCES optimscul.cuenta_cobro(id) ON DELETE CASCADE;


--
-- TOC entry 5877 (class 2606 OID 25345)
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_estudiante_beneficio_financiero_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_estudiante_beneficio_financiero_id_fkey FOREIGN KEY (estudiante_beneficio_financiero_id) REFERENCES optimscul.estudiante_beneficio_financiero(id) ON DELETE SET NULL;


--
-- TOC entry 5878 (class 2606 OID 25335)
-- Name: cuenta_cobro_beneficio cuenta_cobro_beneficio_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro_beneficio
    ADD CONSTRAINT cuenta_cobro_beneficio_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5868 (class 2606 OID 25288)
-- Name: cuenta_cobro cuenta_cobro_concepto_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_concepto_cobro_id_fkey FOREIGN KEY (concepto_cobro_id) REFERENCES optimscul.concepto_cobro(id) ON DELETE RESTRICT;


--
-- TOC entry 5869 (class 2606 OID 25308)
-- Name: cuenta_cobro cuenta_cobro_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5870 (class 2606 OID 25283)
-- Name: cuenta_cobro cuenta_cobro_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE RESTRICT;


--
-- TOC entry 5871 (class 2606 OID 25278)
-- Name: cuenta_cobro cuenta_cobro_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5872 (class 2606 OID 25298)
-- Name: cuenta_cobro cuenta_cobro_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- TOC entry 5873 (class 2606 OID 25313)
-- Name: cuenta_cobro cuenta_cobro_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.cuenta_cobro
    ADD CONSTRAINT cuenta_cobro_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5835 (class 2606 OID 24949)
-- Name: decision_academica_estudiante decision_academica_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- TOC entry 5836 (class 2606 OID 24954)
-- Name: decision_academica_estudiante decision_academica_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5837 (class 2606 OID 24944)
-- Name: decision_academica_estudiante decision_academica_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5838 (class 2606 OID 24959)
-- Name: decision_academica_estudiante decision_academica_estudiante_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.decision_academica_estudiante
    ADD CONSTRAINT decision_academica_estudiante_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5882 (class 2606 OID 25412)
-- Name: documento documento_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.documento
    ADD CONSTRAINT documento_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5883 (class 2606 OID 25417)
-- Name: documento documento_subido_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.documento
    ADD CONSTRAINT documento_subido_por_usuario_id_fkey FOREIGN KEY (subido_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5820 (class 2606 OID 24778)
-- Name: entrega_actividad entrega_actividad_actividad_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT entrega_actividad_actividad_id_fkey FOREIGN KEY (actividad_id) REFERENCES optimscul.actividad_academica(id) ON DELETE CASCADE;


--
-- TOC entry 5821 (class 2606 OID 24783)
-- Name: entrega_actividad entrega_actividad_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.entrega_actividad
    ADD CONSTRAINT entrega_actividad_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5751 (class 2606 OID 23903)
-- Name: escala_valorativa escala_valorativa_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.escala_valorativa
    ADD CONSTRAINT escala_valorativa_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5787 (class 2606 OID 24421)
-- Name: estudiante_acudiente estudiante_acudiente_acudiente_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_acudiente_id_fkey FOREIGN KEY (acudiente_id) REFERENCES optimscul.acudiente(id) ON DELETE CASCADE;


--
-- TOC entry 5788 (class 2606 OID 24416)
-- Name: estudiante_acudiente estudiante_acudiente_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5789 (class 2606 OID 24426)
-- Name: estudiante_acudiente estudiante_acudiente_parentesco_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_acudiente
    ADD CONSTRAINT estudiante_acudiente_parentesco_id_fkey FOREIGN KEY (parentesco_id) REFERENCES optimscul.parentesco(id) ON DELETE SET NULL;


--
-- TOC entry 5858 (class 2606 OID 25223)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- TOC entry 5859 (class 2606 OID 25233)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_aprobado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_aprobado_por_usuario_id_fkey FOREIGN KEY (aprobado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5860 (class 2606 OID 25218)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_beneficio_financiero_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_beneficio_financiero_id_fkey FOREIGN KEY (beneficio_financiero_id) REFERENCES optimscul.beneficio_financiero(id) ON DELETE CASCADE;


--
-- TOC entry 5861 (class 2606 OID 25238)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5862 (class 2606 OID 25213)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5863 (class 2606 OID 25208)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5864 (class 2606 OID 25228)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- TOC entry 5865 (class 2606 OID 25243)
-- Name: estudiante_beneficio_financiero estudiante_beneficio_financiero_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante_beneficio_financiero
    ADD CONSTRAINT estudiante_beneficio_financiero_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5783 (class 2606 OID 24329)
-- Name: estudiante estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5784 (class 2606 OID 24334)
-- Name: estudiante estudiante_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.estudiante
    ADD CONSTRAINT estudiante_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- TOC entry 5772 (class 2606 OID 24499)
-- Name: carga_academica fk_carga_academica_profesor; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.carga_academica
    ADD CONSTRAINT fk_carga_academica_profesor FOREIGN KEY (profesor_id) REFERENCES optimscul.profesor(id) ON DELETE RESTRICT;


--
-- TOC entry 5766 (class 2606 OID 24494)
-- Name: profesor_grupo_director fk_profesor_grupo_director_profesor; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT fk_profesor_grupo_director_profesor FOREIGN KEY (profesor_id) REFERENCES optimscul.profesor(id) ON DELETE CASCADE;


--
-- TOC entry 5755 (class 2606 OID 23997)
-- Name: grado grado_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grado
    ADD CONSTRAINT grado_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5756 (class 2606 OID 24030)
-- Name: grupo grupo_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- TOC entry 5757 (class 2606 OID 24045)
-- Name: grupo grupo_grado_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_grado_id_fkey FOREIGN KEY (grado_id) REFERENCES optimscul.grado(id) ON DELETE RESTRICT;


--
-- TOC entry 5758 (class 2606 OID 24025)
-- Name: grupo grupo_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5759 (class 2606 OID 24040)
-- Name: grupo grupo_jornada_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_jornada_id_fkey FOREIGN KEY (jornada_id) REFERENCES optimscul.jornada(id) ON DELETE SET NULL;


--
-- TOC entry 5760 (class 2606 OID 24035)
-- Name: grupo grupo_sede_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.grupo
    ADD CONSTRAINT grupo_sede_id_fkey FOREIGN KEY (sede_id) REFERENCES optimscul.sede(id) ON DELETE SET NULL;


--
-- TOC entry 5773 (class 2606 OID 24215)
-- Name: horario_carga horario_carga_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- TOC entry 5774 (class 2606 OID 24210)
-- Name: horario_carga horario_carga_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5775 (class 2606 OID 24220)
-- Name: horario_carga horario_carga_sede_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.horario_carga
    ADD CONSTRAINT horario_carga_sede_id_fkey FOREIGN KEY (sede_id) REFERENCES optimscul.sede(id) ON DELETE SET NULL;


--
-- TOC entry 5741 (class 2606 OID 23644)
-- Name: jornada jornada_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.jornada
    ADD CONSTRAINT jornada_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5790 (class 2606 OID 24470)
-- Name: matricula matricula_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE RESTRICT;


--
-- TOC entry 5791 (class 2606 OID 24480)
-- Name: matricula matricula_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5792 (class 2606 OID 24465)
-- Name: matricula matricula_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE RESTRICT;


--
-- TOC entry 5793 (class 2606 OID 24475)
-- Name: matricula matricula_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE SET NULL;


--
-- TOC entry 5794 (class 2606 OID 24460)
-- Name: matricula matricula_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5795 (class 2606 OID 24485)
-- Name: matricula matricula_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.matricula
    ADD CONSTRAINT matricula_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5886 (class 2606 OID 25473)
-- Name: notificacion notificacion_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion
    ADD CONSTRAINT notificacion_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5888 (class 2606 OID 25500)
-- Name: notificacion_destinatario notificacion_destinatario_notificacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT notificacion_destinatario_notificacion_id_fkey FOREIGN KEY (notificacion_id) REFERENCES optimscul.notificacion(id) ON DELETE CASCADE;


--
-- TOC entry 5889 (class 2606 OID 25505)
-- Name: notificacion_destinatario notificacion_destinatario_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion_destinatario
    ADD CONSTRAINT notificacion_destinatario_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE CASCADE;


--
-- TOC entry 5887 (class 2606 OID 25468)
-- Name: notificacion notificacion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.notificacion
    ADD CONSTRAINT notificacion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5840 (class 2606 OID 25030)
-- Name: observacion_estudiante observacion_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- TOC entry 5841 (class 2606 OID 25045)
-- Name: observacion_estudiante observacion_estudiante_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE SET NULL;


--
-- TOC entry 5842 (class 2606 OID 25060)
-- Name: observacion_estudiante observacion_estudiante_cerrada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_cerrada_por_usuario_id_fkey FOREIGN KEY (cerrada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5843 (class 2606 OID 25055)
-- Name: observacion_estudiante observacion_estudiante_creada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_creada_por_usuario_id_fkey FOREIGN KEY (creada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5844 (class 2606 OID 25025)
-- Name: observacion_estudiante observacion_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5845 (class 2606 OID 25040)
-- Name: observacion_estudiante observacion_estudiante_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE SET NULL;


--
-- TOC entry 5846 (class 2606 OID 25020)
-- Name: observacion_estudiante observacion_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5847 (class 2606 OID 25035)
-- Name: observacion_estudiante observacion_estudiante_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE SET NULL;


--
-- TOC entry 5848 (class 2606 OID 25050)
-- Name: observacion_estudiante observacion_estudiante_tipo_observacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.observacion_estudiante
    ADD CONSTRAINT observacion_estudiante_tipo_observacion_id_fkey FOREIGN KEY (tipo_observacion_id) REFERENCES optimscul.tipo_observacion(id) ON DELETE RESTRICT;


--
-- TOC entry 5879 (class 2606 OID 25385)
-- Name: pago pago_cuenta_cobro_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_cuenta_cobro_id_fkey FOREIGN KEY (cuenta_cobro_id) REFERENCES optimscul.cuenta_cobro(id) ON DELETE CASCADE;


--
-- TOC entry 5880 (class 2606 OID 25380)
-- Name: pago pago_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5881 (class 2606 OID 25390)
-- Name: pago pago_registrado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.pago
    ADD CONSTRAINT pago_registrado_por_usuario_id_fkey FOREIGN KEY (registrado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5753 (class 2606 OID 23970)
-- Name: periodo_academico periodo_academico_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT periodo_academico_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- TOC entry 5754 (class 2606 OID 23965)
-- Name: periodo_academico periodo_academico_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.periodo_academico
    ADD CONSTRAINT periodo_academico_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5808 (class 2606 OID 24636)
-- Name: postulacion_acudiente postulacion_acudiente_postulacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion_acudiente
    ADD CONSTRAINT postulacion_acudiente_postulacion_id_fkey FOREIGN KEY (postulacion_id) REFERENCES optimscul.postulacion(id) ON DELETE CASCADE;


--
-- TOC entry 5796 (class 2606 OID 24535)
-- Name: postulacion postulacion_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE SET NULL;


--
-- TOC entry 5797 (class 2606 OID 24555)
-- Name: postulacion postulacion_aprobada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_aprobada_por_usuario_id_fkey FOREIGN KEY (aprobada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5798 (class 2606 OID 24565)
-- Name: postulacion postulacion_convertida_en_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_convertida_en_estudiante_id_fkey FOREIGN KEY (convertida_en_estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE SET NULL;


--
-- TOC entry 5799 (class 2606 OID 24570)
-- Name: postulacion postulacion_convertida_en_matricula_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_convertida_en_matricula_id_fkey FOREIGN KEY (convertida_en_matricula_id) REFERENCES optimscul.matricula(id) ON DELETE SET NULL;


--
-- TOC entry 5800 (class 2606 OID 24575)
-- Name: postulacion postulacion_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5801 (class 2606 OID 24550)
-- Name: postulacion postulacion_grado_aspira_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_grado_aspira_id_fkey FOREIGN KEY (grado_aspira_id) REFERENCES optimscul.grado(id) ON DELETE SET NULL;


--
-- TOC entry 5802 (class 2606 OID 24530)
-- Name: postulacion postulacion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5803 (class 2606 OID 24545)
-- Name: postulacion postulacion_jornada_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_jornada_id_fkey FOREIGN KEY (jornada_id) REFERENCES optimscul.jornada(id) ON DELETE SET NULL;


--
-- TOC entry 5807 (class 2606 OID 24607)
-- Name: postulacion_persona postulacion_persona_postulacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion_persona
    ADD CONSTRAINT postulacion_persona_postulacion_id_fkey FOREIGN KEY (postulacion_id) REFERENCES optimscul.postulacion(id) ON DELETE CASCADE;


--
-- TOC entry 5804 (class 2606 OID 24560)
-- Name: postulacion postulacion_rechazada_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_rechazada_por_usuario_id_fkey FOREIGN KEY (rechazada_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5805 (class 2606 OID 24540)
-- Name: postulacion postulacion_sede_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_sede_id_fkey FOREIGN KEY (sede_id) REFERENCES optimscul.sede(id) ON DELETE SET NULL;


--
-- TOC entry 5806 (class 2606 OID 24580)
-- Name: postulacion postulacion_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.postulacion
    ADD CONSTRAINT postulacion_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5767 (class 2606 OID 24142)
-- Name: profesor_grupo_director profesor_grupo_director_grupo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor_grupo_director
    ADD CONSTRAINT profesor_grupo_director_grupo_id_fkey FOREIGN KEY (grupo_id) REFERENCES optimscul.grupo(id) ON DELETE CASCADE;


--
-- TOC entry 5781 (class 2606 OID 24295)
-- Name: profesor profesor_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT profesor_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5782 (class 2606 OID 24300)
-- Name: profesor profesor_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.profesor
    ADD CONSTRAINT profesor_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- TOC entry 5831 (class 2606 OID 24906)
-- Name: resumen_anual_estudiante resumen_anual_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- TOC entry 5832 (class 2606 OID 24916)
-- Name: resumen_anual_estudiante resumen_anual_estudiante_asignatura_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_asignatura_id_fkey FOREIGN KEY (asignatura_id) REFERENCES optimscul.asignatura(id) ON DELETE RESTRICT;


--
-- TOC entry 5833 (class 2606 OID 24911)
-- Name: resumen_anual_estudiante resumen_anual_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5834 (class 2606 OID 24901)
-- Name: resumen_anual_estudiante resumen_anual_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_anual_estudiante
    ADD CONSTRAINT resumen_anual_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5826 (class 2606 OID 24860)
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_anio_lectivo_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_anio_lectivo_id_fkey FOREIGN KEY (anio_lectivo_id) REFERENCES optimscul.anio_lectivo(id) ON DELETE CASCADE;


--
-- TOC entry 5827 (class 2606 OID 24875)
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- TOC entry 5828 (class 2606 OID 24870)
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_estudiante_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_estudiante_id_fkey FOREIGN KEY (estudiante_id) REFERENCES optimscul.estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5829 (class 2606 OID 24855)
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5830 (class 2606 OID 24865)
-- Name: resumen_periodo_estudiante resumen_periodo_estudiante_periodo_academico_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.resumen_periodo_estudiante
    ADD CONSTRAINT resumen_periodo_estudiante_periodo_academico_id_fkey FOREIGN KEY (periodo_academico_id) REFERENCES optimscul.periodo_academico(id) ON DELETE CASCADE;


--
-- TOC entry 5743 (class 2606 OID 23770)
-- Name: rol_permiso rol_permiso_permiso_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT rol_permiso_permiso_id_fkey FOREIGN KEY (permiso_id) REFERENCES optimscul.permiso(id) ON DELETE CASCADE;


--
-- TOC entry 5744 (class 2606 OID 23765)
-- Name: rol_permiso rol_permiso_rol_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.rol_permiso
    ADD CONSTRAINT rol_permiso_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES optimscul.rol(id) ON DELETE CASCADE;


--
-- TOC entry 5740 (class 2606 OID 23618)
-- Name: sede sede_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sede
    ADD CONSTRAINT sede_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5849 (class 2606 OID 25085)
-- Name: seguimiento_observacion seguimiento_observacion_creado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.seguimiento_observacion
    ADD CONSTRAINT seguimiento_observacion_creado_por_usuario_id_fkey FOREIGN KEY (creado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5850 (class 2606 OID 25080)
-- Name: seguimiento_observacion seguimiento_observacion_observacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.seguimiento_observacion
    ADD CONSTRAINT seguimiento_observacion_observacion_id_fkey FOREIGN KEY (observacion_id) REFERENCES optimscul.observacion_estudiante(id) ON DELETE CASCADE;


--
-- TOC entry 5809 (class 2606 OID 24660)
-- Name: seguimiento_postulacion seguimiento_postulacion_creado_por_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.seguimiento_postulacion
    ADD CONSTRAINT seguimiento_postulacion_creado_por_usuario_id_fkey FOREIGN KEY (creado_por_usuario_id) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5810 (class 2606 OID 24655)
-- Name: seguimiento_postulacion seguimiento_postulacion_postulacion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.seguimiento_postulacion
    ADD CONSTRAINT seguimiento_postulacion_postulacion_id_fkey FOREIGN KEY (postulacion_id) REFERENCES optimscul.postulacion(id) ON DELETE CASCADE;


--
-- TOC entry 5776 (class 2606 OID 24251)
-- Name: sesion_clase sesion_clase_carga_academica_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_carga_academica_id_fkey FOREIGN KEY (carga_academica_id) REFERENCES optimscul.carga_academica(id) ON DELETE CASCADE;


--
-- TOC entry 5777 (class 2606 OID 24261)
-- Name: sesion_clase sesion_clase_created_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_created_by_fkey FOREIGN KEY (created_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5778 (class 2606 OID 24256)
-- Name: sesion_clase sesion_clase_horario_carga_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_horario_carga_id_fkey FOREIGN KEY (horario_carga_id) REFERENCES optimscul.horario_carga(id) ON DELETE SET NULL;


--
-- TOC entry 5779 (class 2606 OID 24246)
-- Name: sesion_clase sesion_clase_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5780 (class 2606 OID 24266)
-- Name: sesion_clase sesion_clase_updated_by_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.sesion_clase
    ADD CONSTRAINT sesion_clase_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES optimscul.usuario(id) ON DELETE SET NULL;


--
-- TOC entry 5839 (class 2606 OID 24987)
-- Name: tipo_observacion tipo_observacion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.tipo_observacion
    ADD CONSTRAINT tipo_observacion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5745 (class 2606 OID 23801)
-- Name: usuario_institucion usuario_institucion_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT usuario_institucion_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5746 (class 2606 OID 23796)
-- Name: usuario_institucion usuario_institucion_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_institucion
    ADD CONSTRAINT usuario_institucion_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE CASCADE;


--
-- TOC entry 5742 (class 2606 OID 23705)
-- Name: usuario usuario_persona_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario
    ADD CONSTRAINT usuario_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES optimscul.persona(id) ON DELETE RESTRICT;


--
-- TOC entry 5747 (class 2606 OID 23830)
-- Name: usuario_rol usuario_rol_institucion_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_institucion_id_fkey FOREIGN KEY (institucion_id) REFERENCES optimscul.institucion(id) ON DELETE CASCADE;


--
-- TOC entry 5748 (class 2606 OID 23835)
-- Name: usuario_rol usuario_rol_rol_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES optimscul.rol(id) ON DELETE CASCADE;


--
-- TOC entry 5749 (class 2606 OID 23825)
-- Name: usuario_rol usuario_rol_usuario_id_fkey; Type: FK CONSTRAINT; Schema: optimscul; Owner: postgres
--

ALTER TABLE ONLY optimscul.usuario_rol
    ADD CONSTRAINT usuario_rol_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES optimscul.usuario(id) ON DELETE CASCADE;


-- Completed on 2026-06-25 17:21:14

--
-- PostgreSQL database dump complete
--

\unrestrict 9zFTh2G7tucwNIwg3MuUL43743DEZ2W6miAn5UezxAQfXuLC9Ey3Xpu2uyvGTiO

