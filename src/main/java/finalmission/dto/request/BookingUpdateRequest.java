package finalmission.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookingUpdateRequest(
        @NotNull(message = "업데이트할 예약의 예약 인원은 필수입니다.") int reserveCount
) {
}
