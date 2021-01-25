package pl.lodz.p.pas.service.reservation;

import com.nimbusds.jwt.JWT;
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
import static org.hamcrest.Matchers.containsString;

public class ReservationTestsByGuest {
    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/pas-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();

        JWT_TOKEN = login("TestManager", "zaq1@WSX");
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
    public void addReservationTest() {
        JSONObject testGuest1 = addTestGuest();
        JSONObject testApartment = addTestApartment();

        String testGuest1Token = login(testGuest1.getString("login"), "zaq1@WSX");

        DateTimeFormatter dmf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = DateTime.now();
        String updatedDate = dmf.print(DateTime.now());

        JSONObject jsonObj = new JSONObject()
                .put("guest", new JSONObject().put("id", testGuest1.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")))
                .put("reservationStartDate", updatedDate);

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + testGuest1Token))
                .post("reservation")
                .then()
                .assertThat()
                .body("guest.id", containsString(testGuest1.getString("id")))
                .body("apartment.id", containsString(testApartment.getString("id")))
                .body("reservationStartDate", containsString((dmf.print(dt.minusHours(1)))))
                .statusCode(200);
    }

    @Test
    public void getReservationTest() {
        JSONObject testGuest1 = addTestGuest();
        JSONObject testApartment = addTestApartment();

        String testGuest1Token = login(testGuest1.getString("login"), "zaq1@WSX");

        JSONObject testReservation = addTestReservation(testGuest1.getString("id"), testApartment.getString("id"), testGuest1Token);

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
        JSONObject testGuest1 = addTestGuest();
        JSONObject testApartment1 = addTestApartment();
        JSONObject testApartment2 = addTestApartment();

        String testGuest1Token = login(testGuest1.getString("login"), "zaq1@WSX");

        JSONObject testReservation1 = addTestReservation(testGuest1.getString("id"), testApartment1.getString("id"), testGuest1Token);
        JSONObject testReservation2 = addTestReservation(testGuest1.getString("id"), testApartment2.getString("id"), testGuest1Token);

        JSONArray guestsReservations = new JSONArray(
                given().contentType(ContentType.JSON)
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .get("reservation")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );

        Assert.assertEquals(guestsReservations.length(), 2);

        for (int i = 1; i <= 2; i++) {
            Assert.assertEquals(guestsReservations.getJSONObject(i - 1).getJSONObject("guest").getString("login"), testGuest1.getString("login"));
        }
    }

    public JSONObject addTestGuest() {
        int randomNum = ThreadLocalRandom.current().nextInt(50, 1337);
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
        int randomNum = ThreadLocalRandom.current().nextInt(1111, 9898);
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

    public JSONObject addTestReservation(String guestId, String apartmentId, String jwtToken) {
        JSONObject testGuest = addTestGuest();
        JSONObject testApartment = addTestApartment();

        JSONObject jsonObj = new JSONObject()
                .put("guest", new JSONObject().put("id", guestId))
                .put("apartment", new JSONObject().put("id", apartmentId));

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + jwtToken))
                        .post("reservation")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}
