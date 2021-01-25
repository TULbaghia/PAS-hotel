package pl.lodz.p.pas.service.apartment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FiveStarTests {
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
    public void addFiveStarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(1111, 9898);
        JSONObject jsonObj = new JSONObject()
                .put("howManyBeds", 3)
                .put("doorNumber", randomNum)
                .put("basePricePerDay", 100.00)
                .put("bonus","DywanTest")
                .put("pcName", "PCTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("fivestar")
                .then()
                .assertThat()
                .body("howManyBeds", equalTo(jsonObj.getInt("howManyBeds")))
                .body("doorNumber", equalTo(jsonObj.getInt("doorNumber")))
                .body("basePricePerDay", equalTo(jsonObj.getFloat("basePricePerDay")))
                .body("bonus", containsString(jsonObj.getString("bonus")))
                .body("pcName", containsString(jsonObj.getString("pcName")))
                .statusCode(200);
    }

    @Test
    public void getFiveStarTest() {
        JSONObject fiveStarTest = addTestApartment();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("fivestar/" + fiveStarTest.getString("id"))
                .then()
                .assertThat()
                .body("howManyBeds", equalTo(fiveStarTest.getInt("howManyBeds")))
                .body("doorNumber", equalTo(fiveStarTest.getInt("doorNumber")))
                .body("basePricePerDay", equalTo(fiveStarTest.getFloat("basePricePerDay")))
                .body("bonus", containsString(fiveStarTest.getString("bonus")))
                .body("pcName", containsString(fiveStarTest.getString("pcName")))
                .statusCode(200);
    }

    @Test
    public void getAllFiveStarTest() {
        JSONArray fiveStarArray = new JSONArray(
                given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("fivestar")
                .then()
                .extract()
                .body()
                .asString()
        );

        for (int i = 1; i <= 4; i++) {
            Assert.assertEquals(fiveStarArray.getJSONObject(i - 1).get("pcName"), "pcAp" + i);
        }

        int fiveStarNumber = fiveStarArray.length();

        addTestApartment();
        addTestApartment();

        JSONArray withNewFiveStar = new JSONArray(
                given().contentType(ContentType.JSON)
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .get("fivestar")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );

        Assert.assertEquals(withNewFiveStar.length(), fiveStarNumber + 2);

        for (int i = fiveStarNumber - 1; i < withNewFiveStar.length(); i++) {
            Assert.assertEquals(withNewFiveStar.getJSONObject(i).get("pcName"), "PCTest");
        }
    }

    @Test
    public void updateFiveStarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(1111, 9898);
        JSONObject testFiveStar = addTestApartment();
        JSONObject jsonObj = new JSONObject()
                .put("id", testFiveStar.getString("id"))
                .put("howManyBeds", 1)
                .put("doorNumber", randomNum)
                .put("basePricePerDay", 999)
                .put("bonus","UpdateTest")
                .put("pcName", "UpdateTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("fivestar")
                .then()
                .assertThat()
                .body("howManyBeds", equalTo(jsonObj.getInt("howManyBeds")))
                .body("doorNumber", equalTo(jsonObj.getInt("doorNumber")))
                .body("basePricePerDay", equalTo(jsonObj.getFloat("basePricePerDay")))
                .body("bonus", containsString(jsonObj.getString("bonus")))
                .body("pcName", containsString(jsonObj.getString("pcName")))
                .statusCode(200);
    }

    @Test
    public void deleteFiveStarTest() {
        JSONObject testFiveStar = addTestApartment();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .delete("fivestar/" + testFiveStar.getString("id"))
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    public JSONObject addTestApartment() {
        int randomNum = ThreadLocalRandom.current().nextInt(1111, 9898);
        JSONObject jsonObj = new JSONObject()
                .put("howManyBeds", 3)
                .put("doorNumber", randomNum)
                .put("basePricePerDay", 100)
                .put("bonus","DywanTest")
                .put("pcName", "PCTest");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("fivestar")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}