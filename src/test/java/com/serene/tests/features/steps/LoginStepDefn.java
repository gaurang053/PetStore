package com.serene.tests.features.steps;

import java.util.List;

import org.junit.runner.RunWith;

import com.serene.tests.features.Users.UserInfo;

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
public class LoginStepDefn {
	
	
	private Response res = null; //Response
    private JsonPath jp = null; //JsonPath
    private RequestSpecification requestSpec = null;
    private UserInfo newUser = null;
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
	
	@Given("^I provide login information \"(.*)\"$")
	public void i_provide_login_information(List<String> userInfo) {
		newUser = loginAPI.createUserClass(userInfo);
		
	}


	@When("^I send request to crete user$")
	public void i_send_request_to_crete_user() {
	    // Write code here that turns the phrase above into concrete actions
		res =  loginAPI.createAndPostUserRequest(newUser);
	}

	@Then("^Create user is successful$")
	public void create_user_is_successful() {
	    // Write code here that turns the phrase above into concrete actions
		loginAPI.validateCreateUserResponse(res);
	}
	
	@Then("^Change the First name to \"([^\"]*)\"$")
	public void change_the_First_name_to(String firstName){
		newUser.setFirstName(firstName);
		res =  loginAPI.createAndPostUserRequest(newUser);
		loginAPI.validateUpdateUserResponse(res);
	    
	}


	@Then("^Change the Last name to \"([^\"]*)\"$")
	public void change_the_Last_name_to(String lastName){
		newUser.setLastName(lastName);
		res =  loginAPI.createAndPostUserRequest(newUser);
		loginAPI.validateUpdateUserResponse(res);
	}

	@Then("^Change the Email to \"([^\"]*)\"$")
	public void change_the_Email_to(String email){
		newUser.setEmail(email);
		res =  loginAPI.createAndPostUserRequest(newUser);
		loginAPI.validateUpdateUserResponse(res);
	}

	@Then("^Change password to \"([^\"]*)\"$")
	public void change_password_to(String password){
		newUser.setPassword(password);
		res =  loginAPI.createAndPostUserRequest(newUser);
		loginAPI.validateUpdateUserResponse(res);
	}

	@Then("^Change phone number to \"([^\"]*)\"$")
	public void change_mobile_number_to(String phoneNumber){
		newUser.setPhone(phoneNumber);
		res =  loginAPI.createAndPostUserRequest(newUser);
		loginAPI.validateUpdateUserResponse(res);
	}

}
