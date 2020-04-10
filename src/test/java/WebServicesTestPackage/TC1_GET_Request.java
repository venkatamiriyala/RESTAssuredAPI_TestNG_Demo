package WebServicesTestPackage;
import org.junit.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC1_GET_Request {

	@Test // Simple GET request
	public void getWeatherDetails()
	{
		//END POINT BASE URI
		RestAssured.baseURI="http://restapi.demoqa.com/utilities/weather/city";  // URI
		
		// GET REQUEST
		RequestSpecification httpRequest =RestAssured.given();
		
		//sent request
		Response response = httpRequest.request(Method.GET, "/Sydney");     // GET Request
		
		//EXTRACT body FROM the Response
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		
		//EXTRACT STATUS CODE
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
				
		//Extract Status line
		String statusLine = response.getStatusLine();
		System.out.println(statusLine);
		
		// Validation
		Assert.assertEquals(String.valueOf(statusCode), "200");
	}
}
