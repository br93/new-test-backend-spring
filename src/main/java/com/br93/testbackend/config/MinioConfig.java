package com.br93.testbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;

@Configuration
public class MinioConfig {

    @Bean
    @ConfigurationProperties("minio")
    MinioProperties minioProperties() {
        return new MinioProperties();
    }

    @Setter
    @Getter
    public static class MinioProperties {
        private String endpoint;
        private Integer port;
        private String accessKey;
        private String secretKey;
        private String bucketName;
        private boolean secure;
        private long fileSize;
    }

    @Bean
    MinioClient minioClient(MinioProperties properties) {
        return MinioClient.builder().credentials(properties.getAccessKey(), properties.getSecretKey())
                .endpoint(properties.getEndpoint(), properties.getPort(), properties.isSecure()).build();
    }

}
