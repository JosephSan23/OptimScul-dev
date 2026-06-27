package backend.people.infrastructure.rest.dto;

import backend.people.domain.model.EstadoInstitucion;
import backend.people.domain.model.TipoInstitucion;

public class InstitucionResponseDto {

    private String codigo;

    private String nombre;

    private String nombreCorto;

    private TipoInstitucion tipoInstitucion;

    private String nit;

    private String dane;

    private String resolucionFuncionamiento;

    private String descripcion;

    private String correoContacto;

    private String telefonoContacto;

    private String sitioWeb;

    private String direccionPrincipal;

    private String ciudad;

    private String departamento;

    private String pais;

    private String logoUrl;

    private String zonaHoraria;

    private String moneda;

    private EstadoInstitucion estado;

    public InstitucionResponseDto() {}

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
}
