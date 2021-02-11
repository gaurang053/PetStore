package com.serene.tests.features.steps.stepDefinition;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.serene.tests.features.pojo.petProfile.Pet;
import com.serene.tests.features.pojo.petProfile.PetAPIResponse;
import com.serene.tests.features.steps.generic.PetAPISteps;
import com.serene.tests.features.steps.generic.StepDefn;
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
public class PetProfileStepDefn implements StepDefn {

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
		this.petInfo = petAPISteps.createStoreClass(petData);
		this.res = petAPISteps.createPetRequest(PetProfileStepDefn.petUrl, this.petInfo);
		Pet petResponse = petAPISteps.validatePetInfoIsAdded(this.res);
		petAPISteps.comparePetInfo(softAssertion, this.petInfo, petResponse);
	}

	@When("^I add new pet, it shoud be avilable to serach with pet by ID \"([^\"]*)\"$")
	public void i_add_new_pet_it_shoud_be_avilable_to_serach_with_pet_by_ID(String petId) {
		Pet petResponse = petAPISteps.fetchPetInfoById(PetProfileStepDefn.petUrl, petId);
		petAPISteps.comparePetInfo(softAssertion, this.petInfo, petResponse);
	}

	@Then("^I upload a pet image \"([^\"]*)\" by \"([^\"]*)\"$")
	public void i_upload_a_pet_image_using(String image, String petId) {
		String dir = System.getProperty("user.dir");
		PetAPIResponse expectedResponse = petAPISteps.uploadImageOfPetById(dir + image, PetProfileStepDefn.petUrl,
				petId);
		Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());
		Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
		Assert.assertNotNull("Message field in response is not empty", expectedResponse.getMessage());
	}

	@Then("^I can delete the pet profile by id \"([^\"]*)\"$")
	public void i_can_delete_the_pet_profile_by_id(String petId) {
		PetAPIResponse expectedResponse = petAPISteps.deletePetInfoById(PetProfileStepDefn.petUrl, petId);
		Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());
		Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
		Assert.assertEquals("Message return id", petId, expectedResponse.getMessage());
	}

	@Given("^As a Shop owner, I can create new pet \"([^\"]*)\" profile information$")
	public void as_a_Shop_owner_I_can_create_new_pet_profile_using_form_data(List<String> petData) {
		this.petInfo = petAPISteps.createStoreClass(petData);
		this.res = petAPISteps.createPetRequest(PetProfileStepDefn.petUrl, this.petInfo);
	}

	@When("^Once, the profile created, I can update the Pet data \"([^\"]*)\" and Validate$")
	public void once_the_profile_created_I_can_update_the_Pet_data_and_Validate(List<String> petDataToBeUpdated) {
		Pet toBeUpdated = petAPISteps.createStoreClass(petDataToBeUpdated);
		Pet actualResponse = petAPISteps.updatePetRequest(PetProfileStepDefn.petUrl, toBeUpdated);
		petAPISteps.comparePetInfo(softAssertion, toBeUpdated, actualResponse);
	}

	@Then("^I can view pet info by status and validate if updated pet profile with \"([^\"]*)\" exists$")
	public void i_can_view_pet_info_by_status_and_validate_if_updated_pet_profile_exists(List<String> petStatus) {
		String param = petStatus.get(0).replace("&", "&status=");
		Pet[] petResponse = petAPISteps
				.findPetInfoByStatus(PetProfileStepDefn.petUrl + "/findByStatus?status=" + param);
		for (Pet petProfile : petResponse) {
			if (petProfile.getId().equals(Integer.parseInt(petStatus.get(0)))) {
				Assert.assertEquals("Verify Pet Status!", petProfile.getStatus(), petStatus.get(1));
			}
		}

	}

	@Then("^Update a pet in the store with form data \"([^\"]*)\"$")
	public void update_a_pet_in_the_store_with_form_data(List<String> petFormData) {
		String param = "name=" + petFormData.get(1) + "&status=" + petFormData.get(2);
		PetAPIResponse expectedResponse = petAPISteps
				.updatePetDataWithFormData(PetProfileStepDefn.petUrl + "/" + petFormData.get(0), param);
		Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());
		Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
		Assert.assertEquals("Message return id", petFormData.get(0), expectedResponse.getMessage());
	}

}
