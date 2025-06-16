package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendDeleteWithFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendGetWithFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendGetWithTokenAndFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendPostWithTokenAndFilter;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_CREATE_REQUEST_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_DELETE_PATH_PARAMETERS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_MINE_RESPONSE_LIST_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_RESPONSE_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_RESPONSE_LIST_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import finalmission.dto.request.ReservationCreateRequest;
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
        @DisplayName("예약 생성 API")
        void createReservation() {
            String token = extractTestMemberLoginToken();
            ReservationCreateRequest request = new ReservationCreateRequest(1L, 1);

            Filter filter = createDocumentFilter(docsBaseDir(), "create",
                    requestFields(RESERVATION_CREATE_REQUEST_FIELDS),
                    responseFields(RESERVATION_RESPONSE_FIELDS)
            );

            sendPostWithTokenAndFilter("/reservations", request, spec, token, filter)
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservation");
        }

        @Test
        @DisplayName("예약 삭제 API")
        void deleteReservation() {
            Filter filter = createDocumentFilter(docsBaseDir(), "delete",
                    pathParameters(RESERVATION_DELETE_PATH_PARAMETERS)
            );

            sendDeleteWithFilter("/reservations/{id}", spec, filter, 1)
                    .then().log().all()
                    .statusCode(204);
        }

        @Test
        @DisplayName("나의 예약 목록 조회 API")
        void findMyReservations() {
            String token = extractTestMemberLoginToken();

            Filter filter = createDocumentFilter(docsBaseDir(), "find-all-mine",
                    responseFields(RESERVATION_MINE_RESPONSE_LIST_FIELDS)
            );

            sendGetWithTokenAndFilter("/reservations/me", spec, token, filter)
                    .then().log().all()
                    .statusCode(200);
        }
    }
}
