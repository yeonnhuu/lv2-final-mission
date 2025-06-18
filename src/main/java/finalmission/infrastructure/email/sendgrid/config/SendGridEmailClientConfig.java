package finalmission.infrastructure.email.sendgrid.config;

import finalmission.infrastructure.email.sendgrid.SendGridEmailClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class SendGridEmailClientConfig {

    private static final String AUTH_TYPE_BEARER = "Bearer";
    private static final String AUTH_DELIMITER = " ";
    private static final String URL_DELIMITER = "/";

    @Value("${email.sendgrid.base-url}")
    private String baseUrl;

    @Value("${email.sendgrid.api-key}")
    private String apiKey;

    @Value("${email.connect-timeout-length}")
    private Duration connectTimeout;

    @Value("${email.read-timeout-length}")
    private Duration readTimeout;

    @Bean
    public SendGridEmailClient sendGridClient() {
        return new SendGridEmailClient(createRestClient());
    }

    private RestClient createRestClient() {
        return RestClient.builder()
                .requestFactory(createRequestFactory())
                .baseUrl(String.join(URL_DELIMITER, baseUrl, "v3", "mail"))
                .defaultHeader(HttpHeaders.AUTHORIZATION, createAuthorizationHeader())
                .build();
    }

    private ClientHttpRequestFactory createRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) connectTimeout.toMillis());
        factory.setReadTimeout((int) readTimeout.toMillis());
        return factory;
    }

    private String createAuthorizationHeader() {
        return AUTH_TYPE_BEARER + AUTH_DELIMITER + apiKey;
    }
}
