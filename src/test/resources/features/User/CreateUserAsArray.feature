Feature: User - Operations about users

	As a User Admin, I can create multiple user either using Array or List

	Scenario Outline: As a Aser Admin, I can create multiple user with Array
    Given I provide user information "<userData1>" and "<userData2>" to create array 
    When I send request to crete user with Array
    Then Create user is successful with Array
    And Allow to fetch the list of user details by "testuser1"
    And Allow to fetch the list of user details by "testuser2" 
    
   Examples: Valid
  			|userData1|userData2|
		  	|20,testuser1,firstname,lastname,testuser@gmail.com,test123,123456789,0|21,testuser2,firstname2,lastname2,testuser2@gmail.com,test2123,123456789,0|
		  	
	Scenario Outline: As a user admin, I can create multiple user with List
    Given I provide user information "<userData3>" and "<userData4>" to create list 
    When I send request to crete user with list
    Then Create user is successful with list
    And Allow to fetch the list of user details by "testuser3"
    And Allow to fetch the list of user details by "testuser4" 
    
   Examples: Valid
  			|userData3|userData4|
		  	|20,testuser3,firstname,lastname,testuser@gmail.com,test123,123456789,0|21,testuser4,firstname2,lastname2,testuser2@gmail.com,test2123,123456789,0|