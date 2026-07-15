package backend.people.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.people.domain.model.Sexo;
import backend.people.domain.model.TipoDocumentoPersona;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "persona", schema = "optimscul")
public class PersonaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_documento", columnDefinition = "tipo_documento_persona_enum")
    private TipoDocumentoPersona tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "sexo", columnDefinition = "sexo_enum")
    private Sexo sexo;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "telefono_alternativo")
    private String telefonoAlternativo;

    @Column(name = "correo")
    private String correo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "barrio")
    private String barrio;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "pais")
    private String pais;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
