package backend.admission.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;
import backend.people.domain.model.Sexo;
import backend.people.domain.model.TipoDocumentoPersona;

public class PostulacionPersonaResponseDto {

    private UUID postulacionId;

    private TipoDocumentoPersona tipoDocumento;

    private String numeroDocumento;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private LocalDate fechaNacimiento;

    private Sexo sexo;

    private String nacionalidad;

    private String correo;

    private String telefono;

    private String direccion;

    private String ciudad;

    private String departamento;

    private String pais;

    private String colegioProcedencia;

    private String observaciones;

    public PostulacionPersonaResponseDto() {}

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
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getColegioProcedencia() { return colegioProcedencia; }
    public void setColegioProcedencia(String colegioProcedencia) { this.colegioProcedencia = colegioProcedencia; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
