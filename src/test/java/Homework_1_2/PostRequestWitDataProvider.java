package Homework_1_2;

import apitests.Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
//import utilities.ExcelUtil;
import static org.testng.Assert.assertEquals;

public class PostRequestWitDataProvider {


    //Optional homeworks
    //Homework-1
    //1-Create excel file from mackaroo website, which includes name,gender,phone
    //2-Download excel file
    //3- using testng data provider and apache poi create data driven posting from spartan





    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");

    }

    @DataProvider
    public Object [][] userData(){
        ExcelUtil mockData=new ExcelUtil("src/test/resources/MOCK_DATA.xlsx","data");
        String [][] dataArray =mockData.getDataArrayWithoutFirstRow();
        return dataArray;
    }

    @Test(dataProvider = "userData")
    public void test1(String name, String gender, String phone){

        BigDecimal phoneNum=new BigDecimal(phone);
        Spartan spartan=new Spartan();
        spartan.setName(name);
        spartan.setGender(gender);
        spartan.setPhone(phoneNum.longValue());

        Response response= given().log().all().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and()
                .body(spartan)
                .when().post("/api/spartans");

        int id=response.path("data.id");
        System.out.println("id = " + id);

        given().accept(ContentType.JSON)
                .and().pathParam("id",id)
                .get("/api/spartans/{id}")
                .then().statusCode(200).log().all();


    }



    //Homework-2
    //-Create one mackaroo api for name,gender,phone
    //send get request to retrieve random info from that api
    //use those info to send post request to spartan

    @Test
    public  void  test2(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("key", "c5e36f20")
                .get("https://my.api.mockaroo.com/spartan.json");

        assertEquals(response.statusCode(), 200);

        response.prettyPrint();

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().body(response.as(Map.class))
                .log().all()
                .post("/api/spartan.json");
    }
}



