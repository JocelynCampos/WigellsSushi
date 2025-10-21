package se.edugrade.wigellssushi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.wigellssushi.dto.ExchangeAPIResponse;

@Service
public class CurrencyService {
    private final RestClient restClient;

    @Value("${currency.api.key}")
    private String apiKey;

    public CurrencyService(RestClient.Builder restClientBuilder, @Value("${currency.api.base-url}") String baseUrl) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    @Cacheable("exchangeRate")
    public float getSEKtoEUR() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing or invalid api key");
        }

        ExchangeAPIResponse response = restClient.get()
                .uri(uri-> uri.path("/latest")
                        .queryParam("access_key", apiKey)
                        .queryParam("base", "EUR")
                        .queryParam("symbols", "SEK")
                        .build())
                .retrieve()
                .body(ExchangeAPIResponse.class);
        if (response.rates != null && response.success != false && response.rates != null && response.rates.containsKey("SEK")) {
            return response.rates.get("SEK");
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not get SEK from exchange currency API");
        }

    }
}


