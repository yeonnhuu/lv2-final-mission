package finalmission.helper;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredRequestUtils {

    // === 필터 포함 요청 ===
    public static Response sendGetRequest(String uri, RequestSpecification spec, Filter filter) {
        return RestAssured.given(spec).log().all()
                .filters(filter)
                .when().get(uri);
    }

    public static Response sendPostRequest(String uri, Object body, RequestSpecification spec, Filter filter) {
        return RestAssured.given(spec).log().all()
                .filters(filter)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(uri);
    }

    // === 필터 + 토큰 포함 요청 ===
    public static Response sendGetRequestWithToken(String uri, RequestSpecification spec, String token, Filter filter) {
        return RestAssured.given(spec).log().all()
                .filters(filter)
                .cookie("token", token)
                .when().get(uri);
    }

    public static Response sendPostRequestWithToken(String uri, Object body, RequestSpecification spec, String token, Filter filter) {
        return RestAssured.given(spec).log().all()
                .filters(filter)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)
                .when().post(uri);
    }

    public static Response sendPutRequestWithToken(String uri, Object body, RequestSpecification spec, String token, Filter filter, Object... pathParams) {
        return RestAssured.given(spec).log().all()
                .filters(filter)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)
                .when().put(uri, pathParams);
    }

    public static Response sendDeleteRequestWithToken(String uri, RequestSpecification spec, String token, Filter filter, Object... pathParams) {
        return RestAssured.given(spec).log().all()
                .cookie("token", token)
                .filters(filter)
                .when().delete(uri, pathParams);
    }
}
