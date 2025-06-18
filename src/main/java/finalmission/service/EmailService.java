package finalmission.service;

import finalmission.domain.EmailClient;
import finalmission.dto.request.EmailRequest;
import finalmission.dto.response.BookingResponse;
import finalmission.infrastructure.email.EmailMessageFactory;
import finalmission.infrastructure.email.sendgrid.SendGridEmailRequestFactory;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;
    private final EmailMessageFactory messageFactory;
    private final SendGridEmailRequestFactory requestFactory;

    public void sendReserveSuccessEmail(BookingResponse response) {
        EmailRequest emailRequest = messageFactory.createReserveSuccessEmail(response);
        SendGridEmailRequest sendGridRequest = requestFactory.createEmailRequest(emailRequest);
        emailClient.sendEmail(sendGridRequest);
    }
}
