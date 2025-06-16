package finalmission.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
        @NotNull(message = "로그인할 멤버 이메일은 필수입니다.") String email,
        @NotNull(message = "로그인할 멤버 비밀번호는 필수입니다.") String password
) {
}
