package backend.people.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.TipoInstitucion;

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
    @Column(name = "tipo_institucion")
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
    @Column(name = "estado")
    private EstadoInstitucion estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public InstitucionEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombreCorto() { return nombreCorto; }
    public void setNombreCorto(String nombreCorto) { this.nombreCorto = nombreCorto; }
    public TipoInstitucion getTipoInstitucion() { return tipoInstitucion; }
    public void setTipoInstitucion(TipoInstitucion tipoInstitucion) { this.tipoInstitucion = tipoInstitucion; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getDane() { return dane; }
    public void setDane(String dane) { this.dane = dane; }
    public String getResolucionFuncionamiento() { return resolucionFuncionamiento; }
    public void setResolucionFuncionamiento(String resolucionFuncionamiento) { this.resolucionFuncionamiento = resolucionFuncionamiento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCorreoContacto() { return correoContacto; }
    public void setCorreoContacto(String correoContacto) { this.correoContacto = correoContacto; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    public String getDireccionPrincipal() { return direccionPrincipal; }
    public void setDireccionPrincipal(String direccionPrincipal) { this.direccionPrincipal = direccionPrincipal; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getZonaHoraria() { return zonaHoraria; }
    public void setZonaHoraria(String zonaHoraria) { this.zonaHoraria = zonaHoraria; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public EstadoInstitucion getEstado() { return estado; }
    public void setEstado(EstadoInstitucion estado) { this.estado = estado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
