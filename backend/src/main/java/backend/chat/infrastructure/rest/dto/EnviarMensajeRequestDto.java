package backend.chat.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EnviarMensajeRequestDto {
    @NotBlank
    @Size(max = 4000)
    private String contenido;
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}