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

public class CBTrainingWithJson {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("cbt_api_url");
    }

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 20201)
                .when().get("/student/{id}");

        assertEquals(response.statusCode(),200);
       // assertEquals(response.contentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath=response.jsonPath();
        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println(firstName);

        String phoneNumber = jsonPath.get("students.contact[0].phone");
        System.out.println(phoneNumber);

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println(zipCode);
        assertEquals(zipCode,60606);


    }
}
