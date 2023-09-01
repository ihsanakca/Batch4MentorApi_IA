package apiTests.day_04;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojoTemplates.GoRestUser;

import static io.restassured.RestAssured.*;

public class Test_02_Pojo {

    @BeforeClass
    public void setUpClass(){
        baseURI="https://gorest.co.in/public/v2";
    }

    @Test
    public void goRestPojoTest(){
        /**
         //TASK
         //base url = https://gorest.co.in
         //end point = /public/v2/users
         //path parameter = {id} --> 4992711
         //send a get request with the above credentials
         //parse to Json object to pojo (custom java class)
         //verify that the body below
         /*
         {
         "id": 4992711,
         "name": "Harit Talwar",
         "email": "talwar_harit@yost.test",
         "gender": "female",
         "status": "active"
         },
         */

        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 4992711)
                .when()
                .get("/users/{id}");

        Assert.assertEquals(response.statusCode(),200);

        GoRestUser goRestUser = response.as(GoRestUser.class);
        System.out.println("goRestUser.getEmail() = " + goRestUser.getEmail());
        Assert.assertEquals(goRestUser.getId(),4992711);

        System.out.println("goRestUser = " + goRestUser);

    }
}
