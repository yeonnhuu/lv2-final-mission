package finalmission.dto.response;

import finalmission.domain.Lecture;
import finalmission.domain.Member;
import finalmission.domain.Reservation;

public record BookingResponse(Long id, LectureResponse lecture, MemberResponse member) {

    public static BookingResponse from(Reservation reservation) {
        Lecture lecture = reservation.lecture();
        Member member = reservation.member();
        return new BookingResponse(
                reservation.id(),
                new LectureResponse(lecture.sport(), lecture.date()),
                new MemberResponse(member.name(), member.email())
        );
    }
}
