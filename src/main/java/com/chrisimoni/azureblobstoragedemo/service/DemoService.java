package com.chrisimoni.azureblobstoragedemo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DemoService {
    String uploadToContainer(MultipartFile file) throws IOException;

    List<String> getAllBlobsInContainer() throws Exception;

    void deleteBlobInContainer(String blobName);
}
