package Homework_1_2.StarWarsApi_Practice;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PostRequests {

    @BeforeClass
    public void beforeclass(){
        baseURI= "https://reqres.in/api/";

    }

    @Test
    public void PostFirstWay(){
         String jsonBody="{\n" +
                 "    \"name\": \"polly\",\n" +
                 "    \"job\": \"teacher\"\n" +
                 "}";

        Response login = given().log().all().accept(ContentType.JSON)
                .and().contentType("application/json")
                .body(jsonBody)
                .when().post("users");

        assertEquals(login.statusCode(),201);
        assertEquals(login.contentType(),"application/json; charset=utf-8");

    }
}
