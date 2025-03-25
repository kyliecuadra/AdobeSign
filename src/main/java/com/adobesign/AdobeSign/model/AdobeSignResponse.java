package com.adobesign.AdobeSign.model;

public class AdobeSignResponse {
    private String agreementId;
    private String status;

    public AdobeSignResponse(String agreementId, String status) {
        this.agreementId = agreementId;
        this.status = status;
    }

    public String getAgreementId() { return agreementId; }
    public String getStatus() { return status; }
}