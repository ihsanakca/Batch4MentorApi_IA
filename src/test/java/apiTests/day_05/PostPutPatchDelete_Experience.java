package apiTests.day_05;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class PostPutPatchDelete_Experience {

    static Response response;
    static int experienceId;

     String email="mgezer@gmail.com";
     String password="Mg12345678";
    @BeforeClass
    public void setUpClass(){
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    /**
     * Request ne içermelidir?
     * HTTP method (Get, Pst, Put vb..)
     * Base URL
     * EndPoint
     * Headers (Gerekiyorsa)
     * Parameters (Path veya Query) (Gerekiyorsa)
     * Body (Post, Put, Patch için zorunludur)
     * Token-Authorization (gerekiyorsa)
     */

    /**
     * {
     *   "job": "Junior Developer",
     *   "company": "Kraft Techex",
     *   "location": "USA",
     *   "fromdate": "YYYY-MM-DD",
     *   "todate": "YYYY-MM-DD",
     *   "current": "false",
     *   "description": "Description"
     * }
     */

    @Test (priority = 0)
    public void addNewExperience(){

        String body="{\n" +
                "  \"job\": \"PHP Developer\",\n" +
                "  \"company\": \"Kraft Techniques\",\n" +
                "  \"location\": \"Turkey\",\n" +
                "  \"fromdate\": \"2016-11-11\",\n" +
                "  \"todate\": \"2018-12-12\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        response = RestAssured.given().contentType(ContentType.JSON)
                .headers("token", Authorization.getToken(email, password))
                .body(body)
                .post("/experience/add")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);
        experienceId=response.path("id");
        System.out.println("experienceId = " + experienceId);
    }

    @Test (priority = 1)
    public void updateExperienceWithPut(){
        String body="{\n" +
                "  \"job\": \"Java Developer\",\n" +
                "  \"company\": \"Kraft Studies\",\n" +
                "  \"location\": \"Turkey\",\n" +
                "  \"fromdate\": \"2016-11-11\",\n" +
                "  \"todate\": \"2018-12-12\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        String jsonBody= """
                {
                  "job": "Junior Developer",
                  "company": "Kraft Techex",
                  "location": "USA",
                  "fromdate": "2012-10-10",
                  "todate": "2016-10-22",
                  "current": "false",
                  "description": "Description"
                }
                """;

         response = RestAssured.given().accept(ContentType.JSON)
                .headers("token", Authorization.getToken(email, password))
                .body(jsonBody)
                .queryParam("id",experienceId)
                 .when()
                .put("/experience/updateput")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);

    }

    @Test (priority = 2)
    public void deleteExperience(){
        response = RestAssured.given().accept(ContentType.JSON)
                .headers("token", Authorization.getToken(email, password))
                .pathParam("id",experienceId)
                .when()
                .delete("/experience/delete/{id}")
                .prettyPeek();
        Assert.assertEquals(response.statusCode(),200);
    }
}
