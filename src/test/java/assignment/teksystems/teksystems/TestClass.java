package assignment.teksystems.teksystems;

import java.util.List;
import java.util.Scanner;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestClass {

	public static void main(String[] args) {

		boolean flag = true;
		Scanner scan = new Scanner(System.in);

		while(flag) {
			System.out.println("Please select your option quit/state abbrivation(Eg:Alabama->AL) : ");
			String input = scan.nextLine();

			if(!(input.equalsIgnoreCase("quit")) && input !=null && (!input.isEmpty())) {
				getBaseURI();
				RequestSpecification httpRequest = RestAssured.given();
				Response response = httpRequest.get(input);
				JsonPath jsonPathEvaluator = response.jsonPath();

				try {
					Assert.assertEquals(response.getStatusCode(), 200);
					if(input.equalsIgnoreCase("all")) {
						List<StatePojo> allStates = jsonPathEvaluator.getList("RestResponse.result", StatePojo.class);
						for(StatePojo state : allStates)
						{
							System.out.println("Largest City for "+input+" : " + state.largest_city);
							System.out.println("Capital for "+input+" : " + state.capital);
						}
					} else {
						StatePojo allStates = jsonPathEvaluator.getObject("RestResponse.result", StatePojo.class);
						System.out.println("Largest City for "+input+" : "+ allStates.largest_city);
						System.out.println("Capital for "+input+" : " + allStates.capital);
					}
				} catch(NullPointerException e) {

					System.out.println("Seems the option is wrong, Please provide right option");
				}
				catch(Exception e) {

					System.out.println("Seems something went wrong, Please provide right option");
				}

			} else if(input == null || input.isEmpty()) {
				System.out.println("Please don't leave empty, select right value");
			} else if(input.equalsIgnoreCase("quit")){
				flag=false;
				System.out.println("********************Execution completed********************");
			}
		}
	}

	public static void getBaseURI() {
		RestAssured.baseURI = "http://services.groupkt.com/state/get/USA/";
	}

}
