Feature: Pet Information -

	Owner of pet shope can maintain the pet store information 

	Scenario Outline:  As a owner I would like add, update, delete and serach pet information
    Given As a owner I would add new pet "<petData>" to the store with url "<url>"
    When I add new pet, it shoud be avilable to serach with pet by ID "<petId>"
    Then I update upload the an image using "<petId>" with "<image>"
    And I can delete the pet profile by id "<petId>" 
 
  Examples: Valid
  			|url|petData|petId|image|
		  	|/pet|10,0,dog,doggie,\\src\test\\resources\\image\\download.jpg,0,test,available|10|\\src\test\\resources\\image\\download.jpg|