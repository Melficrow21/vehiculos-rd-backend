package com.vehiculosrd.imagenes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.time.Duration;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${storage.bucket:}")
    private String bucket;

    // Para Cloudflare R2: https://<account_id>.r2.cloudflarestorage.com
    // Para AWS S3: dejar vacio y usar solo la region
    @Value("${storage.endpoint:}")
    private String endpoint;

    @Value("${storage.access-key:}")
    private String accessKey;

    @Value("${storage.secret-key:}")
    private String secretKey;

    // URL publica desde donde se sirven las imagenes (dominio del bucket o CDN)
    @Value("${storage.public-url:}")
    private String publicUrl;

    private S3Presigner presigner() {
        var credenciales = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey));

        var builder = S3Presigner.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credenciales);

        if (!endpoint.isBlank()) {
            builder.endpointOverride(URI.create(endpoint));
        }

        return builder.build();
    }

    // Genera una URL temporal (10 minutos) a la que el FRONTEND sube el archivo
    // directamente con un PUT, sin pasar por nuestro servidor.
    public UrlSubidaResponse generarUrlSubida(String contentType, String extension) {
        String key = "vehiculos/" + UUID.randomUUID() + "." + extension;

        try (S3Presigner presigner = presigner()) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putRequest)
                    .build();

            String uploadUrl = presigner.presignPutObject(presignRequest).url().toString();
            return new UrlSubidaResponse(uploadUrl, urlPublicaFinal(key));
        }
    }

    private String urlPublicaFinal(String key) {
        return publicUrl.isBlank()
                ? endpoint + "/" + bucket + "/" + key
                : publicUrl + "/" + key;
    }
}
