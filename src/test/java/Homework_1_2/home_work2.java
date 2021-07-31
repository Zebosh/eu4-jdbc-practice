package Homework_1_2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class home_work2 {
/*
Q1:
Given accept type is json
And path param id is 20
When user sends a get request to "/spartans/{id}"
Then status code is 200
And content-type is "application/json;char"
And response header contains Date
And Transfer-Encoding is chunked
And response payload values match the following:
id is 20,
name is "Lothario",
gender is "Male",
phone is 7551551687
 */
    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    @Test
    public void task1(){
//        Response response = given().accept(ContentType.JSON)
//                .and().pathParam("id", 20)
//                .when().get("api/spartans/{id}");
//
//        assertEquals(response.statusCode(),200);
//        assertEquals(response.contentType(),"application/json");
//
//        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));
//
//        String header = response.getHeader("Transfer-Encoding");
//        assertEquals(header,"chunked");


        given().accept(ContentType.JSON)
                .and().pathParam("id", 20)
                .when().get("api/spartans/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().header("Date", notNullValue())
                .and().header("Transfer-Encoding","chunked")
                .and().assertThat().body("id",equalTo(20),
                "name",equalTo("Lothario"), "gender", equalTo("Male"),
                "phone", equalTo(7551551687l));

    }
    /*
    Given accept type is json
And query param gender = Female
And queary param nameContains = r
When user sends a get request to "/spartans/search"
Then status code is 200
And content-type is "application/json;char"
And all genders are Female
And all names contains r
And size is 20
And totalPages is 1
And sorted is false
     */

    @Test
    public void task2(){
        given().contentType(ContentType.JSON)
                .when().queryParam("gender", "Female")
                .and().queryParam("nameContains","r")
                .and().get("api/spartans/search")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().assertThat().body("content.gender",hasItem("Female"))
                .and().assertThat().body("content.name",everyItem(containsStringIgnoringCase("r")))
                .and().body("totalElements",equalTo(20),
                "totalPages",equalTo(1),
                "sort.sorted", equalTo(false));

    }
}
