package finalmission.service;

import finalmission.dto.request.BookingCreateRequest;
import finalmission.dto.request.BookingUpdateRequest;
import finalmission.dto.response.BookingMineResponse;
import finalmission.dto.response.BookingResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ReservationService reservationService;
    private final EmailService emailService;

    public List<BookingResponse> findBookings() {
        return reservationService.findReservations();
    }

    public List<BookingMineResponse> findBookingsOfMember(long memberId) {
        return reservationService.findReservationsOfMember(memberId);
    }

    public BookingResponse createBooking(BookingCreateRequest request, long memberId) {
        BookingResponse bookingResponse = reservationService.createReservation(request, memberId);
        emailService.sendReserveSuccessEmail(bookingResponse);
        return bookingResponse;
    }

    public BookingResponse updateBooking(long id, BookingUpdateRequest request, long memberId) {
       return reservationService.updateReservation(id, request, memberId);
    }

    public void deleteBooking(long id) {
        reservationService.deleteReservation(id);
    }
}
