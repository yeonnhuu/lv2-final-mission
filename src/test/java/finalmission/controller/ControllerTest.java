package finalmission.controller;


import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import finalmission.dto.request.MemberLoginRequest;
import finalmission.helper.AuthExtractor;
import finalmission.helper.DatabaseCleaner;
import finalmission.helper.FixtureLoader;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.restdocs.snippet.Snippet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ControllerTest {

    protected static final String TEST_EMAIL = "member1@email.com";
    protected static final String TEST_PASSWORD = "password";

    @LocalServerPort
    protected int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private FixtureLoader fixtureLoader;

    protected RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        RestAssured.port = port;

        this.spec = RestAssured.given()
                .filter(RestAssuredRestDocumentation.documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()));

        databaseCleaner.execute();
        fixtureLoader.insertFixtures();
    }

    protected abstract String docsBaseDir();

    protected Filter createDocumentFilter(String identifier, String operationName, Snippet... snippets) {
        return document(docsBaseDir() + "/" + operationName, snippets);
    }

    protected String extractTestMemberLoginToken() {
        return AuthExtractor.extractMemberLoginToken(new MemberLoginRequest(TEST_EMAIL, TEST_PASSWORD));
    }
}
