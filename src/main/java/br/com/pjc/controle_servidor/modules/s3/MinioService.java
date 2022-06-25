package br.com.pjc.controle_servidor.modules.s3;

import br.com.pjc.controle_servidor.modules.pessoa.dto.FotoResponseDTO;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class MinioService {
    public MinioService() {
    }

    private final Logger logger = Logger.getLogger(MinioService.class);
    @Inject
    MinioClient minioClient;


    public void enviarArquivo(MinioSendFile file) {
        prepararBucket(file.getBucket());
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(file.getBucket()).object(file.getName()).stream(
                                    file.getFile(), -1, 10485760)
                            .contentType(file.getContentType())
                            .build());
        } catch (ErrorResponseException | ServerException | NoSuchAlgorithmException | XmlParserException |
                 IOException | InvalidResponseException | InvalidKeyException | InternalException |
                 InsufficientDataException e) {
            logger.error("Falha ao enviar o arquivo", e);
            throw new RuntimeException(e);
        }
    }

    private void prepararBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                logger.infov("Bucket {0} criado com sucesso", bucketName);
            }
        } catch (ErrorResponseException | NoSuchAlgorithmException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | IOException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public FotoResponseDTO buscarArquivo(String bucket, String hash) {
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucket)
                        .object(hash)
                        .expiry(5, TimeUnit.MINUTES)
                        .build());
        return FotoResponseDTO.builder()
                .url(url)
                .build();
    }
}
