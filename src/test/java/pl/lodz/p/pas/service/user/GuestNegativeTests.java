package pl.lodz.p.pas.service.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class GuestNegativeTests {
    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/pas-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject jsonObj = new JSONObject()
                .put("login","TestManager")
                .put("password","zaq1@WSX");

        Response r = given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("auth/login")
                .then()
                .statusCode(202)
                .extract()
                .response();

        JWT_TOKEN = r.getBody().asString();
    }

    @Test
    public void GuestPasswordIncorrectTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(50, 1337);
        JSONObject jsonObj = new JSONObject()
                .put("login","loginTest" + randomNum)
                .put("password","gmd")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("guest")
                .then()
                .assertThat()
                .body("errorMessage[0].message", containsString("password_incorrect"))
                .statusCode(400);
    }
}