package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendGetRequest;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_RESPONSE_LIST_FIELDS;
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

    @Nested
    @DisplayName("예약 API")
    class ReservationApi {

        @Test
        @DisplayName("예약 목록 조회 API")
        void findReservations() {
            Filter filter = createDocumentFilter(docsBaseDir(), "find-all",
                    responseFields(RESERVATION_RESPONSE_LIST_FIELDS)
            );

            sendGetRequest("/reservations", spec, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
