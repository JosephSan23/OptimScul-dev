package backend.shared.infrastructure.storage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
@EnableConfigurationProperties({ StorageProperties.class, SubidaProperties.class })
public class StorageConfig {

    private StaticCredentialsProvider credenciales(StorageProperties p) {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(p.getAccessKey(), p.getSecretKey()));
    }

    private S3Configuration serviceConfig(StorageProperties p) {
        return S3Configuration.builder()
                .pathStyleAccessEnabled(p.isPathStyleAccess())
                .build();
    }

    @Bean
    public S3Client s3Client(StorageProperties p) {
        return S3Client.builder()
                .endpointOverride(URI.create(p.getEndpoint()))
                .region(Region.of(p.getRegion()))
                .credentialsProvider(credenciales(p))
                .serviceConfiguration(serviceConfig(p))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(StorageProperties p) {
        return S3Presigner.builder()
                .endpointOverride(URI.create(p.getEndpoint()))
                .region(Region.of(p.getRegion()))
                .credentialsProvider(credenciales(p))
                .serviceConfiguration(serviceConfig(p))
                .build();
    }
}