package backend.academic.infrastructure.rest.dto.Grado;

import backend.academic.domain.model.NivelAcademico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GradoRequestDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull(message = "Selecciona el nivel académico")
    private NivelAcademico nivel;
    @NotNull(message = "El orden es obligatorio")
    @Positive(message = "El orden debe ser un número positivo")
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
