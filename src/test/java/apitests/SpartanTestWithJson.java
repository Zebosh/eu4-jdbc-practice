package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class SpartanTestWithJson {


        @BeforeClass
        public void beforeclass() {

            baseURI = ConfigurationReader.get("spartan_api_url");
        }
        @Test
        public void test1(){
    Response response = given().accept(ContentType.JSON)
            .and().pathParam("id", 11)
            .when().get("/api/spartans/{id}");

          assertEquals(response.statusCode(),200);
          assertEquals(response.contentType(),"application/json");

          //assert id and name with path
            int id = response.path("id");
            String name = response.path("name");

            assertEquals(id,11);
            assertEquals(name,"Nona");

            //assign response to jsonPath
            JsonPath jsonPath=response.jsonPath();

          int  idJson= jsonPath.getInt("id");
          String nAme= jsonPath.getString("name");
          String gender= jsonPath.getString("gender");
          long phone=jsonPath.getLong("phone");

            System.out.println("idJson = " + idJson);
            System.out.println("nAme = " + nAme);
            System.out.println("gender = " + gender);
            System.out.println("phone = " + phone);


            assertEquals(idJson,11);
            assertEquals(nAme,"Nona");
            assertEquals(gender,"Female");
            assertEquals(phone,7959094216l);

        }

    }

