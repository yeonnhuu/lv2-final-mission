package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendDeleteRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendGetRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendPostRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendPutRequestWithToken;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_CREATE_REQUEST_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_DELETE_PATH_PARAMETERS;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_MINE_RESPONSE_LIST_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_RESPONSE_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_UPDATE_PATH_PARAMETERS;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_UPDATE_REQUEST_FIELDS;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import finalmission.dto.request.BookingCreateRequest;
import finalmission.dto.request.BookingUpdateRequest;
import finalmission.service.EmailService;
import io.restassured.filter.Filter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@Import(MemberBookingControllerTest.TestMockConfig.class)
public class MemberBookingControllerTest extends ControllerTest {

    @Autowired
    private EmailService emailService;

    @TestConfiguration
    static class TestMockConfig {
        @Bean
        public EmailService emailService() {
            return mock(EmailService.class);
        }
    }

    @Override
    protected String docsBaseDir() {
        return "member-reservation";
    }

    @DisplayName("멤버 예약 API")
    @Nested
    class MemberBookingApi {

        @DisplayName("예약 목록 조회 API")
        @Test
        void findMyBookings() {
            // given
            String uri = "/bookings/me";
            String token = extractTestMemberLoginToken();

            Filter filter = createDocumentFilter(docsBaseDir(), "find-all",
                    responseFields(BOOKING_MINE_RESPONSE_LIST_FIELDS)
            );

            // when & then
            sendGetRequestWithToken(uri, spec, token, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("예약 생성 API")
        @Test
        void createBooking() {
            // given
            String uri = "/bookings";
            String token = extractTestMemberLoginToken();
            BookingCreateRequest request = new BookingCreateRequest(1L, 1);

            Filter filter = createDocumentFilter(docsBaseDir(), "create",
                    requestFields(BOOKING_CREATE_REQUEST_FIELDS),
                    responseFields(BOOKING_RESPONSE_FIELDS)
            );

            // when & then
            sendPostRequestWithToken(uri, request, spec, token, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @DisplayName("예약 수정 API")
        @Test
        void updateBooking() {
            // given
            String uri = "/bookings/{id}";
            long id = 1;
            String token = extractTestMemberLoginToken();
            BookingUpdateRequest request = new BookingUpdateRequest(3);

            Filter filter = createDocumentFilter(docsBaseDir(), "update",
                    pathParameters(BOOKING_UPDATE_PATH_PARAMETERS),
                    requestFields(BOOKING_UPDATE_REQUEST_FIELDS),
                    responseFields(BOOKING_RESPONSE_FIELDS)
            );

            // when & then
            sendPutRequestWithToken(uri, request, spec, token, filter, id)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("예약 삭제 API")
        @Test
        void deleteBooking() {
            // given
            String uri = "/bookings/{id}";
            long id = 1;
            String token = extractTestMemberLoginToken();

            Filter filter = createDocumentFilter(docsBaseDir(), "delete",
                    pathParameters(BOOKING_DELETE_PATH_PARAMETERS)
            );

            // when & then
            sendDeleteRequestWithToken(uri, spec, token, filter, id)
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }
}

