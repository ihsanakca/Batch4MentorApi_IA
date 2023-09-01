package apiTests.day_04;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import  static org.testng.Assert.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class Test_01_JsonToJava_deSerialization {
    @BeforeClass
    public void setUpClass(){
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void allUsersToList(){
        /**
         *
         *          given accept type is json
         *          And query param pagesize is 30
         *          And query param page is 1
         *          And take the request logs
         *          When user sends a get request to /allusers/alluser
         *          Then status code should be 200
         *          And content type should be application/json; charset=UTF-8
         *          10. user'ın adını assert edelim "Selim Gezer"
         *          10.user'ın skillerinin ikincisinin Selenium olduğunu verify edelim
         *          10.user'ın educationın 3.sünün school adının Ankara University olduğunu verify edelim
         *
         */

        Response response = RestAssured.given().accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .and()
                .queryParam("page", 1)
                .when().log().all()
                .get("/allusers/alluser");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //json body'i java collection'a çevirmeye de-serialization denir. Tersi de olur, ona da serilization denir.

        List<Map<String,Object>>allUserData=response.as(List.class);

        //System.out.println("allUserData = " + allUserData);
        //10. user'ın adını assert edelim "Selim Gezer"
        String name = (String) allUserData.get(9).get("name");
        assertEquals(name,"Selim Gezer");

       // 10.user'ın skillerinin ikincisinin Selenium olduğunu verify edelim
        List <String>skills = (List<String>) allUserData.get(9).get("skills");
        String skill2 = skills.get(1);
        assertEquals(skill2,"Selenium");
        //direkt alabilir miyim?
        String skill3 = (String) ((List<?>) allUserData.get(9).get("skills")).get(1);
        assertEquals(skill3,"Selenium");

        //10.user'ın educationın 3.sünün school adının Ankara University olduğunu verify edelim
        List<Map<String,Object>> dataOfTenthUserEducation= (List<Map<String, Object>>) allUserData.get(9).get("education");

        String school = (String) dataOfTenthUserEducation.get(2).get("school");
       assertEquals(school,"Ankara University");
    }

    @Test
    public void jsonToJavaDeSerializationMap(){
        /**
         Given accept type json
         When user sends a get request to https://bookstore.toolsqa.com/BookStore/v1/Books
         Then status code should be 200
         And content type should be application/json; charset=utf-8
         And the first book isbn should be 9781449325862
         And the first book publisher should be O'Reilly Media

         */
        Response response = RestAssured.given().accept(ContentType.JSON)
                .and()
                .when()
                .get("https://bookstore.toolsqa.com/BookStore/v1/Books");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        Map<String,Object>jsonMap =response.body().as(Map.class);

        List<Map<String,Object>>bookData= (List<Map<String, Object>>) jsonMap.get("books");
        String isbn = (String) bookData.get(0).get("isbn");
        assertEquals(isbn,"9781449325862");

        String  publisher = (String) bookData.get(0).get("publisher");
        assertEquals(publisher,"O'Reilly Media");

        System.out.println("bookData.get(3) = " + bookData.get(3));


    }
}
