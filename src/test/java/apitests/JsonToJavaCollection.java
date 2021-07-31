package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class JsonToJavaCollection {
    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    @Test
    public void SpartanToMap(){
        Response response = given().contentType(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}");
        assertEquals(response.statusCode(),200);

        //convert Json to Java collection
        Map<String,Object> jsonDataMap=response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);
        String name = (String) jsonDataMap.get("name");
        assertEquals(name,"Meta");

   BigDecimal phone=new BigDecimal(String.valueOf(jsonDataMap.get("phone")));
        System.out.println("phone = " + phone);
    }
    @Test
    public void allSpartansToListOfMap(){

        Response response = given().contentType(ContentType.JSON)
                .when().get("/api/spartans");
        assertEquals(response.statusCode(),200);

        //we need to deserialize JSON to List of Maps

       List<Map<String,Object>>allSpartansList= response.body().as(List.class);

        System.out.println("allSpartansList = " + allSpartansList);

        //print second spartan firstname
        System.out.println(allSpartansList.get(1).get("name"));

        Map<String, Object> spartan3 = allSpartansList.get(2);

    }

    @Test
    public void regionToMap(){
        Response response = given().contentType(ContentType.JSON)
                .when().get("http://54.236.249.191:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        Map <String,Object> regionMap= response.body().as(Map.class);

        System.out.println(regionMap.get("count"));
        System.out.println(regionMap.get("hasMore"));

       List<Map<String,Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");
        System.out.println(itemsList.get(0).get("region_name"));
    }
}
