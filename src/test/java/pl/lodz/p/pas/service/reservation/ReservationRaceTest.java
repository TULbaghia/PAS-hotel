package pl.lodz.p.pas.service.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReservationRaceTest {
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
    public void apartmentAlreadyRentedTest() {
        JSONObject testGuest1 = addTestGuest();
        JSONObject testGuest2 = addTestGuest();
        JSONObject testApartment = addTestApartment();

        String testGuest1Token = login(testGuest1.getString("login"), "zaq1@WSX");
        String testGuest2Token = login(testGuest2.getString("login"), "zaq1@WSX");

        JSONObject jsonObj1 = new JSONObject()
                .put("guest", new JSONObject().put("id", testGuest1.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")));

        JSONObject jsonObj2 = new JSONObject()
                .put("guest", new JSONObject().put("id", testGuest2.getString("id")))
                .put("apartment", new JSONObject().put("id", testApartment.getString("id")));


        var json1 = given().contentType(ContentType.JSON)
                .body(jsonObj1.toString())
                .header(new Header("Authorization", "Bearer " + testGuest1Token))
                .post("reservation")
                .then()
                .assertThat();

        var json2 = given().contentType(ContentType.JSON)
                .body(jsonObj2.toString())
                .header(new Header("Authorization", "Bearer " + testGuest2Token))
                .post("reservation")
                .then()
                .assertThat();

        if(json1.extract().statusCode() == 200) {
            json1.body("guest.id", containsString(testGuest1.getString("id")))
                .body("apartment.id", containsString(testApartment.getString("id")))
                .body("reservationStartDate", notNullValue())
                .statusCode(200);

            json2.body("errorMessage[0].item", containsString("addReservation"))
                .body("errorMessage[0].message", containsString("apartmentAlreadyRented"))
                .statusCode(400);
        } else if (json2.extract().statusCode() == 200) {
            json2.body("guest.id", containsString(testGuest1.getString("id")))
                    .body("apartment.id", containsString(testApartment.getString("id")))
                    .body("reservationStartDate", notNullValue())
                    .statusCode(200);

            json1.body("errorMessage[0].item", containsString("addReservation"))
                    .body("errorMessage[0].message", containsString("apartmentAlreadyRented"))
                    .statusCode(400);
        } else {
            throw new RuntimeException("Incorrect status code for reservation add request");
        }
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

    public JSONObject addTestGuest() {
        int randomNum = ThreadLocalRandom.current().nextInt(50, 192392);
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
