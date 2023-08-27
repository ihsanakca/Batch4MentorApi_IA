package apiTests;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class TestBase {
    @BeforeClass
    public void setUpClass(){
        baseURI ="https://www.krafttechexlab.com/sw/api/v1";
    }
}
