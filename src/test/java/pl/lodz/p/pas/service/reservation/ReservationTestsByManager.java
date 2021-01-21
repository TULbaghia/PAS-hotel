package pl.lodz.p.pas.service.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
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
        String date = dmf.print(DateTime.now());

        JSONObject jsonObj = new JSONObject()
                .put("guest", new JSONObject().put("id", testGuest.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")))
                .put("reservationStartDate", date);

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

//    @Test
//    public void getReservationTest() {
//
//    }
//
//    @Test
//    public void getAllReservationTest() {
//
//    }
//
//    @Test
//    public void updateReservationTest() {
//
//    }
//
//    @Test
//    public void endReservationTest() {
//
//    }

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
}
