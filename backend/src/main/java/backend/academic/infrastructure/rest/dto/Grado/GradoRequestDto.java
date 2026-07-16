package backend.academic.infrastructure.rest.dto.Grado;

import backend.academic.domain.model.NivelAcademico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GradoRequestDto {

    @NotBlank
    private String codigo;
    @NotBlank
    private String nombre;
    @NotNull
    private NivelAcademico nivel;
    @NotNull
    private Short orden;

    public GradoRequestDto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NivelAcademico getNivel() {
        return nivel;
    }

    public void setNivel(NivelAcademico nivel) {
        this.nivel = nivel;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }
}