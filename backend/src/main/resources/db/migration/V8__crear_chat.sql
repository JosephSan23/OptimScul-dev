CREATE TABLE optimscul.conversacion (
    id                 uuid DEFAULT gen_random_uuid() NOT NULL,
    institucion_id     uuid NOT NULL,
    usuario_menor_id   uuid NOT NULL,
    usuario_mayor_id   uuid NOT NULL,
    created_by         uuid NOT NULL,
    created_at         timestamp without time zone DEFAULT now() NOT NULL,
    updated_at         timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT pk_conversacion PRIMARY KEY (id),
    CONSTRAINT uq_conversacion_par UNIQUE (institucion_id, usuario_menor_id, usuario_mayor_id),
    CONSTRAINT ck_conversacion_distintos CHECK (usuario_menor_id <> usuario_mayor_id)
);

CREATE TABLE optimscul.mensaje (
    id                 uuid DEFAULT gen_random_uuid() NOT NULL,
    conversacion_id    uuid NOT NULL,
    remitente_id       uuid NOT NULL,
    contenido          text NOT NULL,
    leido              boolean DEFAULT false NOT NULL,
    created_at         timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT pk_mensaje PRIMARY KEY (id),
    CONSTRAINT fk_mensaje_conversacion FOREIGN KEY (conversacion_id)
        REFERENCES optimscul.conversacion (id) ON DELETE CASCADE
);

CREATE INDEX ix_conversacion_menor ON optimscul.conversacion (usuario_menor_id);
CREATE INDEX ix_conversacion_mayor ON optimscul.conversacion (usuario_mayor_id);
CREATE INDEX ix_mensaje_conversacion ON optimscul.mensaje (conversacion_id, created_at);