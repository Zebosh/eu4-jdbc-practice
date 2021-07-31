package apitests.Day6_POJO;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.AssertJUnit.assertEquals;

public class Pojo_deserialization {

    @Test
    public void oneSpartanPojo(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("http://54.236.249.191:8000/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        //JSON to POJO
        //JSON to spartan class object

        Spartan spartan15=response.body().as(Spartan.class);
        System.out.println(spartan15);
        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());

        assertEquals(spartan15.getGender(),"Female");
        assertEquals(spartan15.getName(),"Meta");
    }

    @Test
    public void regionToPojo(){
        Response response = when().get("http://54.236.249.191:1000/ords/hr/regions");

      assertEquals(response.statusCode(),200);

      //JSON to POJO

        Regions regions = response.body().as(Regions.class);

        System.out.println("regions.getHasMore() = " + regions.getHasMore());
        System.out.println("regions.getCount() = " + regions.getCount());

        List<Item> items = regions.getItems();
        System.out.println(items.get(0).getRegionId());

    }

    @Test
    public void gson_example(){
        Gson gson=new Gson();
        //Json to Java or Pojo------> de-serialization

        String myJsonData="{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";
        Map <String,Object>map = gson.fromJson(myJsonData, Map.class);

        System.out.println(map);


        Spartan spartan15=gson.fromJson(myJsonData,Spartan.class);
        System.out.println(spartan15);

        // -------SERIALIZATION----------------

      //  JAVA COLLECTION OR POJO TO JSON

        Spartan spartanEU=new Spartan(200,"Mike","Male", 12334566);

        String jsonSpartanEU=gson.toJson(spartanEU);
        System.out.println("jsonSpartanEU = " + jsonSpartanEU);
    }
}
