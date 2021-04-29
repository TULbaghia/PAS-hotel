package pl.lodz.p.pas.service.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class UserNotActiveLoginTest {
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
    public void userNotActiveTest() {
        JSONObject testGuest = addTestGuest();

        Assert.assertTrue(testGuest.getBoolean("active"));

        JSONObject jsonObj = new JSONObject()
                .put("id", testGuest.get("id"))
                .put("active", false);

        RequestSpecification rs = RestAssured.given();

        rs.contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("guest")
                .then()
                .assertThat()
                .body("login", containsString(testGuest.getString("login")))
                .body("firstname", containsString(testGuest.getString("firstname")))
                .body("lastname", containsString(testGuest.getString("lastname")))
                .body("active", equalTo(false))
                .statusCode(200);

        jsonObj = new JSONObject()
                .put("login", testGuest.getString("login"))
                .put("password", "zaq1@WSX");

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


    public JSONObject addTestGuest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","TestCaseUser" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","TestCaseUser")
                .put("lastname","TestCaseUser");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("guest")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}
