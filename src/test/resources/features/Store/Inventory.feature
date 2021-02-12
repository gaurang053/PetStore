Feature: Store - Access to Petstore order

	Store - Check Inventory status

	Scenario Outline:  As a Store Owner, I would like to check my inventory
    Given I hit the "<url>"
    When Request should submit and Positive API response should received "<response>"
    Then Inventory data should display 
 
  Examples: Valid
  			| url								| response	|
		  	| /store/inventory	| 200				|