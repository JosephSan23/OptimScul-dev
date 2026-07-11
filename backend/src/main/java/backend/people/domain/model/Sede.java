package backend.people.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sede {

    private UUID id;
    private UUID institucionId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String correo;
    private String ciudad;
    private String departamento;
    private String pais;
    private Boolean principal;
    private EstadoRegistro estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
