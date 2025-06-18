package finalmission.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import finalmission.domain.EmailClient;
import finalmission.dto.request.EmailRequest;
import finalmission.dto.response.BookingResponse;
import finalmission.infrastructure.email.EmailMessageFactory;
import finalmission.infrastructure.email.sendgrid.SendGridEmailRequestFactory;
import finalmission.infrastructure.email.sendgrid.dto.SendGridEmailRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailClient emailClient;

    @Mock
    private EmailMessageFactory messageFactory;

    @Mock
    private SendGridEmailRequestFactory requestFactory;

    @InjectMocks
    private EmailService emailService;

    @DisplayName("예약 성공에 대한 이메일 전송을 요청한다.")
    @Test
    void sendReserveSuccessEmail() {
        // given
        BookingResponse dummyResponse = mock(BookingResponse.class);

        EmailRequest emailRequest = new EmailRequest("이메일 제목", "이메일 본문", "to@example.com");
        SendGridEmailRequest sendGridEmailRequest = mock(SendGridEmailRequest.class);

        when(messageFactory.createReserveSuccessEmail(dummyResponse)).thenReturn(emailRequest);
        when(requestFactory.createEmailRequest(emailRequest)).thenReturn(sendGridEmailRequest);

        // when
        emailService.sendReserveSuccessEmail(dummyResponse);

        // then
        verify(messageFactory).createReserveSuccessEmail(dummyResponse);
        verify(requestFactory).createEmailRequest(emailRequest);
        verify(emailClient).sendEmail(sendGridEmailRequest);
    }
}
