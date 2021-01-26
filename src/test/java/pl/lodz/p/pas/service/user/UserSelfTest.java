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

public class UserSelfTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/pas-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();
    }

    public String login(String username, String password) {
        JSONObject jsonObj = new JSONObject()
                .put("login", username)
                .put("password", password);

        Response r = given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("auth/login")
                .then()
                .statusCode(202)
                .extract()
                .response();

        return r.getBody().asString();
    }

    @Test
    public void selfTest() {
        JSONObject testGuest = addTestGuest();
        String testGuestToken = login(testGuest.getString("login"), "zaq1@WSX");

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + testGuestToken))
                .get("self")
                .then()
                .assertThat()
                .body("firstname", containsString(testGuest.getString("firstname")))
                .body("active", equalTo(testGuest.getBoolean("active")))
                .body("id", containsString(testGuest.getString("id")))
                .body("lastname", containsString(testGuest.getString("lastname")))
                .body("group", containsString("Guest"))
                .statusCode(200);
    }

    public JSONObject addTestGuest() {
        String managerJwtToken = login("TestManager", "zaq1@WSX");
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","TestCaseUser" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","TestCaseUser")
                .put("lastname","TestCaseUser");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + managerJwtToken))
                        .post("guest")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}
