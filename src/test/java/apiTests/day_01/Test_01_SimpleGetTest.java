package apiTests.day_01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_01_SimpleGetTest {
    String bookCartBaseURL="https://bookcart.azurewebsites.net";

    @Test
    public void test1(){
        Response response=RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers("Accept",ContentType.JSON)
                .when()
                .get(bookCartBaseURL+"/api/Book");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=utf-8");

        String bodyString = response.body().asString();

        Assert.assertTrue(bodyString.contains("Alyssa Cole"));

        System.out.println("bodyString = " + bodyString);

    }

    @Test
    public void test2(){
        //RestAssured Library kullanarak assert işlemi yapalım...

       RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(bookCartBaseURL + "/api/Book")
                .then()
                .contentType("application/json; charset=utf-8")
                .and()
                .statusCode(200);


    }
}
