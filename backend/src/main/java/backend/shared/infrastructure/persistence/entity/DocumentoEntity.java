package backend.shared.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.shared.ModuloDocumento;

@Entity
@Table(name = "documento", schema = "optimscul")
public class DocumentoEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "modulo")
    private ModuloDocumento modulo;

    @Column(name = "entidad_id")
    private UUID entidadId;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "nombre_original")
    private String nombreOriginal;

    @Column(name = "url_archivo")
    private String urlArchivo;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "tamano_bytes")
    private Long tamanoBytes;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "subido_por_usuario_id")
    private UUID subidoPorUsuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public DocumentoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public Long getTamanoBytes() { return tamanoBytes; }
    public void setTamanoBytes(Long tamanoBytes) { this.tamanoBytes = tamanoBytes; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public UUID getSubidoPorUsuarioId() { return subidoPorUsuarioId; }
    public void setSubidoPorUsuarioId(UUID subidoPorUsuarioId) { this.subidoPorUsuarioId = subidoPorUsuarioId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
