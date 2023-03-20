package com.chrisimoni.azureblobstoragedemo.controller;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.models.BlobItem;
import com.chrisimoni.azureblobstoragedemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadToContainer(@RequestParam(value = "file", required = true) MultipartFile file)
            throws Exception {
        String url = demoService.uploadToContainer(file);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllBlobsInContainer()
            throws Exception {
        List<String> url = demoService.getAllBlobsInContainer();
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlobInContainer(@RequestParam(value = "blobName", required = true) String blobName)
            throws Exception {
        demoService.deleteBlobInContainer(blobName);
        return ResponseEntity.ok("url");
    }
}
