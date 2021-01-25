package pl.lodz.p.pas.service.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class NoJwtTokenFailTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/pas-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void noJwtTokenFailTest() {
        String res = given().contentType(ContentType.JSON)
                .get("self")
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .body()
                .asString();

        Assert.assertEquals(res, "\"UNAUTHORIZED\"");
    }
}
