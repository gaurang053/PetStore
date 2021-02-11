Feature: Pet - Everything about Pets

	Pet - Everything about Pets 

	Scenario Outline:  As a Store Owner, I would like add new pet profile, upload pet image and delete pet profile
    Given As a owner I would add new pet "<petData>" to the store with url "<url>"
    When I add new pet, it shoud be avilable to serach with pet by ID "<petId>"
    Then I upload a pet image "<image>" by "<petId>" 
    And I can delete the pet profile by id "<petId>" 
 
  Examples: Valid
  			|url|petData|petId|image|
		  	|/pet|10,0,dog,doggie,\\src\test\\resources\\image\\download.jpg,0,test,available|10|\\src\test\\resources\\image\\download.jpg|
	
	
	Scenario Outline:  As a Store Owner, I would like add update pet profile, view pet info by status or create new pet profile with form data
    Given As a Shop owner, I can create new pet "<petData>" profile information
    When Once, the profile created, I can update the Pet data "<updatePetInfo>" and Validate
    Then I can view pet info by status and validate if updated pet profile with "<petStatus>" exists
    Then Update a pet in the store with form data "<petFormData>"
        
  Examples: Valid
  			|petData|updatePetInfo|petStatus|petFormData|
		  	|100,100,cat,cat123,cat.jpg,100,test,available|100,200,mouse,mouse123,mouse.jpg,200,mouse,available|available&pending&sold,100|100,mouseTocat,sold|
  