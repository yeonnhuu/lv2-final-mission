package finalmission.domain;

import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest;

public interface EmailClient {

    void sendEmail(SendGridEmailRequest request);
}
