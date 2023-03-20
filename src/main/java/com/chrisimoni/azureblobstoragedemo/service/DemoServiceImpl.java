package com.chrisimoni.azureblobstoragedemo.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService{
    @Autowired
    private BlobClientBuilder client;
    @Autowired
    private BlobContainerClient blobContainerClient;
    //@Override
//    public String upload(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) {
//            throw new RuntimeException("You must upload a file");
//        }
//
//        String filename = file.getOriginalFilename();
//        String path = "";
//        try {
//
//            client.blobName(filename).buildClient().upload(file.getInputStream(),file.getSize(), true);
//
//            OffsetDateTime expiryTime = OffsetDateTime.now().plusMonths(1);
//            BlobSasPermission blobSasPermission = new BlobSasPermission().setReadPermission(true);
//            BlobServiceSasSignatureValues serviceSasValues = new BlobServiceSasSignatureValues(expiryTime, blobSasPermission);
//
//            String sas = client.buildClient().generateSas(serviceSasValues);
//            String url = client.buildClient().getBlobUrl();
//
//            path = url + "?" + sas;
//
//        }catch (IOException ex) {
//            throw new IOException(ex.getMessage());
//        }
//
//        return path;
//    }

    @Override
    public String uploadToContainer(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(filename);
            blobClient.upload(file.getInputStream(), file.getSize(), true);

            String path = generateSas(blobClient);

            return path;

        }catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    @Override
    public List<String>  getAllBlobsInContainer() throws Exception {
        List<String> paths = new ArrayList<>();
        try {
            for(BlobItem blobItem: blobContainerClient.listBlobs()){
                BlobClient blobClient = blobContainerClient.getBlobClient(blobItem.getName());
                paths.add(generateSas(blobClient));
            }

            return paths;
        }catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public void deleteBlobInContainer(String blobName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        blobClient.deleteIfExists();
    }

    private String generateSas(BlobClient blobClient) {
        OffsetDateTime expiryTime = OffsetDateTime.now().plusMonths(1);
        BlobSasPermission blobSasPermission = new BlobSasPermission().setReadPermission(true);
        BlobServiceSasSignatureValues serviceSasValues = new BlobServiceSasSignatureValues(expiryTime, blobSasPermission);

        String sas = blobClient.generateSas(serviceSasValues);
        String blobUrl = blobClient.getBlobUrl();

        return blobUrl + "?" + sas;
    }



}
