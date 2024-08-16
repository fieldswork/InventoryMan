@createwarehouse
Feature: Create a warehouse
    Scenario: Making a warehouse
        Given I am on the main page
        When I click on the Add Warehouse button
        And the page loads
        And I enter the warehouse name "TEST"
        And I enter the warehouse capacity "123"
        And I click the Create button
        Then I should be redirected to "http://localhost:3000/warehouses"
        And I should see the warehouse "TEST" in the list
        And I should see the warehouse text "0/123 cubic feet utilized"