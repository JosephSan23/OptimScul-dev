package backend.academic.infrastructure.rest.dto.Grupo;

import backend.academic.domain.model.Grupo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GrupoResponseDto {

    private UUID id;
    private UUID gradoId;
    private UUID anioLectivoId;
    private UUID sedeId;
    private UUID jornadaId;
    private String codigo;
    private String nombre;
    private Integer cupoMaximo;
    private String estado;
    private String observaciones;
    private Long totalEstudiantes; 

    public static GrupoResponseDto desde(Grupo g) {
        GrupoResponseDto d = new GrupoResponseDto();
        d.setId(g.getId());
        d.setGradoId(g.getGradoId());
        d.setAnioLectivoId(g.getAnioLectivoId());
        d.setSedeId(g.getSedeId());
        d.setJornadaId(g.getJornadaId());
        d.setCodigo(g.getCodigo());
        d.setNombre(g.getNombre());
        d.setCupoMaximo(g.getCupoMaximo());
        d.setEstado(g.getEstado() != null ? g.getEstado().name() : null);
        d.setObservaciones(g.getObservaciones());
        return d;
    }

    public static GrupoResponseDto desde(Grupo g, long totalEstudiantes) {
        GrupoResponseDto d = desde(g);
        d.setTotalEstudiantes(totalEstudiantes);
        return d;
    }
}