package backend.admission.infrastructure.rest.dto;

import java.util.UUID;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.people.domain.model.TipoParentesco;

public class PostulacionAcudienteResponseDto {

    private UUID postulacionId;

    private TipoDocumentoPersona tipoDocumento;

    private String numeroDocumento;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private TipoParentesco parentesco;

    private String telefono;

    private String telefonoAlternativo;

    private String correo;

    private String direccion;

    private String ocupacion;

    private String empresa;

    private Boolean esPrincipal;

    private Boolean autorizadoRecogida;

    private Boolean autorizadoInfoAcademica;

    private String observaciones;

    public PostulacionAcudienteResponseDto() {}

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
}
