package finalmission.infrastructure.email.sendgrid;

import finalmission.dto.request.EmailRequest;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.Content;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.From;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.Personalization;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest.Personalization.To;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendGridEmailRequestFactory {

    @Value("${email.sendgrid.from}")
    private String fromEmail;

    public SendGridEmailRequest createEmailRequest(EmailRequest request) {
        Personalization personalization = createPersonalization(request);
        From from = createFrom();
        Content content = createContent(request);
        return new SendGridEmailRequest(
                List.of(personalization),
                from,
                List.of(content)
        );
    }

    private Personalization createPersonalization(EmailRequest request) {
        To to = new To(request.toEmail());
        String subject = request.subject();
        return new Personalization(List.of(to), subject);
    }

    private From createFrom() {
        return new From(fromEmail);
    }

    private Content createContent(EmailRequest request) {
        String type = "text/plain";
        String value = request.value();
        return new Content(type, value);
    }
}
