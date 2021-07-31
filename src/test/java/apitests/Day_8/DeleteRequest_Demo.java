package apitests.Day_8;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;


public class DeleteRequest_Demo {
    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");

    }

        @Test
        public void test1(){
        given().pathParam("id",158)
                .when().delete("api/spartans/{id}")
                .then().statusCode(204).log().all();

        }



    @Test
    public void DeleteRandomSpartan(){
        Random random=new Random();
        int idDelete= random.nextInt(200)+1;

        System.out.println("This Id= "+idDelete+" spartan will be deleted");

        given().pathParam("id",idDelete)
                .when().delete("api/spartans/{id}")
                .then().statusCode(204).log().all();

    }

}
