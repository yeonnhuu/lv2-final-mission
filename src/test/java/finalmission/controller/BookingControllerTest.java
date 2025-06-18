package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendGetRequest;
import static finalmission.helper.RestDocsFieldSnippets.Booking.BOOKING_RESPONSE_LIST_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import io.restassured.filter.Filter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class BookingControllerTest extends ControllerTest {

    @Override
    protected String docsBaseDir() {
        return "reservation";
    }

    @DisplayName("예약 API")
    @Nested
    class BookingApi {

        @DisplayName("예약 목록 조회 API")
        @Test
        void findBookings() {
            // given
            String uri = "/bookings";

            Filter filter = createDocumentFilter(docsBaseDir(), "find-all",
                    responseFields(BOOKING_RESPONSE_LIST_FIELDS)
            );

            // when & then
            sendGetRequest(uri, spec, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
