package com.automation.tests.bomb.Catalog_Tag_Pipeline.Catalog_Editor;

import com.automation.base.BaseTest;
import com.automation.constants.HttpStatus;
import com.automation.models.response.MarkAsDoneResponse;
import com.automation.tests.bomb.Login.LoginApiTest;
import com.automation.utils.JsonUtils;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test class for BOMB Catalog Editor - Mark As Done endpoint.
 * Endpoint: PUT
 * https://api.bizup.app/v1/admin/editor/assign/videos/done/{{seller_id}}/{{catalog_id}}
 * Implements comprehensive Postman test scripts for marking catalog as done
 * validation.
 */
@Epic("BOMB Catalog Tag Pipeline")
@Feature("Catalog Editor")
public class MarkAsDone extends BaseTest {

    private String authToken;
    private Response response;
    private MarkAsDoneResponse markAsDoneResponse;

    // Full endpoint URL (different base URL: api.bizup.app instead of
    // bomb.bizup.app)
    private static final String MARK_AS_DONE_URL = "https://api.bizup.app/v1/admin/editor/assign/videos/done";

    // IDs
    private static final String SELLER_ID = "63ee780c9689be92acce8f35";
    private static final String CATALOG_ID = "682584c0240b174c4c1a55f4"; // Hardcoded in Postman

    @BeforeClass
    public void setupAuth() {
        // Ensure login test runs first and token is available
        if (LoginApiTest.bombToken != null) {
            authToken = LoginApiTest.bombToken;
            logger.info("Using BOMB token from LoginApiTest");
        } else {
            throw new RuntimeException("Login token not available. Please run LoginApiTest first.");
        }
    }

    @Test(description = "Response status code is 200", priority = 1, groups = "bomb")
    @Story("Catalog Editor - Mark As Done")
    @Severity(SeverityLevel.BLOCKER)
    public void testStatusCode200() {
        // Send PUT request to mark catalog as done
        response = RestAssured.given()
                .header("authorization", "JWT " + authToken)
                .header("Content-Type", "application/json")
                .when()
                .put(MARK_AS_DONE_URL + "/" + SELLER_ID + "/" + CATALOG_ID);

        // Parse response for other tests
        markAsDoneResponse = JsonUtils.fromResponse(response, MarkAsDoneResponse.class);

        // Verify response status is 200 OK
        assertThat("Status code should be 200",
                response.getStatusCode(), equalTo(HttpStatus.OK));

        logger.info("Response status verified: 200 OK");
    }

    @Test(description = "Response time is less than threshold", priority = 2, dependsOnMethods = "testStatusCode200", groups = "bomb")
    @Story("Catalog Editor - Mark As Done")
    @Severity(SeverityLevel.NORMAL)
    public void testResponseTimeLessThanThreshold() {
        // Use specific threshold of 20000ms as per Postman script
        long threshold = 20000;
        long actualResponseTime = response.getTime();

        // Verify response time is available
        assertThat("Response time should be available", actualResponseTime, notNullValue());

        // Verify response time is below threshold
        assertThat("Response time should be less than threshold",
                actualResponseTime, lessThan(threshold));

        logger.info("Response time verified: {} ms (Threshold: {} ms)", actualResponseTime, threshold);
    }
}
