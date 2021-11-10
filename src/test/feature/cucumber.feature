Feature: Spring PetClinic Test

Scenario: Go to LocalHost and AddOwner
	When Open LocalHost
	Then Click Find Owners
	And Navigate Add Owner
	Then Fill the blanks
	And Click Add Owner
	Then User successfully created
	
Scenario: Find User
	When User needs to be find
	Then Click Find Owners
	And Type Last name of User
	
Scenario: Update User
	When User needs to be updated
	Then Click Find Owners
	And Type Last name of User
	Then Click Edit Owner
	Then Update the Blanks
	And Click Update Owner

Scenario: Add Pet
	When User buys pet
	Then Click Add New Pet
	Then Fill Pets attributes
	And Click Add Pet

Scenario: Add Pet Visit
	When Pet need visits
	Then Click Add Visit
	Then Fill the Visit attributes
	And Click Add Visit
	