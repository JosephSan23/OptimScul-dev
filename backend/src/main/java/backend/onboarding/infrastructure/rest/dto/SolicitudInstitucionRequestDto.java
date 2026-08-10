package backend.onboarding.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SolicitudInstitucionRequestDto {

    @NotBlank(message = "El nombre del colegio es obligatorio")
    private String nombreColegio;

    @Pattern(regexp = "^[0-9-]*$", message = "El NIT solo debe contener números")
    private String nit;
    private String ciudad;
    private String direccion;
    @Pattern(regexp = "^([0-9]{7}|[0-9]{10})?$",
             message = "Teléfono inválido (7 dígitos fijo o 10 dígitos celular)")
    private String telefono;

    @NotBlank(message = "El nombre de contacto es obligatorio")
    private String nombreContacto;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Escribe un correo válido (ej: nombre@dominio.com)")
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
