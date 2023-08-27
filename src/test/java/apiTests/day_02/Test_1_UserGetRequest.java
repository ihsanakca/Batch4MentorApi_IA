package apiTests.day_02;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_1_UserGetRequest {

    @BeforeClass
    public void setUpClass(){
        baseURI ="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void headerTest(){

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("userId", 62)
                .when()
                .get("/allusers/getbyid/{userId}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //response headers

        System.out.println("response.headers() = " + response.headers());

        Assert.assertEquals(response.header("Content-Length"),"756");

        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

        Headers headers = response.headers();
        System.out.println("headers.getValue(\"Date\") = " + headers.getValue("Date"));
        System.out.println("response.header(\"Date\") = " + response.header("Date"));

    }

    @Test
    public void negativeTest(){

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("userId", 8)
                .when()
                .get("/allusers/getbyid/{userId}");

        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        String body = response.body().asString();
        Assert.assertTrue(body.contains("No User Record Found..."));

    }


}
