package finalmission.controller;

import static finalmission.helper.AuthTokenExtractor.extractMemberLoginToken;
import static finalmission.helper.DocsFilterFactory.createDocumentFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendGetWithTokenAndFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendPostWithFilter;
import static finalmission.helper.RestDocsFieldSnippets.Auth.MEMBER_LOGIN_CHECK_RESPONSE_FIELDS;
import static finalmission.helper.RestDocsFieldSnippets.Auth.MEMBER_LOGIN_REQUEST_FIELDS;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import finalmission.dto.MemberLoginRequest;
import io.restassured.filter.Filter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

public class AuthControllerTest extends ControllerTest {

    private static final String DOCS_BASE_DIR = "auth";

    @DisplayName("인증 API")
    @Nested
    class AuthApi {

        @DisplayName("멤버 로그인 API")
        @Test
        void memberLogin() {
            MemberLoginRequest request = new MemberLoginRequest("member1@email.com", "password");

            Filter filter = createDocumentFilter(DOCS_BASE_DIR, "memberLogin",
                    requestFields(MEMBER_LOGIN_REQUEST_FIELDS)
            );

            sendPostWithFilter("/auth/login", request, spec, filter)
                    .then().statusCode(200)
                    .header(HttpHeaders.SET_COOKIE, Matchers.containsString("token="));
        }

        @DisplayName("멤버 로그인 확인 API")
        @Test
        void memberLoginCheck() {
            String token = extractMemberLoginToken();

            Filter filter = createDocumentFilter(DOCS_BASE_DIR, "memberLoginCheck",
                    responseFields(MEMBER_LOGIN_CHECK_RESPONSE_FIELDS)
            );

            sendGetWithTokenAndFilter("/auth/login/check", spec, token, filter)
                    .then().statusCode(200);
        }
    }
}
