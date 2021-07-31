package Homework_1_2.StarWarsApi_Practice;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GetRequests {
    @BeforeClass
    public void beforeclass(){
        baseURI="https://swapi.dev/api/";
    }

    @Test
    public void withPath(){
        Response response = given().accept(ContentType.JSON)
                .when().pathParam("id",1)
                .and().get("people/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        String name= response.path("name");
        String hairColor=response.path("hair_color");
        String gender=response.path("gender");
        String height=response.path("height");

        assertEquals(name,"Luke Skywalker");
        assertEquals(hairColor,"blond");
        assertEquals(gender,"male");
        assertEquals(height,"172");

    }

    @Test
    public void withJsonPath(){

        Response response = given().accept(ContentType.JSON)
                .when().get("starships");

        JsonPath jsonPath=response.jsonPath();

        int count = jsonPath.getInt("count");
        assertEquals(count, 36);

        List<Object> nameList = jsonPath.getList("results.name");
        System.out.println(nameList);

        List<Object> conditionList = jsonPath.getList("results.findAll{it.crew==\"1\"}.name");

        System.out.println("conditionList = " + conditionList);

    }
@Test
    public void hamcrestMatchers(){
        given().accept(ContentType.JSON)
                .when().pathParam("id",4)
                .and().get("vehicles/{id}")
                .then().assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("name", equalTo("Sand Crawler"),
                       "model", equalTo("Digger Crawler") ,
                      "manufacturer", equalTo("Corellia Mining Corporation") );
    }

    @Test
    public void JsonToJavaCollectionMap(){
//        given().accept(ContentType.JSON)
//                .and().pathParam("id",11)
//                .when().get("species/{id}")
//                .then().assertThat().statusCode(200)
//                .contentType("application/json")
//        .header("Date", notNullValue())
//        .header("Connection", equalTo("keep-alive"));

       Response response= given().accept(ContentType.JSON)
                .and().pathParam("id",11)
                .when().get("species/{id}");

        Map<String, Object>species11 = response.body().as(Map.class);

        assertEquals(species11.get("name"), "Neimodian");
        assertEquals(species11.get("average_height"), "180");
        assertEquals(species11.get("eye_colors"), "red, pink");

    }

    // List of Map

    @Test
    public void JsonTOJavaListOfMap(){
        Response response= given().accept(ContentType.JSON)
                .when().get("species");

       Map<String, Object>species = response.body().as(Map.class);

        List<Map<String,Object>> results = (List<Map<String, Object>>) species.get("results");
        System.out.println(results.get(0).get("name"));

    }

    @Test
    public void JsonToPojo(){

        Response response = given().accept(ContentType.JSON)
                .get("planets/7");

        assertEquals(response.statusCode(),200);

        Endor endor= response.body().as(Endor.class);
        System.out.println("endor.getName() = " + endor.getName());
        System.out.println("endor.getPopulation() = " + endor.getPopulation());
        System.out.println("endor.getRotationPeriod() = " + endor.getRotationPeriod());

    }

}
