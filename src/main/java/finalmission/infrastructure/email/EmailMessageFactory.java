package finalmission.infrastructure.email;

import finalmission.dto.request.EmailRequest;
import finalmission.dto.response.BookingResponse;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageFactory {

    private static final String SUBJECT_FORMAT = "%s 클래스 예약이 완료되었습니다!";
    private static final String CONTENT_FORMAT = "%s %s 클래스 예약이 완료되었습니다. 감사합니다.";

    public EmailRequest createReserveSuccessEmail(BookingResponse response) {
        String sport = response.lecture().sport();
        String date = String.valueOf(response.lecture().date());

        String subject = String.format(SUBJECT_FORMAT, sport);
        String content = String.format(CONTENT_FORMAT, sport, date);
        String toEmail = response.member().email();

        return new EmailRequest(subject, content, toEmail);
    }
}
