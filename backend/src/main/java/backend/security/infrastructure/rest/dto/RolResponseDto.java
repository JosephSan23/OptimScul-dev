package backend.security.infrastructure.rest.dto;

public class RolResponseDto {

    private String codigo;

    private String nombre;

    private String descripcion;

    private Boolean esSistema;

    private Boolean activo;

    public RolResponseDto() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getEsSistema() { return esSistema; }
    public void setEsSistema(Boolean esSistema) { this.esSistema = esSistema; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
