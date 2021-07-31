package apitests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;

public class hrApiWithJsonPath {
    @BeforeClass
    public void beforeclass(){

        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void test1(){

        Response response=get("/countries");
        //assign response to jsonPath
        JsonPath jsonPath=response.jsonPath();
        String secondCountryName = jsonPath.getString("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        List<String> countryIDs=jsonPath.getList("items.country_id");
        System.out.println(countryIDs);

        //get all country names where id is equal to 2
        List<String> countryNamesWithRegionID2=jsonPath.getList("items.findAll {it.region_id==2}.country_name");
        System.out.println(countryNamesWithRegionID2);
    }

    @Test
    public void test2(){

        Response response=given().queryParam("limit",107)
                .when().get("/employees");
        JsonPath jsonPath=response.jsonPath();

        //get me all firstnames of employees who is working as IT_PROG

        List<String> firstNames=jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.first_name");
        System.out.println(firstNames);

        //get me list of all firstnames who is making more than 10000

        List<String> firstNames2=jsonPath.getList("items.findAll {it.salary<2200}.first_name");
        System.out.println(firstNames2);

        //get the employee name who is the highest salary
       String kingName= jsonPath.getString("items.max {it.salary}.first_name");
        System.out.println(kingName);

    }



}
