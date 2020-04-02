package DataDrivenTesting;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddNewEmployeesDataProviderModel {

// Send body in JSON format to create a new record - Sample JSON request  {"name":"John123","Salary":"123","age":"23"}
	
	@Test(dataProvider="empdataprovider")
	void postNewEmployees(String ename,  String esal, String eage )
	{
		//Unique/Random User Name string for new customer Registration
		String randomString1= RandomStringUtils.randomAlphanumeric(6);
		
		//END POINT BASE URI
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";  // URI
	
		RequestSpecification httpRequest =RestAssured.given();
		
		//building request PAYLOAD in JSON Format
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("name",ename);
		requestParams.put("salary", esal);		
		requestParams.put("age", eage);
		
		// Add request HEADER
		httpRequest.header("Content-Type", "application/json");
		
		//Add the above JSON payload to the BODYof the request
		httpRequest.body(requestParams.toJSONString());
		
		// POST the  REQUEST
		Response response = httpRequest.post("/create");
		
		// PRINT Response  CONVERT output of JSON format to STRING
		String responseBody = response.body().asString();
		System.out.println("Response Body is : " + responseBody);
		
		//Validate response BODY
		Assert.assertEquals(responseBody.contains(ename), true);
		Assert.assertEquals(responseBody.contains(esal), true);
		Assert.assertEquals(responseBody.contains(eage), true);
		
		//Validate Response status code for 200
		int statusCode = response.getStatusCode();
		System.out.println("Status code is : " + statusCode);
		Assert.assertEquals(statusCode, 200);
	}


// Data PROVIDER TO THE ABOve meTHod	
	@DataProvider (name="empdataprovider")
	String [][] getEmpData()
	{
		String empdata[][]= {{"abc123","30000","30"}, {"abc456","30000","30"}, {"abc789","30000","30"}};
		return(empdata);
		
	}
	
}
