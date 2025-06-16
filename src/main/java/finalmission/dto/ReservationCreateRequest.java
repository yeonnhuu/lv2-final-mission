package finalmission.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest(
        @NotNull(message = "운동 클래스 번호는 필수입니다.") Long lectureId,
        @NotNull(message = "운동 클래스 예약 인원은 필수입니다.") int reserveCount
) {
}

