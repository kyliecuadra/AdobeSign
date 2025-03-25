package com.adobesign.AdobeSign.model;

public class AdobeSignRequest {
    private String recipientEmail;
    private String filePath;

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}