package se.edugrade.wigellssushi.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(FxProperties.class)
public class HttpConfig {
    @Bean
    public RestTemplate restTemplate(FxProperties fx) {
        var f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(fx.getConnectTimeoutMs());
        f.setReadTimeout(fx.getReadTimeoutMs());

        return new RestTemplate(f);
    }
}
