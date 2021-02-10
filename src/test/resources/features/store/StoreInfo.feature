Feature: Store Information 

	End User can find order, create new order and delete order also Store manager can see the inventories status 

	Scenario Outline:  As a user I would like to maintain my pet store order cart
    Given I would like to place an order for a  pet "<orderData>" with order url "<url>"
    When My order is placed, I would like to get the order information by "<orderid>"
    Then I would like to delete my order by "<orderid>" if I am not not happy with it 
 
  Examples: Valid
  			|url|orderData|orderid|
		  	|/store/order|1,1,1,2021-02-10T08:39:11.959Z,placed,true|1|
		  	
	Scenario Outline:  As a store owner, I would like to check my inventory
    Given I hit the "<url>"
    When Request should submit and Positive API response should received "<response>"
    Then Inventory data should display 
 
  Examples: Valid
  			|url|response|
		  	|/store/inventory|200|