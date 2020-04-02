package DataDrivenTesting;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddNewEmployeesExcel {

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
	String [][] getEmpData() throws IOException
	{
		 String path = System.getProperty("user.dir")+"/src/test/java/DataDrivenTesting/empdata.xls";
		 
		 int rownum =XLUtils.getRowCount(path, "Sheet1");
		 int colcount =XLUtils.getCellCount(path, "Sheet1", 1);
		 	 
		//Static 2 dimensional array
		 String empdata[][] = new String[rownum][colcount];
		 
		 for(int i=1; i<=rownum; i++) {
			 
			 for(int j=0; j<colcount; j++) {
				 empdata[i-1][j] = XLUtils.getCellData(path, "Sheet1", i, j);
			 }
		 }
		 
		return(empdata);
		
	}
	
}
