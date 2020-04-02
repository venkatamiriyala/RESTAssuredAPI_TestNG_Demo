package WebServicesTestPackage;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC1_POST_Request {

	
	@Test // JSON POST  request
	public void registerUser()
	{
		//Unique/Random User Name string for new customer Registration
		String randomString1= RandomStringUtils.randomAlphanumeric(6);
		
		//END POINT BASE URI
		RestAssured.baseURI="http://restapi.demoqa.com/customer";  // URI
	
		RequestSpecification httpRequest =RestAssured.given();
		
		//building request PAYLOAD in JSON Format
		
		JSONObject requestParams = new JSONObject();
		
		requestParams.put("FirstName", "firstName"+randomString1);
		requestParams.put("LastName", "lastName"+randomString1);		
		requestParams.put("UserName", "usrtName"+randomString1);
		requestParams.put("Password", randomString1);
		requestParams.put("Email", randomString1+"@mail.com");
		
		// Add request HEADER
		httpRequest.header("Content-Type", "application/json");
		
		//Add the above JSON payload to the BODYof the request
		httpRequest.body(requestParams.toJSONString());
		
		// POST the  REQUEST
		Response response = httpRequest.post("/register");
	
		
		// PRINT Response  CONVERT output of JSON format to STRING
		System.out.println("Response Body is : " + response.body().asString());
		
		//Validate Response status code for PUT is 201
		int statusCode = response.getStatusCode();
		System.out.println("Status code is : " + statusCode);
		Assert.assertEquals(statusCode, 201);
	
		//Validate response SUCCESS CODE
		String successCode = response.jsonPath().getString("SuccessCode");
		Assert.assertEquals(successCode, "OPERATION_SUCCESS");
		
	
	}
	
	



}
