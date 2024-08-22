@addingWarehouseDataValidation
Feature: Validate warehouse data
    Scenario Outline: Entering invalid capacity to create an warehouse
        Given I am on the Add Warehouse page
        When I enter a "invalidTest" in the warehouse name field
        And I enter a "<capacity>" in the warehouse capacity field
        And I click the Create button to submit the warehouse
        Then The "Capacity must be positive." message should be displayed when entering invalid capacity
   

    Examples:
        |   capacity        |
        |   0               |
        |   -1              |