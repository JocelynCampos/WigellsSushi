package se.edugrade.wigellssushi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
@ConfigurationProperties(prefix = "fx")
public class FxProperties {
    private String baseUrl;
    private String apiKey;

    private int ttlMinutes = 30;

    private int connectTimeoutMs = 5000;
    private int readTimeoutMs = 5000;

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public int getTtlMinutes() {
        return ttlMinutes;
    }
    public void setTtlMinutes(int ttlMinutes) {
        this.ttlMinutes = ttlMinutes;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getReadTimeoutMs() {
        return readTimeoutMs;
    }

    public void setReadTimeoutMs(int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }
}
**/