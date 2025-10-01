package se.edugrade.wigellssushi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fx")
public class FxProperties {
    private String baseUrl;
    private String apiKey;
    private int apiTimeout = 30;

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
    public int getApiTimeout() {
        return apiTimeout;
    }
    public void setApiTimeout(int apiTimeout) {
        this.apiTimeout = apiTimeout;
    }
}
