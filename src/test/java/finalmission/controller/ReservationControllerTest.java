package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendDeleteWithFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendGetWithFilter;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_RESPONSE_LIST_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import io.restassured.filter.Filter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ReservationControllerTest extends ControllerTest {

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

            sendGetWithFilter("/reservations", spec, filter)
                    .then().log().all()
                    .statusCode(200);
        }

        @Test
        @DisplayName("예약 삭제 API")
        void deleteReservation() {
            Filter filter = createDocumentFilter(docsBaseDir(), "delete",
                    pathParameters(parameterWithName("id").description("예약 ID"))
            );

            sendDeleteWithFilter("/reservations/{id}", spec, filter, 1)
                    .then().log().all()
                    .statusCode(204);
        }
    }
}
