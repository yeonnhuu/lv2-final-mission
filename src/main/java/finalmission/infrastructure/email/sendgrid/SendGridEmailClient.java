package finalmission.infrastructure.email.sendgrid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.common.exception.EmailSendException;
import finalmission.domain.EmailClient;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class SendGridEmailClient implements EmailClient {

    private final RestClient restClient;

    @Override
    public void sendEmail(SendGridEmailRequest emailRequest) {
        restClient.post()
                .uri("/send")
                .contentType(MediaType.APPLICATION_JSON)
                .body(emailRequest)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        (request, response) -> {
                            HttpStatus status = (HttpStatus) response.getStatusCode();
                            String errorBody = response.getBody().toString();
                            String errorMessage = extractErrorMessage(errorBody);

                            String message = String.format("이메일 전송 실패: status=%s, message=%s", status, errorMessage);
                            throw new EmailSendException(message);
                        })
                .toBodilessEntity();
    }

    private String extractErrorMessage(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            return root.path("errors").get(0).path("message").asText();
        } catch (Exception e) {
            return "알 수 없는 오류";
        }
    }
}
