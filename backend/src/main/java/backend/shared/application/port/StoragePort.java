package backend.shared.application.port;

import java.io.InputStream;
import java.time.Duration;

public interface StoragePort {

    String subir(String clave, InputStream contenido, long tamanoBytes, String mimeType);

    String generarUrlDescarga(String clave, Duration duracion);

    void eliminar(String clave);
}