package com.serene.tests.features.steps.stepDefinition;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.serene.tests.features.pojo.petProfile.Pet;
import com.serene.tests.features.pojo.petProfile.PetAPIResponse;
import com.serene.tests.features.steps.generic.PetAPISteps;
import com.serene.tests.features.steps.generic.StepDefn;
import com.serene.tests.features.steps.generic.StoreAPISteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

@RunWith(SerenityRunner.class)
public class PetProfileStepDefn implements StepDefn{

	private Response res = null; // Response
	private SoftAssertions softAssertion = null;
	private static String petUrl = null;
	private Pet petInfo = null;
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
	PetAPISteps petAPISteps;
	
	@Given("^As a owner I would add new pet \"([^\"]*)\" to the store with url \"([^\"]*)\"$")
	public void as_a_owner_I_would_add_new_pet_to_the_store_with_url(List<String> petData, String url) {
		PetProfileStepDefn.petUrl = url;
		this.petInfo=petAPISteps.createStoreClass(petData);
		this.res = petAPISteps.createPetRequest(PetProfileStepDefn.petUrl, this.petInfo);
		Pet petResponse = petAPISteps.validatePetInfoIsAdded(this.res);
		petAPISteps.comparePetInfo(softAssertion, this.petInfo, petResponse);
	}


	@When("^I add new pet, it shoud be avilable to serach with pet by ID \"([^\"]*)\"$")
	public void i_add_new_pet_it_shoud_be_avilable_to_serach_with_pet_by_ID(String petId) {
		Pet petResponse=petAPISteps.fetchPetInfoById(PetProfileStepDefn.petUrl,petId);
		petAPISteps.comparePetInfo(softAssertion, this.petInfo, petResponse);
	}

	@Then("^I update upload the an image using \"([^\"]*)\" with \"([^\"]*)\"$")
	public void i_update_upload_the_an_image_using(String petId,String image) {
		String dir = System.getProperty("user.dir");
		PetAPIResponse expectedResponse = petAPISteps.uploadImageOfPetById(dir+image, PetProfileStepDefn.petUrl,petId);
		Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());	
		Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
		Assert.assertNotNull("Message field in response is not empty", expectedResponse.getMessage());
	}
	
	@Then("^I can delete the pet profile by id \"([^\"]*)\"$")
	public void i_can_delete_the_pet_profile_by_id(String petId)  {
		PetAPIResponse expectedResponse=petAPISteps.deletePetInfoById(PetProfileStepDefn.petUrl,petId);
		Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());	
		Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
		Assert.assertEquals("Message return id",petId, expectedResponse.getMessage());
	}

}
