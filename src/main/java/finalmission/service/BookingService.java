package finalmission.service;

import finalmission.dto.request.ReservationCreateRequest;
import finalmission.dto.request.ReservationUpdateRequest;
import finalmission.dto.response.ReservationMineResponse;
import finalmission.dto.response.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ReservationService reservationService;
    private final EmailService emailService;

    public List<ReservationResponse> findReservations() {
        return reservationService.findReservations();
    }

    public List<ReservationMineResponse> findReservationsOfMember(long memberId) {
        return reservationService.findReservationsOfMember(memberId);
    }

    public ReservationResponse createReservation(ReservationCreateRequest request, long memberId) {
        ReservationResponse reservationResponse = reservationService.createReservation(request, memberId);
        emailService.sendReserveSuccessEmail(reservationResponse);
        return reservationResponse;
    }

    public ReservationResponse updateReservation(long id, ReservationUpdateRequest request, long memberId) {
       return reservationService.updateReservation(id, request, memberId);
    }

    public void deleteReservationById(long id) {
        reservationService.deleteReservationById(id);
    }
}
