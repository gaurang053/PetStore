package com.serene.tests.features.steps.user;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.jruby.ir.operands.Array;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.serene.tests.features.pojo.users.UserInfo;
import com.serene.tests.features.pojo.users.UserResponse;
import com.serene.tests.features.steps.generic.APIRequestBuilder;
import com.serene.tests.features.steps.generic.LoginAPISteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

@RunWith(SerenityRunner.class)
public class CreateMultiUserStepDefin {
	private Response res = null; // Response
	private JsonPath jp = null; // JsonPath
	private RequestSpecification requestSpec = null;
	private UserInfo user1 = null;
	private UserInfo user2 = null;
	private List<UserInfo> userArray = null;

	
	private SoftAssertions softAssertion = null;
	@Before
	public void setup() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		this.softAssertion = new SoftAssertions();
	}

	@After
	public void tearDown() {
		this.softAssertion.assertAll();
		RestAssured.reset();
	}

	@Steps
	LoginAPISteps loginAPI;

	@Given("^I provide user information \"([^\"]*)\" and \"([^\"]*)\" to create array$")
	public void i_provide_user_information_and_to_create_array(List<String>  userData1, List<String>  userData2){
		user1 = loginAPI.createUserClass(userData1); 
		user2 = loginAPI.createUserClass(userData2); 
		UserInfo[] userArray = {user1,user2};
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder("/user/createWithArray","application/json",userArray);
		this.requestSpec = apiRequestBuilder.getRequestSpecification();
		this.requestSpec = RestAssured.given().spec(this.requestSpec);
		
	}


	@When("^I send request to crete user with Array$")
	public void i_send_request_to_crete_user_with_Array_list() {
		this.res = this.requestSpec.when().post();
		
	}

	@Then("^Create user is successful with Array$")
	public void create_user_is_successful_with_Array_list() {
		loginAPI.validateCreateUserResponse(this.res);
	}
	
	@And("^Allow to fetch the list of user details by \"([^\"]*)\"$")
	public void allow_to_fetch_the_list_of_user_details_by(String userName) throws Exception {
		UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
		UserInfo user = this.user1;
		if("testuser2".equalsIgnoreCase(userName)) {
			user = this.user2;
		}else if("testuser3".equalsIgnoreCase(userName)) {
			user = userArray.get(0);
		}else if("testuser4".equalsIgnoreCase(userName)) {
			user = userArray.get(1);;
		}
		loginAPI.compareUserInfo(softAssertion, user,actualUser);
	}
	
	@Given("^I provide user information \"([^\"]*)\" and \"([^\"]*)\" to create list$")
	public void i_provide_user_information_and_to_create_list(List<String>  userData1, List<String>  userData2){
		this.userArray = new ArrayList<>();
		this.userArray.add(loginAPI.createUserClass(userData1));
		this.userArray.add(loginAPI.createUserClass(userData2));
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder("/user/createWithArray","application/json",this.userArray);
		this.requestSpec = apiRequestBuilder.getRequestSpecification();
		this.requestSpec = RestAssured.given().spec(this.requestSpec);
	}
	
	@When("^I send request to crete user with list$")
	public void i_send_request_to_crete_user_with_list(){
		this.res = this.requestSpec.when().post();
	}


	@Then("^Create user is successful with list$")
	public void create_user_is_successful_with_list() {
		loginAPI.validateCreateUserResponse(this.res);
	}



}
