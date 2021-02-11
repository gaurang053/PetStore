package com.serene.tests.features.steps.stepDefinition;

import java.util.List;

import org.junit.runner.RunWith;

import com.serene.tests.features.pojo.users.UserInfo;
import com.serene.tests.features.steps.generic.LoginAPISteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

@RunWith(SerenityRunner.class)
public class LoginStepDefn{
	public Response res = null; //Response
    public JsonPath jp = null; //JsonPath
    public RequestSpecification requestSpec = null;
    public UserInfo newUser = null;
    
    @Before
	public void setup()
	{
    	RestAssured.baseURI = "https://petstore.swagger.io/v2";
	}
	
	@After
	public void tearDown()
	{
        RestAssured.reset();
	}

	@Steps
	LoginAPISteps loginAPI;
	
	@Given("^I provide login credentials \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_provide_login_credentials_and(String username, String password){
		Serenity.setSessionVariable("username").to(username);
		Serenity.setSessionVariable("password").to(password);
		requestSpec = loginAPI.givenUserDetails(username,password);
	}


	@When("^I send request to login$")
	public void i_send_request_to_login() {
	    // Write code here that turns the phrase above into concrete actions
		res = loginAPI.postLoginRequest(requestSpec);
	}

	@Then("^login failed$")
	public void login_failed() {
		String username = Serenity.sessionVariableCalled("username").toString();
		String password = Serenity.sessionVariableCalled("password").toString();
		loginAPI.verifyLoginFailure(res, username,password);
	}

	@Then("^login is successful$")
	public void login_is_successful() {
		String username = Serenity.sessionVariableCalled("username").toString();
		String password = Serenity.sessionVariableCalled("password").toString();
		loginAPI.verifyLoginSuccess(res, username,password);
	}
	
	@Then("^logout is successful$")
	public void logout_is_successful() {
	    // Write code here that turns the phrase above into concrete actions
		String username = Serenity.sessionVariableCalled("username").toString();
		String password = Serenity.sessionVariableCalled("password").toString();
		loginAPI.verifyLogoutSuccess(res, username,password);
	}
	


}
