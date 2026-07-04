-- Tabla para las solicitudes de instituciones que quieren contactar OptimScul.
-- Una solicitud, al ser aprobada, se convierte en un registro de la tabla institucion.

CREATE TABLE optimscul.solicitud_institucion (
    id                        uuid DEFAULT gen_random_uuid() NOT NULL,

    -- Datos que llegan del formulario público
    nombre_colegio            varchar(200) NOT NULL,
    nit                       varchar(50),
    ciudad                    varchar(120),
    direccion                 varchar(255),
    telefono                  varchar(50),
    nombre_contacto           varchar(200) NOT NULL,
    correo                    varchar(150) NOT NULL,
    mensaje                   text,

    -- Quién la envió (usuario logueado que llenó el formulario)
    enviada_por_usuario_id    uuid,

    -- Control del flujo de aprobación
    estado                    varchar(30) DEFAULT 'PENDIENTE' NOT NULL,
    revisada_por_usuario_id   uuid,
    fecha_revision            timestamp without time zone,
    motivo_rechazo            text,

    -- Enlace a la institución creada cuando se aprueba (el "convertir")
    convertida_en_institucion_id uuid,
    fecha_conversion          timestamp without time zone,

    created_at                timestamp without time zone DEFAULT now() NOT NULL,
    updated_at                timestamp without time zone DEFAULT now() NOT NULL,

    CONSTRAINT solicitud_institucion_pkey PRIMARY KEY (id)
);