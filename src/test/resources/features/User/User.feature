Feature: User

	I login to application and I should be able to create new user, update existing user information and delete the user

	Scenario Outline: Create, Update, Delete and Fetch user information
    Given I provide login information "<userData>" 
    When I send request to crete user
    Then Create user is successful
    And Allow to fetch the user details by "<userName>" 
    And Allow to updated the First name "<firstname>" where Username "<userName>"
    And Allow to updated the Last name "<lastname>" where Username "<userName>"
    And Allow to updated the Email "<email>" where Username "<userName>"
    And Allow to updated password "<changepassword>" where Username "<userName>"
    And Allow to updated phone number "<phoneNumber>" where Username "<userName>"
    And Delete the user with username "<userName>"
  
  Examples: Valid
  			|userData|firstname|lastname|email|changepassword|phoneNumber|userName|
		  	|20,testuser,firstname,lastname,testuser@gmail.com,test123,123456789,0|pet|store|petstore@gmail.com|test1234|987654321|testuser|
    
    