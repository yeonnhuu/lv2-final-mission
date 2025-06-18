package finalmission.service;

import finalmission.common.exception.LectureException;
import finalmission.common.exception.MemberException;
import finalmission.common.exception.ReservationException;
import finalmission.domain.Lecture;
import finalmission.domain.LectureRepository;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import finalmission.dto.request.BookingCreateRequest;
import finalmission.dto.request.BookingUpdateRequest;
import finalmission.dto.response.BookingMineResponse;
import finalmission.dto.response.BookingResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    public List<BookingResponse> findReservations() {
        return reservationRepository.findAll().stream()
                .map(BookingResponse::from)
                .toList();
    }

    public List<BookingMineResponse> findReservationsOfMember(long memberId) {
        return reservationRepository.findAllByMemberId(memberId).stream()
                .map(BookingMineResponse::from)
                .toList();
    }

    @Transactional
    public BookingResponse createReservation(BookingCreateRequest request, long memberId) {
        Lecture lecture = findLectureById(request.lectureId());
        Member member = findMemberById(memberId);
        Reservation reservation = new Reservation(LocalDate.now(), request.reserveCount(), lecture, member);

        Reservation savedReservation = reservationRepository.save(reservation);
        return BookingResponse.from(savedReservation);
    }

    @Transactional
    public BookingResponse updateReservation(long id, BookingUpdateRequest request, long memberId) {
        Reservation reservation = findReservationById(id);
        validateUpdateMember(memberId, reservation);
        reservation.changeReserveCount(request.reserveCount());
        return BookingResponse.from(reservation);
    }

    @Transactional
    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }

    private void validateUpdateMember(final long memberId, final Reservation reservation) {
        if (!reservation.isSameMember(memberId)) {
            throw new ReservationException("본인의 예약만 수정할 수 있습니다.");
        }
    }

    private Reservation findReservationById(final long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("존재하지 않는 예약입니다."));
    }

    private Lecture findLectureById(long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureException("존재하지 않는 강의입니다."));
    }

    private Member findMemberById(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원입니다."));
    }
}
