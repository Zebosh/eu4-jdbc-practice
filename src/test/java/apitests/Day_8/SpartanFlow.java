package apitests.Day_8;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class SpartanFlow {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");

    }


    @Test(priority = 1)
    public void PostSpartan() {

        Map<String,Object> postSpartanMap=new HashMap<>();
        postSpartanMap.put("name","Maryam");
        postSpartanMap.put("gender","Female");
        postSpartanMap.put("phone",9876543211l);

        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(postSpartanMap)
                .when().post("/api/spartans");

        int id=response.path("data.id");
        System.out.println("id = " + id);

    }
    @Test(priority = 2)
    public void PutSpartan() {
        Map<String,Object> putSpartanMap=new HashMap<>();
        putSpartanMap.put("name","Mariya");
        putSpartanMap.put("gender","Female");
        putSpartanMap.put("phone",9876543211l);


        given().log().all().
                contentType(ContentType.JSON)
                .and().pathParam("id",595)
                .and().body(putSpartanMap)
                .when().put("/api/spartans/{id}")
                .then().assertThat()
                .statusCode(204).log().all();

    }

    @Test(priority = 3)
    public void PatchSpartan() {
        Map<String,Object> patchSpartanMap=new HashMap<>();
        patchSpartanMap.put("name","Mariyam");

        given().log().all()
                .contentType(ContentType.JSON)
                .and().pathParam("id",595)
                .and().body(patchSpartanMap)
                .when().put("/api/spartans/{id}")
                .then().log().all().assertThat().statusCode(204);

    }

    @Test(priority = 4)
    public void GetSpartan() {

        given().accept(ContentType.JSON)
                .and(). pathParam("id", 595)
                .when().get("/api/spartans/{id}")
                .then().log().all().assertThat().statusCode(200);
    }

    @Test(priority = 5)
    public void DeleteSpartan() {

        given().accept(ContentType.JSON)
                .and().pathParam("id",595)
                .when().delete("/api/spartans/{id}")
                .then().log().all()
                .assertThat(). statusCode(204);

    }
}
