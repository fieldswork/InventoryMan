@addWarehouse
Feature: Add warehouse
    Scenario Outline: Adding warehouse
        Given I am on the Add Warehouse page
        When I enter a "<name>" in the warehouse name field
        And I enter a "<capacity>" in the warehouse capacity field
        And I click the Create button to submit the warehouse
        Then I should be redirected to the Warehouses page if the warehouse is valid
        And I should see the warehouse "<name>" in the Warehouses page
        And I should see the capacity "<capacity>" in the Warehouses page

    Examples:
        |name                   |   capacity        |
        |validtest              |   10              |
        |validtest2             |   100             |