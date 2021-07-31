package Homework_1_2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static org.hamcrest.Matchers.*;
import static  org.testng.Assert.assertEquals;

import java.util.List;

import static io.restassured.RestAssured.*;

public class home_work1 {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");

    }
    /* TASK 1
    - Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is
     */
    @Test
    public void task1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country_id", "US")
                .when().get("/countries/{country_id}");

        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.contentType(),"application/json");

        String country_id = response.path("country_id");
        String country_name = response.path("country_name");
        int region_id = response.path("region_id");

        Assert.assertEquals("US",country_id);
        Assert.assertEquals("United States of America",country_name);
        Assert.assertEquals(2, region_id);

    }
/*
TASK 2:
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25

 */
    @Test
    public void task2(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");
        
        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.contentType(),"application/json");

        List<String> job_IDs= response.path("items.job_id");

        for (String job_id : job_IDs) {
           Assert.assertTrue(job_id.startsWith("SA"));
        }

       List<Integer> department_IDs=response.path("items.department_id");
        for (int department_id : department_IDs) {
          assertEquals(department_id,80);
        }
        int  count = response.path("count");
        Assert.assertTrue(count==25);


    }
/*
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
 */
    @Test
    public void task3(){

        given().accept(ContentType.JSON)
                .when().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().body("items.region_id",everyItem(equalTo(3)),
                "count",equalTo(6), "hasMore",equalTo(false),
                "items.country_name",hasItems("Australia","China","India","Japan","Malaysia","Singapore"));


//        Response response = given().accept(ContentType.JSON)
//                .and().queryParam("q", "{\"region_id\":3}")
//                .when().get("/countries");
//
//       assertEquals(response.statusCode(),200);
//
//       assertEquals(response.contentType(),"application/json");
//
//       List<Integer> region_IDs=response.path("items.region_id");
//        for (int region_id : region_IDs) {
//          assertEquals(region_id, 3);
//        }
//        int count = response.path("count");
//        assertEquals(count,6);
//
//        boolean hasMore = response.path("hasMore");
//        assertEquals(hasMore, false);
//
//        List<String> countryNames = response.path("items.country_name");
//
//        for (String country_name : countryNames) {
//            System.out.println(country_name);
//        }



    }
}
