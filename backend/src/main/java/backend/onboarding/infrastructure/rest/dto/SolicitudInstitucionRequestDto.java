package backend.onboarding.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public class SolicitudInstitucionRequestDto {

    @NotBlank
    private String nombreColegio;

    private String nit;
    private String ciudad;
    private String direccion;
    private String telefono;

    @NotBlank
    private String nombreContacto;

    @NotBlank
    private String correo;

    private String mensaje;

    public String getNombreColegio() { return nombreColegio; }
    public void setNombreColegio(String nombreColegio) { this.nombreColegio = nombreColegio; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}