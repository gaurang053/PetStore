Feature: User - Operations about users - Login & Logout

	As a End-User, I can login and logout to the application using API

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