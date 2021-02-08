Feature: Login

	I login to application I should be able to find my user credentials

	Scenario Outline: Unauthorized Login
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login failed
 
  Examples: Invalid
  			|username|password|
		  	|test-user|abc122|
		  	
	Scenario Outline: Successful Login and Logout
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login is successful
    Then logout is successful
  Examples: Valid
  			|username|password|
		  	|test|test@123|
		  	
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
    
    