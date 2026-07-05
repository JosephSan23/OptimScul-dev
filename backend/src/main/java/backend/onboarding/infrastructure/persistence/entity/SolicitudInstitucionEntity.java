package backend.onboarding.infrastructure.persistence.entity;

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
@Table(name = "solicitud_institucion", schema = "optimscul")
public class SolicitudInstitucionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre_colegio")
    private String nombreColegio;

    @Column(name = "nit")
    private String nit;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Column(name = "correo")
    private String correo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "enviada_por_usuario_id")
    private UUID enviadaPorUsuarioId;

    @Column(name = "estado")
    private String estado;

    @Column(name = "revisada_por_usuario_id")
    private UUID revisadaPorUsuarioId;

    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;

    @Column(name = "convertida_en_institucion_id")
    private UUID convertidaEnInstitucionId;

    @Column(name = "fecha_conversion")
    private LocalDateTime fechaConversion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}