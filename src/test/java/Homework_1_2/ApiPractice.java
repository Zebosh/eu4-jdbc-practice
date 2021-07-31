package Homework_1_2;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class ApiPractice {

    @BeforeClass
    public void beforeclass(){
        baseURI="https://www.breakingbadapi.com/api";

    }

    @Test
    public void withPath(){
        Response response = given().accept(ContentType.JSON)
                .and().get("/characters");

        assertEquals(response.statusCode(),200);

        assertEquals(response.contentType(), "application/json; charset=utf-8");

    }

    @Test
    public void pathWithQuery(){
        Response response = given().accept(ContentType.JSON)
                .when().queryParam("limit", 10)
                .and().queryParam("offset", 10)
                .when().get("/characters");

        assertEquals(response.statusCode(),200);

        assertEquals(response.contentType(), "application/json; charset=utf-8");
    }

    @Test
    public void withJson(){
        Response response = given().pathParam("id", 60)
                .get("/episodes/{id}");

        JsonPath jsonPath=response.jsonPath();

      int id= jsonPath.getInt("episode_id");
        String title=  jsonPath.getString("title");
       String season= jsonPath.getString("season");

       assertEquals(id,60);
       assertEquals(title,"Ozymandias");
       assertEquals(season,"5");

    }

    @Test
    public void withJsonList() {
        Response response = given().accept(ContentType.JSON)
                .and().get("/characters");
        JsonPath jsonPath=response.jsonPath();

     List<String>names=jsonPath.getList("name");
        System.out.println(names);

        String name1 = jsonPath.getString("name[0]");

        System.out.println(name1);

        String name2 = jsonPath.getString("name[1]");
        System.out.println(name2);

       String occupation= jsonPath.getString("occupation[0]");

    }
@Test
    public void hamcrestMatchers(){
        given().accept(ContentType.JSON)
                .when().queryParam("name","Gustavo Fring")
                .get("/death-count")
                .then().assertThat().log().all()
                .contentType("application/json; charset=utf-8")
                .and().statusCode(200)
                .and()
                .body("name", equalTo("Gustavo Fring"),
                        "deathCount", equalTo(22));

    }
}
