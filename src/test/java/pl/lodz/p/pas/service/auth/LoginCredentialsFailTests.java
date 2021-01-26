package pl.lodz.p.pas.service.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginCredentialsFailTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/pas-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void wrongCredentialsTest() {
        JSONObject jsonObj = new JSONObject()
                .put("login", "TestGuest")
                .put("password", "zlehaslo");

        String res = given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("auth/login")
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .body()
                .asString();

        Assert.assertEquals(res, "\"UNAUTHORIZED\"");
    }
}
