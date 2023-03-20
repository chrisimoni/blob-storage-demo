package com.chrisimoni.azureblobstoragedemo.config;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Value("${azure.blob.connection-string}")
    String connectionString;
    @Value("${azure.blob.container-name}")
    String containerName;
    @Bean
    public BlobClientBuilder getClient() {
        BlobClientBuilder client = new BlobClientBuilder();
        client.connectionString(connectionString);
        client.containerName(containerName);
        return client;
    }

    @Bean
    public BlobContainerClient containerClient() {
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        return containerClient;
    }
}
