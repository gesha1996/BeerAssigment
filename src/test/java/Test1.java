import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


public class Test1 {


    @Test
    public void getAllBeers() {
        RestAssured.baseURI = "https://api.punkapi.com/v2/beers";
        Response response = given().contentType(ContentType.JSON).when().get();
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> listOfBeers = jsonPath.get();
        for (Map<String, Object> a : listOfBeers) {
            Assert.assertTrue(a.containsKey("id"));
            Assert.assertTrue(a.containsKey("name"));
            Assert.assertTrue(a.containsKey("description"));
            Assert.assertTrue(a.containsKey("abv"));
        }


    }

    @Test
    public void beforeDate() throws ParseException {
        String date = "04/2009";
        RestAssured.baseURI = "https://api.punkapi.com/v2/";
        RestAssured.basePath = "beers?brewed_before=<" + date + ">";
        Response response = given().contentType(ContentType.JSON).when().get();
        response.then().statusCode(200);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        Date date1 = sdf.parse(date);
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> listOfBeers = jsonPath.get();
        for (Map<String, Object> a : listOfBeers) {
            Assert.assertTrue(a.containsKey("id"));
            Assert.assertTrue(a.containsKey("name"));
            Assert.assertTrue(a.containsKey("description"));
            Assert.assertTrue(a.containsKey("abv"));
            Date date2 = sdf.parse(a.get("first_brewed").toString());
            if (date1.compareTo(date2) >= 0) {
                Map<String, String> brewedBeforeDate = new HashMap<String, String>();
                brewedBeforeDate.put(a.get("name").toString(), a.get("first_brewed").toString());
                System.out.println(brewedBeforeDate);
            }

        }


    }


}
