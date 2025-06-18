package finalmission.infrastructure.email.sendgrid;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import finalmission.common.exception.EmailSendException;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.Content;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.From;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.Personalization;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.Personalization.To;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class SendGridEmailClientTest {

    private static final String BASE_URL = "https://api.sendgrid.com/v3/mail";

    private final RestClient.Builder testBuilder = RestClient.builder()
            .baseUrl(BASE_URL);

    private final MockRestServiceServer server = MockRestServiceServer.bindTo(testBuilder).build();
    private final SendGridEmailClient sendGridEmailClient = new SendGridEmailClient(testBuilder.build());

    @BeforeEach
    void setUp() {
        server.reset();
    }

    @DisplayName("이메일 전송을 요청한다.")
    @Test
    void sendEmail() {
        // given
        server.expect(requestTo(BASE_URL + "/send"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());

        String fromEmail = "yeonnhuu@gmail.com";
        SendGridEmailRequest request = createSendGridEmailRequest(fromEmail);

        // when & then
        assertDoesNotThrow(() -> sendGridEmailClient.sendEmail(request));
    }

    @DisplayName("이메일 전송에 실패하면 예외가 발생한다.")
    @Test
    void throwEmailSendException() {
        // given
        String expectedBody = """
                {
                    "errors": [
                        {
                            "message": "The from address does not match a verified Sender Identity. Mail cannot be sent until this error is resolved. Visit https://sendgrid.com/docs/for-developers/sending-email/sender-identity/ to see the Sender Identity requirements",
                            "field": "from",
                            "help": null
                        }
                    ]
                }
                """;

        server.expect(requestTo(BASE_URL + "/send"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withStatus(HttpStatus.FORBIDDEN)
                                .body(expectedBody)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        String fromEmail = "unverified@email.com";
        SendGridEmailRequest request = createSendGridEmailRequest(fromEmail);

        // when & then
        assertThatCode(() -> sendGridEmailClient.sendEmail(request))
                .isInstanceOf(EmailSendException.class)
                .hasMessageContaining("이메일 전송 실패");
    }

    private SendGridEmailRequest createSendGridEmailRequest(String fromEmail) {
        String toEmail = "yeonnhuu@gmail.com";
        To to = new To(toEmail);
        String subject = "이메일 제목";
        Personalization personalization = new Personalization(List.of(to), subject);

        From from = new From(fromEmail);

        String type = "text/plain";
        String value = "이메일 본문";
        Content content = new Content(type, value);

       return new SendGridEmailRequest(List.of(personalization), from, List.of(content));
    }
}
