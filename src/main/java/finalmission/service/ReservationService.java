package finalmission.service;

import finalmission.domain.Lecture;
import finalmission.domain.LectureRepository;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import finalmission.dto.request.ReservationCreateRequest;
import finalmission.dto.request.ReservationUpdateRequest;
import finalmission.dto.response.ReservationMineResponse;
import finalmission.dto.response.ReservationResponse;
import finalmission.exception.LectureException;
import finalmission.exception.MemberException;
import finalmission.exception.ReservationException;
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

    public List<ReservationResponse> findReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationMineResponse> findReservationsOfMember(long memberId) {
        return reservationRepository.findAllByMemberId(memberId).stream()
                .map(ReservationMineResponse::from)
                .toList();
    }

    @Transactional
    public ReservationResponse createReservation(ReservationCreateRequest request, long memberId) {
        Lecture lecture = lectureRepository.findById(request.lectureId())
                .orElseThrow(() -> new LectureException("존재하지 않는 강의입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원입니다."));

        Reservation reservation = new Reservation(LocalDate.now(), request.reserveCount(), lecture, member);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    @Transactional
    public ReservationResponse updateReservation(long id, ReservationUpdateRequest request, long memberId) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("존재하지 않는 예약입니다."));
        if (!reservation.isSameMember(memberId)) {
            throw new ReservationException("본인의 예약만 수정할 수 있습니다.");
        }
        reservation.changeReserveCount(request.reserveCount());
        return ReservationResponse.from(reservation);
    }

    @Transactional
    public void deleteReservationById(long id) {
        reservationRepository.deleteById(id);
    }
}
