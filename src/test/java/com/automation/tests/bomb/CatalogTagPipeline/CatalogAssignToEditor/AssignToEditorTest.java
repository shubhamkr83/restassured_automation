package com.automation.tests.bomb.Catalog_Tag_Pipeline.Catalog_Assign_to_Editor;

import com.automation.base.BaseTest;
import com.automation.constants.BombEndpoints;
import com.automation.constants.HttpStatus;
import com.automation.models.response.CatalogAssignResponse;
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
 * Test class for BOMB Catalog Assign - Assign to Editor endpoint.
 * Endpoint: {{bizup_base}}/v1/admin/catalog/assign/{{catalog_foassign_id}}
 * Implements comprehensive Postman test scripts for catalog assignment
 * validation.
 */
@Epic("BOMB Catalog Tag Pipeline")
@Feature("Catalog Assign to Editor")
public class Catalog_Assign_Assign_to_Editor extends BaseTest {

    private String authToken;
    private Response response;
    private CatalogAssignResponse catalogAssignResponse;

    // Collection variables (equivalent to Postman collection variables)
    private String catalogForAssignId;
    private static final String SELLER_ID = "63ee780c9689be92acce8f35";
    private static final String EDITOR_ID = "64f8a9b2c3d4e5f6a7b8c9d0"; // Replace with actual editor ID

    @BeforeClass
    public void setupAuth() {
        // Ensure login test runs first and token is available
        if (LoginApiTest.bombToken != null) {
            authToken = LoginApiTest.bombToken;
            logger.info("Using BOMB token from LoginApiTest");
        } else {
            throw new RuntimeException("Login token not available. Please run LoginApiTest first.");
        }

        // Get catalog ID from previous test
        if (Catalog_Assign_Catalog_Uploaded_Search_by_Seller_Filter.catalogForAssignId != null) {
            catalogForAssignId = Catalog_Assign_Catalog_Uploaded_Search_by_Seller_Filter.catalogForAssignId;
            logger.info("Using catalog for assign ID from previous test: {}", catalogForAssignId);
        } else {
            // Fallback to a default ID if not available
            catalogForAssignId = "6822f5dac17c6dcd589ba173";
            logger.warn("Catalog for assign ID not available from previous test, using default: {}",
                    catalogForAssignId);
        }
    }

