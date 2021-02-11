Feature: User - Operations about users - Login & Logout

	As a End User, I can login and logout to the application using API

	Scenario Outline: As a End User, Unauthorized user can not login to the application using API
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login failed
 
  Examples: Invalid
  			|username|password|
		  	|test-user|abc122|
		  	
	Scenario Outline: As a End User, Valid user can login and logout to the application using API
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login is successful
    Then logout is successful
  
  Examples: Valid
  			|username|password|
		  	|test|test@123| 