package finalmission.helper;

import finalmission.dto.MemberLoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthTokenExtractor {

    public static String extractMemberLoginToken() {
        return extractToken(new MemberLoginRequest("member1@email.com", "password")
        );
    }

    private static String extractToken(MemberLoginRequest memberLoginRequest) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberLoginRequest)
                .when().post("/auth/login")
                .then().statusCode(200)
                .extract();

        String token = response.cookie("token");
        if (token == null) {
            throw new IllegalStateException("로그인 응답에 토큰이 없습니다.");
        }
        return token;
    }
}
