@addWarehouse
Feature: Add warehouse
    Scenario Outline: Adding a new warehouse
        Given I have navigated to the Add Warehouse page
        When I input the warehouse name "<name>"
        And I provide the warehouse capacity "<capacity>"
        And I press the Create button to submit the warehouse form
        Then I should be taken to the Warehouses overview page if the warehouse details are valid
        And I should see the new warehouse "<name>" listed on the Warehouses page
        And I should find the capacity "<capacity>" shown on the Warehouses page

    Examples:
        | name       | capacity |
        | validtest  | 10       |
        | validtest2 | 100      |
        | invalidtest| 1000     |
