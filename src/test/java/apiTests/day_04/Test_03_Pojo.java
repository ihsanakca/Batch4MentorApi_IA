package apiTests.day_04;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojoTemplates.Education;
import pojoTemplates.Experience;
import pojoTemplates.KraftUser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;

public class Test_03_Pojo {

    @BeforeClass
    public void setUpClass(){
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void pojoTestKraft(){
        /**
         * TASK
         * base url = https://www.krafttechexlab.com/sw/api/v1
         * end point /allusers/getbyid/{id}
         * id parameter value is 62
         * send the GET request
         * then status code should be 200
         * get all data into a custom class (POJO)
         */


        Response response = RestAssured.given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),200);

       KraftUser[] kraftUsers= response.as(KraftUser[].class);
        System.out.println("kraftUsers.length = " + kraftUsers.length);

        Integer id = kraftUsers[0].getId();
        Assert.assertEquals(id,62);

        System.out.println("kraftUsers[0].getName() = " + kraftUsers[0].getName());

        //skillslerden ikincisini alalım
        List<String> skills = kraftUsers[0].getSkills();
        String skills1 = skills.get(1);
        System.out.println("skills1 = " + skills1);

        //üçüncü educationın school bilgisini alalım
        List<Education> education = kraftUsers[0].getEducation();
        String school = education.get(2).getSchool();
        System.out.println("school = " + school);

        //ikinci experience recordunun companyi alanını alalım
        List<Experience> experience = kraftUsers[0].getExperience();
        String company = experience.get(1).getCompany();
        System.out.println("company = " + company);

    }
    @Test
    public void test1(){
        Faker faker = new Faker();

        String name = faker.name().name(); // Rastgele bir isim oluşturur
        String email=faker.internet().emailAddress();
        String password=faker.internet().password(8,16,true,true,true);


        String body2 = """
            {
                "name": "%s",
                "email": "%s",
                "password": "%s",
                "about": "hhhh",
                "terms":"10"
            }
            """.formatted(name,email,password);

        String body = """
                {
                "name": "Water Bottle,
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 12,
                "category_id": 3
                }
                """;




        String body1="{\n" +
                "                         \"name\": \"aFm\",\n" +
                "                         \"email\": \"devasa66@krafttechexlab.com\",\n" +
                "                         \"password\": \"123467\",\n" +
                "                         \"about\": \"About Me\",\n" +
                "                         \"terms\": \"10\"\n" +
                "                      }";


        Response response = RestAssured.given().accept(ContentType.JSON)
                .and()
                .body(body2)
                .when()
                .post("/allusers/register");

        System.out.println("response.statusCode() = " + response.statusCode());

        response.prettyPrint();


        String xpathString = "//input[./ancestor::div[contains(@class,\"{0}\")]//label\"{2}\"[contains(text(), \"{1}\")]]";

        String format = MessageFormat.format(xpathString, "oxd-input-field", "Username", "Ahmet");

        System.out.println("format = " + format);


    }
}


