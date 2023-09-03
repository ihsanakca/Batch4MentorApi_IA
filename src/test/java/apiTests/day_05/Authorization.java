package apiTests.day_05;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class Authorization {
    static String email="mgezer@gmail.com";
    static String password="Mg12345678";
    @BeforeClass
    public void setUpClass(){
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void tokenTest(){


        Response response = RestAssured.given().accept(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password", password)
                .when().log().all()
                .post("/allusers/login")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);

        String token=response.path("token");
        System.out.println("token = " + token);
    }

    public  static String getToken(){
        Response response = RestAssured.given().accept(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password", password)
                .when().log().all()
                .post("/allusers/login");

        Assert.assertEquals(response.statusCode(),200);

        String token=response.path("token");
        return token;
    }

    public  static String getToken(String userEmail,String userPassword){
        Response response = RestAssured.given().accept(ContentType.MULTIPART)
                .formParam("email", userEmail)
                .formParam("password", userPassword)
                .when().log().all()
                .post("/allusers/login");

        Assert.assertEquals(response.statusCode(),200);

        String token=response.path("token");
        return token;
    }
}
