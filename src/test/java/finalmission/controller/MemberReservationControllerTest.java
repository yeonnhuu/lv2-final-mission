package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendDeleteRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendGetRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendPostRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendPutRequestWithToken;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_CREATE_REQUEST_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_DELETE_PATH_PARAMETERS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_MINE_RESPONSE_LIST_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_RESPONSE_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_UPDATE_PATH_PARAMETERS;
import static finalmission.helper.RestDocsFieldSnippets.Reservation.RESERVATION_UPDATE_REQUEST_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import finalmission.dto.request.ReservationCreateRequest;
import finalmission.dto.request.ReservationUpdateRequest;
import io.restassured.filter.Filter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberReservationControllerTest extends ControllerTest {

    @Override
    protected String docsBaseDir() {
        return "member-reservation";
    }

    @Nested
    @DisplayName("멤버 예약 API")
    class MemberReservationApi {

        @Test
        @DisplayName("예약 목록 조회 API")
        void findMyReservations() {
            String token = extractTestMemberLoginToken();

            Filter filter = createDocumentFilter(docsBaseDir(), "find-all",
                    responseFields(RESERVATION_MINE_RESPONSE_LIST_FIELDS)
            );

            sendGetRequestWithToken("/reservations/me", spec, token, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
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

            sendPostRequestWithToken("/reservations", request, spec, token, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        @DisplayName("예약 수정 API")
        void updateReservation() {
            String token = extractTestMemberLoginToken();
            ReservationUpdateRequest request = new ReservationUpdateRequest(3);

            Filter filter = createDocumentFilter(docsBaseDir(), "update",
                    pathParameters(RESERVATION_UPDATE_PATH_PARAMETERS),
                    requestFields(RESERVATION_UPDATE_REQUEST_FIELDS),
                    responseFields(RESERVATION_RESPONSE_FIELDS)
            );

            sendPutRequestWithToken("/reservations/{id}", request, spec, token, filter, 1)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        @DisplayName("예약 삭제 API")
        void deleteReservation() {
            String token = extractTestMemberLoginToken();

            Filter filter = createDocumentFilter(docsBaseDir(), "delete",
                    pathParameters(RESERVATION_DELETE_PATH_PARAMETERS)
            );

            sendDeleteRequestWithToken("/reservations/{id}", spec, token, filter, 1)
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }
}

