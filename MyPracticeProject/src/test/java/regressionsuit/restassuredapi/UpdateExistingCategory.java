package regressionsuit.restassuredapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import regressionsuit.testngproject.TestData;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Random;

public class UpdateExistingCategory {
    TestData db;
    List<Integer> catIds;
    int randomCatId;
    String responseBody;
    @BeforeClass
    public void setUp(){
        db=new TestData();
        RestAssured.baseURI=db.api_host;
        RestAssured.port=db.api_port;
        RestAssured.authentication=RestAssured.basic(db.api_username,db.api_password);
    }
    @Test
    public void getAllCategoriesName(){
        String responseBody=given().when().get("/categories").then()
                .assertThat().statusCode(200).extract().response().asString();
        JsonPath jsonPath=new JsonPath(responseBody);
        catIds=jsonPath.getList("id");
    }
    @Test
    public void getRandomCategory(){
        randomCatId=catIds.get(new Random().nextInt(catIds.size()));
        responseBody=given().when().get("/category/"+randomCatId).then()
                .assertThat().statusCode(200).and().extract().response().prettyPrint();
    }
    @Test
    public void updateCategoryInfo(){
        String newCateName=db.categoryName;
        System.out.println("Value to be updated: "+newCateName);
        JSONObject jsonObject=new JSONObject(responseBody);
        jsonObject.put("catName",newCateName);
        given().contentType(ContentType.JSON).and().body(jsonObject.toString()).when().put("/category/"+randomCatId)
                .then().assertThat().statusCode(204);

        /*given().contentType(ContentType.JSON)
                .and().body(PayloadUtility.updatePayload(responseBody,"catName",newCateName))
                .when().put("/category/"+randomCatId).then().assertThat().statusCode(204).extract().response();
*/
        Response getResponse= given().when().get("/category/"+randomCatId);
        Assert.assertEquals(getResponse.jsonPath().getString("catName"),newCateName);
        System.out.println("After Update: ");
       getResponse.prettyPrint();
    }
}
