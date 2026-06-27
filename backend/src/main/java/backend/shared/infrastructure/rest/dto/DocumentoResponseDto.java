package backend.shared.infrastructure.rest.dto;

import java.util.UUID;
import backend.shared.ModuloDocumento;

public class DocumentoResponseDto {

    private UUID institucionId;

    private ModuloDocumento modulo;

    private UUID entidadId;

    private String nombreArchivo;

    private String nombreOriginal;

    private String urlArchivo;

    private String mimeType;

    private Integer tamanoBytes;

    private String descripcion;

    private UUID subidoPorUsuarioId;

    public DocumentoResponseDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public ModuloDocumento getModulo() { return modulo; }
    public void setModulo(ModuloDocumento modulo) { this.modulo = modulo; }
    public UUID getEntidadId() { return entidadId; }
    public void setEntidadId(UUID entidadId) { this.entidadId = entidadId; }
    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }
    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String urlArchivo) { this.urlArchivo = urlArchivo; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public Integer getTamanoBytes() { return tamanoBytes; }
    public void setTamanoBytes(Integer tamanoBytes) { this.tamanoBytes = tamanoBytes; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public UUID getSubidoPorUsuarioId() { return subidoPorUsuarioId; }
    public void setSubidoPorUsuarioId(UUID subidoPorUsuarioId) { this.subidoPorUsuarioId = subidoPorUsuarioId; }
}
