package apiTests.day_02;

import apiTests.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Test_02_GetRequestWithPathMethod extends TestBase {


    @Test
    public void testWithPathMethod(){
        /**
         *         "id": 24,
         *         "name": "mike",
         *         "email": "mike@gmail.com",
         *         "password": "$2y$10$KWJ2f3iTUFvkvzTS7/O0AOBmfwYknjscuwdA8n4c25gkzFqi9tswW",
         *         "about": "graduated SDET 2023",
         *         "terms": "2",
         *         "date": "2022-09-12 20:50:38",
         *         "job": "QA Test Engineer",
         *         "company": "Krafttech",
         *         "website": "www.kraftech.com",
         *         "location": "Istanbul",
         *         "skills": [
         *             "Selenium",
         *             "Java"
         *         ],
         */
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("userId", 24)
                .when()
                .get("/allusers/getbyid/{userId}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //path methodu ile body'den veri alma

        System.out.println("response.body().path(\"name\") = " + response.body().path("name"));
        System.out.println("response.path(\"id\") = " + response.path("id"));

        int id=response.path("id[0]");
        int id2=response.path("[0].id");

        Assert.assertEquals(id,id2);

        //emaili alalım
        String  email = response.path("email[0]");
        String  email2 = response.path("[0].email");
        Assert.assertEquals(email2,email);

    }

    @Test
    public void testAllUsersWithPathParam(){
        /**Class Task
         * Given accept type JSON
         * and Query parameter value pagesize 50
         * and Query parameter value page 1
         * When user send GET request to /allusers/alluser
         * Then response status code is 200
         * And response content type is "application/json; charset=UTF-8"
         * Verify that first id 1
         * verify that first name MercanS
         * verify that last id is 102
         * verify that last name is GHAN
         */

        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("page", 1)
                .and()
                .queryParam("pagesize", 50)
                .when()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        Assert.assertTrue(response.headers().toString().contains("Content-Type"));

        //ilk elemanın idsini ve nameini assert edelim

        int id=response.path("id[0]");
        String name=response.path("[0].name");

        Assert.assertEquals(id,1);
        Assert.assertEquals(name,"MercanS");

        //son elemanın idsini ve nameini assert edelim

        int idLast=response.path("id[-1]");
        String nameLast=response.path("[49].name");

        Assert.assertEquals(idLast,102);
        Assert.assertEquals(nameLast,"GHAN");

        //isimlerden birinin Selim Gezer olduğunu assert edelim
        List<String> names = response.path("name");
        System.out.println("names = " + names);
       Assert.assertTrue(names.stream().anyMatch(e->e.equals("Selim Gezer")));
       Assert.assertTrue(names.contains("Selim Gezer"));
       Assert.assertTrue(names.contains("Muhammet Yusuf"));
//       List<String> expectedList=new ArrayList<>(Arrays.asList("Selim Gezer,Muhammet Yusuf"));
//       Assert.assertTrue(names.contains(expectedList));

        //3. elemanın company'sini alalım
        Assert.assertEquals(response.path("company[2]"),"Krafttech");

        //3. elemanın skillerinin ikincisini alalım
        List<List<String>> allUsersSkills=response.path("skills");
        System.out.println("allUsersSkills = " + allUsersSkills);

        System.out.println("-----------------------------");

        String ucuncuElemanSkill=response.path("skills[2][1]");
        String ucuncuElemanSkill2=response.path("[2].skills[1]");

        Assert.assertEquals(ucuncuElemanSkill,ucuncuElemanSkill2);

        //3.elemanın ikinci education kaydının  schoolunu alalım
        System.out.println("response.path(\"[2].education[1].school\") = " + response.path("[2].education[1].school"));
        System.out.println("response.path(\"[2].education.school[1]\") = " + response.path("[2].education.school[1]"));
        System.out.println("response.path(\"education[2].school[1]\") = " + response.path("education[2].school[1]"));
        System.out.println("response.path(\"education[2][1].school\") = " + response.path("education[2][1].school"));
        System.out.println("response.path(\"[2]['education'][1]['school']\") = " + response.path("[2]['education'][1]['school']"));


    }
}
