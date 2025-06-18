package finalmission.dto.response;

import finalmission.domain.Lecture;
import finalmission.domain.Reservation;
import java.time.LocalDate;

public record BookingMineResponse(Long id, LocalDate reservedAt, int reserveCount, LectureResponse lecture) {

    public static BookingMineResponse from(Reservation reservation) {
        Lecture lecture = reservation.lecture();
        return new BookingMineResponse(
                reservation.id(),
                reservation.reservedAt(),
                reservation.reserveCount(),
                new LectureResponse(lecture.sport(), lecture.date())
        );
    }
}
