package apitests.Day_8;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;


public class SpartanBasicAuth {

    //basic authorazation
    @Test
    public void test1(){
     given().accept(ContentType.JSON)
             .and()
             .auth().basic("admin","admin")
     .when()
             .get("http://54.226.240.64:8000/api/spartans")
             .then().log().all().statusCode(200);
    }
}