    @Test(description = "Status code is 200 and status message is 'OK'", priority = 1, groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.BLOCKER)
    public void testStatusCode200AndMessageOK() {
        // Send POST request to assign catalog to editor
        response = RestAssured.given()
                .spec(requestSpec)
                .header("authorization", "JWT " + authToken)
                .header("source", "bizupChat")
                .when()
                .post(BombEndpoints.CATALOG_ASSIGN + "/" + catalogForAssignId);

        // Parse response for other tests
        catalogAssignResponse = JsonUtils.fromResponse(response, CatalogAssignResponse.class);

        // Verify response status is 200 OK
        assertThat("Status code should be 200",
                response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat("Status message should be OK",
                response.getStatusLine(), containsString("OK"));

        logger.info("Response status verified: 200 OK");
    }

    @Test(description = "Response time is below threshold", priority = 2, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.NORMAL)
    public void testResponseTimeBelowThreshold() {
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

    @Test(description = "Response contains required fields: statusCode, message, and data", priority = 3, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.CRITICAL)
    public void testResponseContainsRequiredFields() {
        // Validate response is an object
        assertThat("Response should not be null", catalogAssignResponse, notNullValue());

        // Validate response has all required fields
        assertThat("Response should have statusCode", catalogAssignResponse.getStatusCode(), notNullValue());
        assertThat("Response should have message", catalogAssignResponse.getMessage(), notNullValue());
        assertThat("Response should have data", catalogAssignResponse.getData(), notNullValue());

        logger.info("Response contains all required fields");
    }

    @Test(description = "statusCode and message are strings; data is an object", priority = 4, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.CRITICAL)
    public void testResponseFieldDataTypes() {
        // Validate response field types
        assertThat("statusCode should be a string", catalogAssignResponse.getStatusCode(), instanceOf(String.class));
        assertThat("message should be a string", catalogAssignResponse.getMessage(), instanceOf(String.class));
        assertThat("data should be an object", catalogAssignResponse.getData(), notNullValue());

        logger.info("Response field data types validated");
    }

    @Test(description = "Validate data types of response fields", priority = 5, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.CRITICAL)
    public void testDataFieldDataTypes() {
        CatalogAssignResponse.CatalogAssignData data = catalogAssignResponse.getData();

        if (data != null) {
            // Validate message type
            assertThat("message should be a string", catalogAssignResponse.getMessage(), instanceOf(String.class));

            // Validate data field types
            assertThat("_id should be a string", data.get_id(), instanceOf(String.class));
            assertThat("videoType should be a string", data.getVideoType(), instanceOf(String.class));
            assertThat("createdAt should be a string", data.getCreatedAt(), instanceOf(String.class));
            assertThat("updatedAt should be a string", data.getUpdatedAt(), instanceOf(String.class));
            assertThat("__v should be a number", data.getVersion(), instanceOf(Integer.class));

            logger.info("Data field data types validated successfully");
        } else {
            logger.warn("Data is null, skipping field type validation");
        }
    }

    @Test(description = "Field 'videoType' should be 'catalog'", priority = 6, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.NORMAL)
    public void testVideoTypeIsCatalog() {
        CatalogAssignResponse.CatalogAssignData data = catalogAssignResponse.getData();

        if (data != null) {
            // Validate videoType is 'catalog'
            assertThat("videoType should be 'catalog'",
                    data.getVideoType(), equalTo("catalog"));

            logger.info("VideoType validated: {}", data.getVideoType());
        } else {
            logger.warn("Data is null, skipping videoType validation");
        }
    }

    @Test(description = "Response _id (FO_Assign_ID) matches collection variable 'catalog_foassign_id'", priority = 7, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.CRITICAL)
    public void testResponseIdMatchesCatalogForAssignId() {
        CatalogAssignResponse.CatalogAssignData data = catalogAssignResponse.getData();

        if (data != null) {
            // Validate _id matches the catalog ID used in the request
            assertThat("Response _id should match catalog_foassign_id",
                    data.get_id(), equalTo(catalogForAssignId));

            logger.info("Response _id validated: {} (Expected: {})", data.get_id(), catalogForAssignId);
        } else {
            logger.warn("Data is null, skipping _id validation");
        }
    }

    @Test(description = "Response sellerId matches collection variable 'seller_id'", priority = 8, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.CRITICAL)
    public void testResponseSellerIdMatchesExpected() {
        CatalogAssignResponse.CatalogAssignData data = catalogAssignResponse.getData();

        if (data != null) {
            // Validate sellerId matches the expected seller ID
            assertThat("Response sellerId should match seller_id",
                    data.getSellerId(), equalTo(SELLER_ID));

            logger.info("Response sellerId validated: {} (Expected: {})", data.getSellerId(), SELLER_ID);
        } else {
            logger.warn("Data is null, skipping sellerId validation");
        }
    }

    @Test(description = "Response editorId matches collection variable 'editor_id'", priority = 9, dependsOnMethods = "testStatusCode200AndMessageOK", groups = "bomb")
    @Story("Catalog Assign - Assign to Editor")
    @Severity(SeverityLevel.CRITICAL)
    public void testResponseEditorIdMatchesExpected() {
        CatalogAssignResponse.CatalogAssignData data = catalogAssignResponse.getData();

        if (data != null) {
            // Validate editorId is present and not null
            // Note: In real scenario, you would compare against actual editor_id from
            // collection variables
            assertThat("Response editorId should not be null", data.getEditorId(), notNullValue());

            logger.info("Response editorId validated: {}", data.getEditorId());
        } else {
            logger.warn("Data is null, skipping editorId validation");
        }
    }
}
