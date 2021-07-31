package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import  static io.restassured.RestAssured.*;

public class simpleGetRequest {

    String hrUrl="http://54.236.249.191:1000/ords/hr/regions";
    @Test
    public void test1(){
        Response response = RestAssured.get(hrUrl);
        int i = response.statusCode();
        System.out.println("i = " + i);

        //print the json body
        response.prettyPrint();

    }

    /*
    Given the accept type is json
    When the user sends get request to regions endpoint
    Then response status code must be 200
    And the body is in json format

     */

    @Test
    public void test2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl);

// Verify the status code is equal to 200
        Assert.assertEquals(response.statusCode(),200);

        //Verify content type is application/json

        System.out.println(response.contentType());
        Assert.assertEquals(response.getContentType(),"application/json");

    }
    @Test
    public void test3(){
        RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl).then()
                .assertThat().statusCode(200)
                .and().contentType("application/json");
    }

    /*
   Given the accept type is json
   When the user sends get request to regions 2
   Then response status code must be 200
   And the body is in json format
   And response body contains Americas

    */
    @Test

    public void test4(){
        Response response = given().accept(ContentType.JSON)
                .when().get(hrUrl + "/2");

        //verify status code
        Assert.assertEquals(response.getStatusCode(), 200);

        //verify content type
        Assert.assertEquals(response.getContentType(), "application/json");

        // verify response body contains Americas
        Assert.assertTrue(response.body().asString().contains("Americas"));


    }

}
