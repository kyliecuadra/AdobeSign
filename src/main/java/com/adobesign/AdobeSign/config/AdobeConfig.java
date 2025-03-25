package com.adobesign.AdobeSign.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdobeConfig {

    @Value("${adobe.api.clientId}")
    private String clientId;

    @Value("${adobe.api.clientSecret}")
    private String clientSecret;

    @Value("${adobe.api.accessToken}")
    private String accessToken;

    @Value("${adobe.api.baseUrl}")
    private String baseUrl;

    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
    public String getAccessToken() { return accessToken; }
    public String getBaseUrl() { return baseUrl; }
}