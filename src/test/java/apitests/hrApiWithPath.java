package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class hrApiWithPath {
    @BeforeClass
    public void beforeclass(){

        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void getCountriesWithPath(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        String  secondCountryName=response.path("items.country_name[-1]");
        System.out.println(secondCountryName);

       String link = response.path("items.links[2].href[0]");

        System.out.println(link);

        //assert that all regions have id equals 2
        int count = response.path("count");
        for (int i = 0; i <count; i++) {
            int region_id = response.path("items.region_id[" + i + "]");
            assertEquals(region_id,2);
            String country_Id = response.path("items.country_id[" + i + "]");
            System.out.println("country_Id = " + country_Id);
        }

        //2nd way

        List<Integer> regionIDs = response.path("items.region_id");
        for (int regionId : regionIDs) {
            assertEquals(regionId,2);
        }
    }
    //verify that all job_id are equal to IT_PROG
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<String>job_IDs=response.path("items.job_id");

        for (String job_id : job_IDs) {
            assertEquals(job_id, "IT_PROG");
        }
    }
}
