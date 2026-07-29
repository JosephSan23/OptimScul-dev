package backend.shared.infrastructure.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "storage.upload")
public class SubidaProperties {

    private int maxArchivosPorEntrega = 5;
    private int maxTamanoMb = 25;
    private List<String> mimePermitidos = List.of(
            "application/pdf",
            "image/png",
            "image/jpeg",
            "image/jpg",
            "image/webp",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "text/plain",
            "application/zip"
    );

    public int getMaxArchivosPorEntrega() { return maxArchivosPorEntrega; }
    public void setMaxArchivosPorEntrega(int v) { this.maxArchivosPorEntrega = v; }
    public int getMaxTamanoMb() { return maxTamanoMb; }
    public void setMaxTamanoMb(int v) { this.maxTamanoMb = v; }
    public List<String> getMimePermitidos() { return mimePermitidos; }
    public void setMimePermitidos(List<String> v) { this.mimePermitidos = v; }
}