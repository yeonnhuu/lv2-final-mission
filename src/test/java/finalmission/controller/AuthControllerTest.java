package finalmission.controller;

import static finalmission.helper.RestAssuredRequestUtils.sendGetRequestWithToken;
import static finalmission.helper.RestAssuredRequestUtils.sendPostRequest;
import static finalmission.helper.RestDocsFieldSnippets.Auth.MEMBER_LOGIN_CHECK_RESPONSE_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Auth.MEMBER_LOGIN_REQUEST_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import finalmission.dto.request.MemberLoginRequest;
import io.restassured.filter.Filter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class AuthControllerTest extends ControllerTest {

    @Override
    protected String docsBaseDir() {
        return "auth";
    }

    @Nested
    @DisplayName("인증 API")
    class AuthApi {

        @Test
        @DisplayName("멤버 로그인 API")
        void memberLogin() {
            MemberLoginRequest request = new MemberLoginRequest("member1@email.com", "password");

            Filter filter = createDocumentFilter(docsBaseDir(), "member-login",
                    requestFields(MEMBER_LOGIN_REQUEST_FIELDS)
            );

            sendPostRequest("/auth/login", request, spec, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .header(HttpHeaders.SET_COOKIE, Matchers.containsString("token="));
        }

        @Test
        @DisplayName("멤버 로그인 확인 API")
        void memberLoginCheck() {
            String token = extractTestMemberLoginToken();

            Filter filter = createDocumentFilter(docsBaseDir(), "member-login-check",
                    responseFields(MEMBER_LOGIN_CHECK_RESPONSE_FIELDS)
            );

            sendGetRequestWithToken("/auth/login/check", spec, token, filter)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
