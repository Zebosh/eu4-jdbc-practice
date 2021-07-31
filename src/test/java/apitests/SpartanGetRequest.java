package apitests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import  static io.restassured.RestAssured.*;

public class SpartanGetRequest {

    String spartanUrl="http://54.236.249.191:8000";
    @Test
    public void test1(){
        Response response = when().get(spartanUrl+"/api/spartans");

        System.out.println(response.statusCode());

        response.prettyPrint();

    }

    /* TASK
       When users sends a get request to /api/spartans/3
       Then status code should be 200
       And content type should be application/json;charset=UTF-8
       and json body should contain Fidole
    */
@Test
    public void test2(){

    Response response = when().get(spartanUrl + "/api/spartans/3");
    //verify status code
    Assert.assertEquals(response.statusCode(),200);
    //verify content type
    Assert.assertEquals(response.contentType(),"application/json");
    // verify name
    Assert.assertTrue(response.body().asString().contains("Fidole"));
}

 /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Sparta"
        */

    @Test
    public void  helloTest(){
        Response response = when().get(spartanUrl + "/api/hello");

        //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        System.out.println(response.header("Date"));
        System.out.println(response.header("Content-Length"));

         //verify that header contains date
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

        //verify that Content-Length is 17
        Assert.assertEquals(response.header("Content-Length"),"17");

       Assert.assertEquals(response.getBody().asString(), "Hello from Sparta");
    }
}
