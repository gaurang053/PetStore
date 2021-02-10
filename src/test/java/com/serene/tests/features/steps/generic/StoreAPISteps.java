package com.serene.tests.features.steps.generic;

import java.text.ParseException;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import com.serene.tests.features.pojo.store.StoreInfo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class StoreAPISteps {

	@Step
	public StoreInfo createStoreClass(List<String> strStoreData) {
		StoreInfo storeInfo = new StoreInfo(Integer.parseInt(strStoreData.get(0)),
				Integer.parseInt(strStoreData.get(1)), Integer.parseInt(strStoreData.get(2)), strStoreData.get(3),
				strStoreData.get(4), Boolean.parseBoolean(strStoreData.get(5)));
		return storeInfo;
	}

	public Response createOrderRequest(String url, StoreInfo storeInfo) {
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder(url, "application/json", storeInfo);
		RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
		requestSpec = RestAssured.given().spec(requestSpec);
		Response res = requestSpec.when().post();
		return res;

	}

	@Step
	public StoreInfo validatePostStatusAndReturnResponse(Response res) {
		StoreInfo storeInfo = storeResponseDeSerialization(res);
		Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
		return storeInfo;
	}

	private StoreInfo storeResponseDeSerialization(Response res) {
		return res.as(StoreInfo.class);
	}

	public void compareStoreInfo(SoftAssertions softAssertion, StoreInfo expected, StoreInfo actual) {
		softAssertion.assertThat(expected.getId()).isEqualTo(actual.getId());
		softAssertion.assertThat(expected.getPetId()).isEqualTo(actual.getPetId());
		softAssertion.assertThat(expected.getQuantity()).isEqualTo(actual.getQuantity());
		String expectedShippedDate = strDateFormat(expected.getShipDate(), "yyyy-MM-dd'T'HH:mm",false);
		String actualShippedDate = strDateFormat(actual.getShipDate(), "yyyy-MM-dd'T'HH:mm",true);
		softAssertion.assertThat(expectedShippedDate).isEqualTo(actualShippedDate);
		softAssertion.assertThat(expected.getStatus()).isEqualTo(actual.getStatus());
		softAssertion.assertThat(expected.getComplete()).isEqualTo(actual.getComplete());
	}

	public String strDateFormat(String date, String format, boolean isUTCTime) {
		String returnDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			if (isUTCTime) {
				dateFormat.setTimeZone(TimeZone.GMT_ZONE);
			}
			returnDate = dateFormat.parse(date).toString();

		} catch (ParseException exception) {
			System.out.println("Failed to parse date > " + exception.getMessage());
		}
		return returnDate;
	}

	public StoreInfo fetchOrderInfoById(String orderUrl, String orderId) {
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder(orderUrl + "/" + orderId, "application/json", null);
		RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
		requestSpec = RestAssured.given().spec(requestSpec);
		Response res = requestSpec.when().get();
		StoreInfo expectedResponse = res.as(StoreInfo.class);
		Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
		return expectedResponse;
	}

	public void deleteOrderInfoById(String orderUrl, String orderId) {
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder(orderUrl + "/" + orderId, "application/json", null);
		RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
		requestSpec = RestAssured.given().spec(requestSpec);
		Response res = requestSpec.when().delete();
		Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
		res = requestSpec.when().get();
		Assert.assertEquals("Validate order removed!", 404, res.getStatusCode());
	}
	public Response fetchInventoryData(String inventoryURL) {
		APIRequestBuilder apiRequestBuilder = new APIRequestBuilder(inventoryURL, "application/json", null);
		RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
		requestSpec = RestAssured.given().spec(requestSpec);
		Response res = requestSpec.when().get();
		return res;
	}
}
