package apitests.Day6_POJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.DBUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PostRequest_demo {

      /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Female",
      "name":"Liza,
      "phone":2345678342
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");

    }

    @Test
    public void PostNewSpartan(){

        String jSonBody="{\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"name\": \"Liza\",\n" +
                "  \"phone\": 2345678342\n" +
                "}";
     Response response= given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and()
                .body(jSonBody)
                .when().post("/api/spartans");

     assertEquals(response.statusCode(),201);
     assertEquals(response.contentType(),"application/json");

     //verify message
        String actualMessage=response.path("success");
        String expectedMessage="A Spartan is Born!";

        assertEquals(actualMessage,expectedMessage);

    //verify  data

        String actualName=response.path("data.name");
        String actualGender=response.path("data.gender");
        long actualPhone = response.path("data.phone");

        assertEquals(actualName,"Liza");
        assertEquals(actualGender,"Female");
        assertEquals(actualPhone,2345678342l);


    }

    @Test
    public void PostNewSpartan2(){

        Map<String, Object> requestMap=new HashMap<>();

        requestMap.put("name","Amy");
        requestMap.put("gender","Female");
        requestMap.put("phone",2345672330l);


        given().log().all().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(requestMap)
                .when().post("/api/spartans")
                .then().log().all().statusCode(201)
                .and().contentType("application/json")
                .and()
                .body("success", Matchers.equalTo("A Spartan is Born!"),
                        "data.name", Matchers.equalTo("Amy"),
                        "data.gender", Matchers.equalTo("Female"),
                        "data.phone",Matchers.equalTo(2345672330l));



    }

    @Test

    public void PostNewSpartan3(){
      Spartan spartanEU=new Spartan();
        spartanEU.setName("Mary");
        spartanEU.setPhone(7855223344l);
        spartanEU.setGender("Female");


        given().log().all().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when().post("/api/spartans")
                .then().log().all().statusCode(201)
                .and().contentType("application/json")
                .and()
                .body("success", Matchers.equalTo("A Spartan is Born!"),
                        "data.name", Matchers.equalTo("Mary"),
                        "data.gender", Matchers.equalTo("Female"),
                        "data.phone",Matchers.equalTo(7855223344l));
    }


    @Test

    public void PostNewSpartan4(){
        Spartan spartanEU=new Spartan();
        spartanEU.setName("Mary");
        spartanEU.setPhone(7855223344l);
        spartanEU.setGender("Female");


        Response response= given().log().all().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when().post("/api/spartans");

        int id=response.path("data.id");
        System.out.println("id = " + id);

        given().accept(ContentType.JSON)
         .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();

//
    }



}
