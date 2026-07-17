package backend.academic.infrastructure.rest.dto.AreaAcademica;

import backend.academic.domain.model.AreaAcademica;
import java.util.UUID;

public class AreaAcademicaResponseDto {

    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Boolean activa;
    private Long totalAsignaturas;

    public AreaAcademicaResponseDto() {}

    public static AreaAcademicaResponseDto desde(AreaAcademica a) {
        AreaAcademicaResponseDto d = new AreaAcademicaResponseDto();
        d.setId(a.getId());
        d.setCodigo(a.getCodigo());
        d.setNombre(a.getNombre());
        d.setDescripcion(a.getDescripcion());
        d.setActiva(a.getActiva());
        return d;
    }

    public static AreaAcademicaResponseDto desde(AreaAcademica a, long totalAsignaturas) {
        AreaAcademicaResponseDto d = desde(a);
        d.setTotalAsignaturas(totalAsignaturas);
        return d;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
    public Long getTotalAsignaturas() { return totalAsignaturas; }
    public void setTotalAsignaturas(Long totalAsignaturas) { this.totalAsignaturas = totalAsignaturas; }
}