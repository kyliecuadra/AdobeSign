package com.adobesign.AdobeSign.controller;

import com.adobesign.AdobeSign.model.AdobeSignRequest;
import com.adobesign.AdobeSign.model.AdobeSignResponse;
import com.adobesign.AdobeSign.service.AdobeSignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/adobe-sign")
public class AdobeSignController {

    private final AdobeSignService adobeSignService;

    public AdobeSignController(AdobeSignService adobeSignService) {
        this.adobeSignService = adobeSignService;
    }

    @PostMapping("/send")
    public ResponseEntity<AdobeSignResponse> sendDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("recipientEmail") String recipientEmail) {

        AdobeSignRequest request = new AdobeSignRequest();
        request.setRecipientEmail(recipientEmail);

        try {
            AdobeSignResponse response = adobeSignService.sendDocument(request, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new AdobeSignResponse(null, "Failed to send document"));
        }
    }

    @GetMapping("/status/{agreementId}")
    public ResponseEntity<AdobeSignResponse> checkStatus(@PathVariable String agreementId) {
        AdobeSignResponse response = adobeSignService.checkStatus(agreementId);
        return ResponseEntity.ok(response);
    }
}