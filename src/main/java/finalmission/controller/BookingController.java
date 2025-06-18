package finalmission.controller;

import finalmission.dto.response.BookingResponse;
import finalmission.service.BookingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> findBookings() {
        log.info("전체 예약 목록 조회 요청");
        List<BookingResponse> response = bookingService.findBookings();

        log.debug("전체 예약 목록 조회 완료: 총 {}건", response.size());
        return ResponseEntity.ok()
                .body(response);
    }
}
