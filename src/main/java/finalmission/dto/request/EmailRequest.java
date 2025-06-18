package finalmission.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmailRequest(
        @NotNull(message = "이메일 제목은 필수입니다.") String subject,
        @NotNull(message = "이메일 본문은 필수입니다.") String value,
        @NotNull(message = "수신 이메일 주소는 필수입니다.") String toEmail
) {
}
