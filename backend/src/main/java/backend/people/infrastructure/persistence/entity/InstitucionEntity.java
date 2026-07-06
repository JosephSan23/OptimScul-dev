package backend.people.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.TipoInstitucion;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "institucion", schema = "optimscul")
public class InstitucionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nombre_corto")
    private String nombreCorto;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_institucion", columnDefinition = "tipo_institucion_enum")
    private TipoInstitucion tipoInstitucion;

    @Column(name = "nit")
    private String nit;

    @Column(name = "dane")
    private String dane;

    @Column(name = "resolucion_funcionamiento")
    private String resolucionFuncionamiento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "correo_contacto")
    private String correoContacto;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @Column(name = "dominio_correo")
    private String dominioCorreo;

    @Column(name = "direccion_principal")
    private String direccionPrincipal;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "pais")
    private String pais;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "zona_horaria")
    private String zonaHoraria;

    @Column(name = "moneda")
    private String moneda;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_institucion_enum")
    private EstadoInstitucion estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    

}
