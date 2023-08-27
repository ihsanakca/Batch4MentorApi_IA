package apiTests.day_03;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Test_04_HamcrestMatchers {
    @BeforeClass
    public void beforeClass() {

        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void getOneUserWithHamcrestMatchers(){
        /**
         *         given accept type is json
         *         And path param id is 62
         *         When user sends a get request to /allusers/getbyid/{id}
         *         Then status code should be 200
         *         And content type should be "application/json; charset=UTF-8"
         *         user's id should be "62"
         *         user's name should be "Selim Gezer"
         *         user's job should be "QA Automation Engineer"
         *         User's second skill should be "Selenium"
         *         User's third education school name should be "Ankara University"
         *         The response header Content-Lenght should be 756
         *         User's email should be "sgezer@gmail.com"
         *         User's company should be "KraftTech"
         *         Response headers should have "Date" header
         *
         *
         */

        given().accept(ContentType.JSON)
                .pathParam("id",62)
                .get("/allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .and()
                .body("[0].id", Matchers.equalTo(62),
                        "name[0]",Matchers.equalTo("Selim Gezer"),
                        "[0].job",Matchers.equalTo("QA Automation Engineer"),
                        "job[0]",Matchers.equalTo("QA Automation Engineer"),
                        "skills[0][1]",Matchers.equalTo("Selenium"),
                        "[0].education[2].school",Matchers.equalTo("Ankara University"))
                .and()
                .header("Content-Length","756")
                .header("Content-Length",Matchers.equalTo("756"))
                .headers("Content-Length","756")
                .headers("Date",Matchers.notNullValue())
                .header("Date",Matchers.notNullValue())
                .body("skills[0]",Matchers.hasItem("Selenium"),
                        "[0].skills",Matchers.hasItems("Selenium","Java"));
    }
}
