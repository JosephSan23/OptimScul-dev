-- El "último mensaje por conversación" ya se apoya en ix_mensaje_conversacion (V8).
-- Este índice acelera el conteo de mensajes NO leídos por conversación.
CREATE INDEX IF NOT EXISTS idx_mensaje_no_leidos
    ON optimscul.mensaje (conversacion_id, leido, remitente_id);
