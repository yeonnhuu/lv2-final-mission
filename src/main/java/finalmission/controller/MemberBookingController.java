package finalmission.controller;

import finalmission.common.annotation.LoginMember;
import finalmission.dto.info.MemberLoginInfo;
import finalmission.dto.request.BookingCreateRequest;
import finalmission.dto.request.BookingUpdateRequest;
import finalmission.dto.response.BookingMineResponse;
import finalmission.dto.response.BookingResponse;
import finalmission.service.BookingService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class MemberBookingController {

    private final BookingService bookingService;

    @GetMapping("/me")
    public ResponseEntity<List<BookingMineResponse>> findMyBookings(@LoginMember MemberLoginInfo memberInfo) {
        log.info("멤버 예약 목록 조회 요청: memberId={}", memberInfo.id());
        List<BookingMineResponse> response = bookingService.findBookingsOfMember(memberInfo.id());

        log.debug("멤버 예약 목록 조회 요청 완료: 총 {}건", response.size());
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingCreateRequest request, @LoginMember MemberLoginInfo memberInfo) {
        log.info("멤버 예약 생성 요청: lectureId={}, memberId={}", request.lectureId(), memberInfo.id());
        BookingResponse response = bookingService.createBooking(request, memberInfo.id());

        log.debug("멤버 예약 생성 완료: reservationId={}, memberId={}", response.id(), memberInfo.id());
        return ResponseEntity.created(URI.create("/reservation"))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable("id") long id, @RequestBody @Valid BookingUpdateRequest request, @LoginMember MemberLoginInfo memberInfo) {
        log.info("멤버 예약 수정 요청: reservationId={}, reserveCount={}, memberId={}", id, request.reserveCount(), memberInfo.id());
        BookingResponse response = bookingService.updateBooking(id, request, memberInfo.id());

        log.debug("멤버 예약 수정 완료: reservationId={}, memberId={}", response.id(), memberInfo.id());
        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") long id, @LoginMember MemberLoginInfo memberInfo) {
        log.info("멤버 예약 삭제 요청: reservationId={}, memberId={}", id, memberInfo.id());
        bookingService.deleteBooking(id);

        log.debug("멤버 예약 삭제 완료: reservationId={}, memberId={}", id, memberInfo.id());
        return ResponseEntity.noContent()
                .build();
    }
}

