package com.serene.tests.features.steps.generic;

import java.util.List;

import org.junit.Assert;

import com.serene.tests.features.Users.UserInfo;
import com.serene.tests.features.Users.UserResponse;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class LoginAPISteps {
	
	@Step
	public RequestSpecification givenUserDetails(String username, String password) {
		String urlPath = "/user/login?username=" + username + "&password=" + password;
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder(urlPath,"application/json",null);
		return RestAssured.given().spec(apiRequestBuilder.getRequestSpecification());

	}

	@Step
	public Response postLoginRequest(RequestSpecification requestSpec) {
		Response res = requestSpec.when().get();
		return res;
	}

	@Step
	public void verifyLoginSuccess(Response res, String username, String password) {
		JsonPath jp = res.jsonPath();
		Assert.assertEquals("Status Check Failed!", 200, res.getStatusCode());
		Assert.assertTrue(jp.get("message").toString().contains("logged in user session:"));
	}
	
	public void verifyLogoutSuccess(Response res, String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Step
	public void verifyLoginFailure(Response res, String username, String password) {
		JsonPath jp = res.jsonPath();
		Assert.assertEquals("Invalid user credentials allowed", 401, res.getStatusCode());
	}

	@Step
	public UserInfo createUserClass(List<String> userInfo) {
		UserInfo newUser = new UserInfo(Integer.parseInt(userInfo.get(0)), userInfo.get(1), userInfo.get(2),
				userInfo.get(3), userInfo.get(4), userInfo.get(5), userInfo.get(6),
				Integer.parseInt(userInfo.get(7)));
		return newUser;
		
	}
	
	@Step
	public Response createAndPostUserRequest(UserInfo newUser) {
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder("/user","application/json",newUser);
		RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
		requestSpec = RestAssured.given().spec(requestSpec);
		Response res = requestSpec.when().post();
		return res;
	}
	
	@Step
	public void validateCreateUserResponse(Response res) {
		JsonPath jp = res.jsonPath();
		UserResponse userResponse = userResponseDeSerialization(res);
		Assert.assertEquals("Status Check Failed!", 200, res.getStatusCode());
		Assert.assertEquals("Validate Status code in response ", "200", userResponse.getCode()+"");
		Assert.assertEquals("Type ", "unknown", userResponse.getType());
		System.out.println("User message > "+ userResponse.getMessage());
		
	}
	
	@Step
	public Response UpdateUserRequest(UserInfo newUser) {
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder("/user/"+newUser.getUsername(),"application/json",newUser);
		RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
		requestSpec = RestAssured.given().spec(requestSpec);
		Response res = requestSpec.when().post();
		return res;
	}
	
	@Step
	public void validateUpdateUserResponse(Response res) {
		validateCreateUserResponse(res);
		
	}
	@Step
	public UserResponse userResponseDeSerialization(Response res) {
		return res.as(UserResponse.class);
	}

	
}
