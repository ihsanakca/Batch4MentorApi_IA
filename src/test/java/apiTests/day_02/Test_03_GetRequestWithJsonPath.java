package apiTests.day_02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.baseURI;

public class Test_03_GetRequestWithJsonPath {
    @BeforeClass
    public void setUpClass(){
        baseURI ="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void testWithJsonPetMethod(){
        /**Class Task
         * Given accept type JSON
         * and Query parameter value pagesize 5
         * and Query parameter value page 1
         * When user send GET request to /allusers/alluser
         * Then response status code is 200
         * And response content type is "application/json; charset=UTF-8"
         * Verify that first id 1
         * verify that first name MercanS
         * verify that last id is 102
         * verify that last name is GHAN
         */

        Map<String , Object> queryMap=new HashMap<>();
        queryMap.put("page",1);
        queryMap.put("pagesize",5);

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .queryParams(queryMap)
//                .queryParam("page",1)
//                .queryParam("pagesize",5)
                .and()
                .when()//.log().all()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //Json path ile devam edelim
        JsonPath jsonPath=response.jsonPath();

        //ilk elemanın idsinin 1 olduğunu assert edelim

        Assert.assertEquals(jsonPath.getInt("[0].id"),1);
        Assert.assertEquals(jsonPath.getInt("id[0]"),1);

        //dördüncü elemanın adını assert edelim  : wilini3845@once
        Assert.assertEquals(jsonPath.get("name[3]"),"wilini3845@once");
        Assert.assertEquals(jsonPath.getString("name[3]"),"wilini3845@once");

        //idlerin hepsini assert edelim
        List<Integer> expectedList=new ArrayList<>(Arrays.asList(1,5,24,29,33));

        List<Integer> actualList = jsonPath.getList("id");
        Assert.assertEquals(actualList,expectedList);

        List<Integer> actualList2=response.path("id");
        Assert.assertEquals(actualList2,expectedList);

        //beşinci elemanın skillerinden ikincisinin "Cucumber" olduğunu assert edelim
        List<String> besinciElemanSkills = jsonPath.getList("skills[4]");
        String actualSkill = besinciElemanSkills.get(1);
        String expectedSkill="Cucumber";

        Assert.assertEquals(actualSkill,expectedSkill);

        String actualSkill1 = jsonPath.getString("[4].skills[1]");
        String actualSkill2 = jsonPath.getString("skills[4][1]");

        Assert.assertEquals(actualSkill1,actualSkill2);

        System.out.println("+++++++++++++++++++++++++++++++");

        List<List<String>> education=jsonPath.getList("education");
        System.out.println("education = " + education);

        List<List<Map<String,Object>>> allEducations=jsonPath.getList("education");

        System.out.println("allEducations.get(0).get(0).get(\"school\") = " + allEducations.get(0).get(0).get("school"));

        System.out.println("allEducations.get(2).get(10).get(\"school\") = " + allEducations.get(2).get(11).get(
                "school"));


    }
}
