package backend.shared.infrastructure.storage;

import backend.shared.application.port.StoragePort;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.InputStream;
import java.time.Duration;

@Component
public class S3StorageAdapter implements StoragePort {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final StorageProperties props;

    public S3StorageAdapter(S3Client s3Client, S3Presigner s3Presigner, StorageProperties props) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.props = props;
    }

    @Override
    public String subir(String clave, InputStream contenido, long tamanoBytes, String mimeType) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(props.getBucket())
                .key(clave)
                .contentType(mimeType)
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(contenido, tamanoBytes));
        return clave;
    }

    @Override
    public String generarUrlDescarga(String clave, Duration duracion) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(props.getBucket())
                .key(clave)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duracion)
                .getObjectRequest(getRequest)
                .build();
        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    @Override
    public void eliminar(String clave) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(props.getBucket())
                .key(clave)
                .build());
    }
}