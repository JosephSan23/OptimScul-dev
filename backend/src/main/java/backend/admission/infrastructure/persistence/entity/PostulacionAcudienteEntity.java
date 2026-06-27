package backend.admission.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.people.domain.model.TipoParentesco;

@Entity
@Table(name = "postulacion_acudiente", schema = "optimscul")
public class PostulacionAcudienteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "postulacion_id")
    private UUID postulacionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "parentesco")
    private TipoParentesco parentesco;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "telefono_alternativo")
    private String telefonoAlternativo;

    @Column(name = "correo")
    private String correo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ocupacion")
    private String ocupacion;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @Column(name = "autorizado_recogida")
    private Boolean autorizadoRecogida;

    @Column(name = "autorizado_info_academica")
    private Boolean autorizadoInfoAcademica;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PostulacionAcudienteEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPostulacionId() { return postulacionId; }
    public void setPostulacionId(UUID postulacionId) { this.postulacionId = postulacionId; }
    public TipoDocumentoPersona getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumentoPersona tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public TipoParentesco getParentesco() { return parentesco; }
    public void setParentesco(TipoParentesco parentesco) { this.parentesco = parentesco; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getTelefonoAlternativo() { return telefonoAlternativo; }
    public void setTelefonoAlternativo(String telefonoAlternativo) { this.telefonoAlternativo = telefonoAlternativo; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
    public Boolean getAutorizadoRecogida() { return autorizadoRecogida; }
    public void setAutorizadoRecogida(Boolean autorizadoRecogida) { this.autorizadoRecogida = autorizadoRecogida; }
    public Boolean getAutorizadoInfoAcademica() { return autorizadoInfoAcademica; }
    public void setAutorizadoInfoAcademica(Boolean autorizadoInfoAcademica) { this.autorizadoInfoAcademica = autorizadoInfoAcademica; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
