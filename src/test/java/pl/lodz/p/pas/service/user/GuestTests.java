package pl.lodz.p.pas.service.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GuestTests {

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
    public void addGuestTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","loginTest" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("guest")
                .then()
                .assertThat()
                .body("login", containsString("loginTest" + randomNum))
                .body("firstname", containsString("firstnameTest"))
                .body("lastname", containsString("lastnameTest"))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void getAllGuestTest() {
        JSONObject testGuest1 = addTestGuest();
        JSONObject testGuest2 = addTestGuest();
        JSONObject testGuest3 = addTestGuest();

        JSONArray guestsArray = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("guest")
                .then()
                .extract()
                .body().asString());

        int lastIndex = guestsArray.length() - 1;
        Assert.assertEquals(guestsArray.getJSONObject(lastIndex).getString("id"), testGuest3.getString("id"));
        Assert.assertEquals(guestsArray.getJSONObject(lastIndex - 1).getString("id"), testGuest2.getString("id"));
        Assert.assertEquals(guestsArray.getJSONObject(lastIndex - 2).getString("id"), testGuest1.getString("id"));
    }

    @Test
    public void getGuestTest() {
        JSONObject testUser = addTestGuest();
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("guest/" + testUser.getString("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void updateGuestTest() {
        JSONObject testUser = addTestGuest();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", testUser.get("login"))
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("guest")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString("UpdatedFirstname"))
                .body("lastname", containsString("UpdatedLastname"))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void activateGuestTest() {
        JSONObject testUser = addTestGuest();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("active", false);

        RequestSpecification rs = RestAssured.given();

        rs.contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("guest")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(false))
                .statusCode(200);
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
