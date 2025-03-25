package com.adobesign.AdobeSign.service;

import com.adobesign.AdobeSign.config.AdobeConfig;
import com.adobesign.AdobeSign.model.AdobeSignRequest;
import com.adobesign.AdobeSign.model.AdobeSignResponse;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AdobeSignService {
    private final AdobeConfig adobeConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    public AdobeSignService(AdobeConfig adobeConfig) {
        this.adobeConfig = adobeConfig;
    }

    // Convert MultipartFile to a File
    private File convertToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    // Send document for signing
    public AdobeSignResponse sendDocument(AdobeSignRequest request, MultipartFile file) throws IOException {
        File pdfFile = convertToFile(file);

        String url = adobeConfig.getBaseUrl() + "/agreements";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adobeConfig.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{ \"file\": \"" + pdfFile.getAbsolutePath() + "\", \"recipient\": \"" + request.getRecipientEmail() + "\" }";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return new AdobeSignResponse("123456", "Document Sent"); // Replace with actual response
    }

    // Check document status
    public AdobeSignResponse checkStatus(String agreementId) {
        String url = adobeConfig.getBaseUrl() + "/agreements/" + agreementId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adobeConfig.getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return new AdobeSignResponse(agreementId, "Signed"); // Replace with actual status
    }
}