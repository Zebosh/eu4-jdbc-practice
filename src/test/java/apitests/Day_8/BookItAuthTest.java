package apitests.Day_8;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class BookItAuthTest {

    @BeforeClass
    public void beforeclass(){
        baseURI= "https://cybertek-reservation-api-qa2.herokuapp.com";

    }
String accessToken="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMzAiLCJhdWQiOiJzdHVkZW50LXRlYW0tbGVhZGVyIn0.3YSCwcTXRcEygBm7LvBLb6_D8jU5WXjAD6E3VA9oh0o";
    @Test
    public void getAllCampuses(){

        Response response=given().header("Authorization",accessToken)
                .when().when().get("/api/campuses");

        response.prettyPrint();
        System.out.println(response.statusCode());

    }
}
