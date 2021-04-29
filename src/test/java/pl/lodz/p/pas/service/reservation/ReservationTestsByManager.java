package pl.lodz.p.pas.service.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReservationTestsByManager {
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
    public void addReservationTest() {
        JSONObject testGuest = addTestGuest();
        JSONObject testApartment = addTestApartment();

        DateTimeFormatter dmf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String updatedDate = dmf.print(DateTime.now());

        JSONObject jsonObj = new JSONObject()
                .put("guest", new JSONObject().put("id", testGuest.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")))
                .put("reservationStartDate", updatedDate);


        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("reservation")
                .then()
                .assertThat()
                .body("guest.id", containsString(testGuest.getString("id")))
                .body("apartment.id", containsString(testApartment.getString("id")))
                .body("reservationStartDate", notNullValue())
                .statusCode(200);
    }

    @Test
    public void getReservationTest() {
        JSONObject testReservation = addTestReservation();
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("reservation/" + testReservation.getString("id"))
                .then()
                .assertThat()
                .body("guest.id", containsString(testReservation.getJSONObject("guest").getString("id")))
                .body("apartment.id", containsString(testReservation.getJSONObject("apartment").getString("id")))
                .body("reservationStartDate", containsString(testReservation.getString("reservationStartDate")))
                .statusCode(200);
    }

    @Test
    public void getAllReservationTest() {
        JSONObject testReservation1 = addTestReservation();
        JSONObject testReservation2 = addTestReservation();
        JSONObject testReservation3 = addTestReservation();

        JSONArray reservationArray = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("reservation")
                .then()
                .extract()
                .body()
                .asString());

        int lastIndex = reservationArray.length() - 1;
        Assert.assertEquals(reservationArray.getJSONObject(lastIndex).getString("id"), testReservation3.getString("id"));
        Assert.assertEquals(reservationArray.getJSONObject(lastIndex - 1).getString("id"), testReservation2.getString("id"));
        Assert.assertEquals(reservationArray.getJSONObject(lastIndex - 2).getString("id"), testReservation1.getString("id"));
    }

    @Test
    public void updateReservationTest() {
        JSONObject testReservation = addTestReservation();
        JSONObject testGuest = addTestGuest();
        JSONObject testApartment = addTestApartment();

        DateTimeFormatter dmf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime date = dmf.parseDateTime("1970-11-20 21:37:00");
        String updatedDate = dmf.print(date);

        JSONObject jsonObj = new JSONObject()
                .put("id", testReservation.getString("id"))
                .put("guest", new JSONObject().put("id", testGuest.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")))
                .put("reservationStartDate", updatedDate);

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("reservation")
                .then()
                .assertThat()
                .body("guest.id", containsString(testGuest.getString("id")))
                .body("apartment.id", containsString(testApartment.getString("id")))
                .body("reservationStartDate", notNullValue())
                .statusCode(200);
    }

    @Test
    public void deleteReservationTest() {
        JSONObject testReservation = addTestReservation();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .delete("reservation/" + testReservation.getString("id"))
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void endReservationTest() {
        JSONObject testReservation = addTestReservation();

        JSONObject jsonObj = new JSONObject()
                .put("id", testReservation.getString("id"));

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("reservation/end")
                .then()
                .assertThat()
                .body("reservationEndDate", notNullValue())
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

    public JSONObject addTestApartment() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("howManyBeds", 3)
                .put("doorNumber", randomNum)
                .put("basePricePerDay", 100)
                .put("bonus","DywanTest");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("threestar")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }

    public JSONObject addTestReservation() {
        JSONObject testGuest = addTestGuest();
        JSONObject testApartment = addTestApartment();

        JSONObject jsonObj = new JSONObject()
                .put("guest", new JSONObject().put("id", testGuest.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")));

        return new JSONObject(
            given().contentType(ContentType.JSON)
            .body(jsonObj.toString())
            .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
            .post("reservation")
            .then()
            .extract()
            .body()
            .asString()
        );

    }
}
