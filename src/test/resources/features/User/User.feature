Feature: User

	I login to application I should be able to create new user and update existing user information

	Scenario Outline: Create and Update user
    Given I provide login information "<userData>" 
    When I send request to crete user
    Then Create user is successful
    Then Change the First name to "<firstname>"
    Then Change the Last name to "<lastname>"
    Then Change the Email to "<email>"
    Then Change password to "<changepassword>"
    Then Change phone number to "<phoneNumber>"
  
  Examples: Valid
  			|userData|firstname|lastname|email|changepassword|phoneNumber|
		  	|0,testuser,firstname,lastname,testuser@gmail.com,test123,123456789,0|pet|store|petstore@gmail.com|test1234|987654321|
    
    