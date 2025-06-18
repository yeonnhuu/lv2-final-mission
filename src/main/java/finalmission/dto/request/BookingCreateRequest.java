package finalmission.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookingCreateRequest(
        @NotNull(message = "생성할 예약의 강의 번호는 필수입니다.") Long lectureId,
        @NotNull(message = "생성할 예약의 예약 인원은 필수입니다.") int reserveCount
) {
}

