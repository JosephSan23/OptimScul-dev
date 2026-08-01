package backend.chat.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mensaje", schema = "optimscul")
public class MensajeEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "conversacion_id")
    private UUID conversacionId;

    @Column(name = "remitente_id")
    private UUID remitenteId;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "leido")
    private boolean leido;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}