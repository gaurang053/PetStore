Feature: Pet - Everything about Pets

	Pet - Everything about Pets 

	Scenario Outline:  As a Shop owner, I would like add new pet profile, upload pet image and delete pet profile
    Given As a owner I would add new pet "<petData>" to the store with url "<url>"
    When I add new pet, it shoud be avilable to serach with pet by ID "<petId>"
    Then I upload a pet image "<image>" by "<petId>" 
    And I can delete the pet profile by id "<petId>" 
 
  Examples: Valid
  			|url|petData|petId|image|
		  	|/pet|10,0,dog,doggie,\\src\test\\resources\\image\\download.jpg,0,test,available|10|\\src\test\\resources\\image\\download.jpg|