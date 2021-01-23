package pl.lodz.p.pas.service.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class HttpFailureTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/pas-1.0-SNAPSHOT/api/";
        RestAssured.useRelaxedHTTPSValidation();
    }

//    @Test
//    public void loginWithHttp() {
//        JSONObject jsonObj = new JSONObject()
//                .put("login", "TestGuest")
//                .put("password", "zaq1@WSX");
//
//        given().contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .post("auth/login")
//                .then()
//                .assertThat()
//                .body("errorMessage[0].message", containsString("HTTP 405 Method Not Allowed"))
//                .statusCode(400);
//    }
}
