package apitests.Day_8;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class PutRequestDemo {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");

    }

    @Test
    public void test1(){

        //create one map to keep json body
        Map<String, Object>putRequestMap=new HashMap<>();

        putRequestMap.put("name", "PutName");
        putRequestMap.put("gender", "Male");
        putRequestMap.put("phone", "9876543210");

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id", 182)
                .and()
                .body(putRequestMap)
                .when().put("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);

//create get request after update
//
//        Response response = given().log().all()
//                .and()
//                .contentType(ContentType.JSON)
//                .and()
//                .pathParam("id", 182)
//                .and()
//                .body(putRequestMap)
//                .when().put("/api/spartans/{id}");
//
//        int id=response.path("id");
//
//        System.out.println(id);
//
//        given().accept(ContentType.JSON)
//                .pathParam("id", id)
//                .when().get("/api/spartans/{id}")
//                .then().statusCode(200).log().all();


    }

    @Test
    public void patchTest(){
        Map<String, Object>patchRequestMap=new HashMap<>();

        patchRequestMap.put("name","Flo");
        patchRequestMap.put("gender","Female");



        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id", 182)
                .and()
                .body(patchRequestMap)
                .when().patch("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);


    }


}
