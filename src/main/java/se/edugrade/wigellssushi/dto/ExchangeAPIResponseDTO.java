package se.edugrade.wigellssushi.dto;

import java.util.Map;

public class ExchangeAPIResponseDTO {
    public boolean success;
    public Map<String, Float> rates;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }
}
