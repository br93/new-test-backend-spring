package com.br93.testbackend.minio;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.br93.testbackend.config.MinioConfig.MinioProperties;
import com.br93.testbackend.exception.BucketNotFoundException;
import com.br93.testbackend.exception.ObjectException;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void putObject(InputStream inputStream, String objectName, String contentType) {

        String bucketName = minioProperties.getBucketName();
        long fileSize = minioProperties.getFileSize();

        this.createBucketIfNotExists();

        PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                .stream(inputStream, -1, fileSize).contentType(contentType).build();

        try {
            minioClient.putObject(args);
        } catch (Exception ex) {
            logger.info("Error while saving object {}: {}", objectName, ex.getMessage());
        }
    }

    public InputStream getObject(String objectName) {
        String bucketName = minioProperties.getBucketName();

        if(!this.bucketExists())
            throw new BucketNotFoundException("Bucket '" + bucketName + "' not found");
        
        GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
        
        try {
            return minioClient.getObject(args);
        } catch (Exception ex) {
            throw new ObjectException("Error while fetching object: " + ex.getMessage());
        }
    }

    private void createBucketIfNotExists() {
        if (!this.bucketExists()) {

            String bucketName = minioProperties.getBucketName();

            MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucketName).build();

            try {
                minioClient.makeBucket(args);
            } catch (Exception ex) {
                logger.info("Error while creating bucket {}: {}", bucketName, ex.getMessage());
            }
        }
    }

    private boolean bucketExists() {

        String bucketName = minioProperties.getBucketName();

        BucketExistsArgs args = BucketExistsArgs.builder().bucket(bucketName).build();

        try {
            return minioClient.bucketExists(args);
        } catch (Exception ex) {
            logger.info("Error while checking bucket {}: {}", bucketName, ex.getMessage());
        }

        return false;
    }

}

