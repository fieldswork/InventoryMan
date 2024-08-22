@addingItemDataValidation
Feature: Validate item data
    Scenario Outline: Entering invalid quantity and size to create an item
        Given I am on the Add Item page
        When I enter "invalidItem" in the item name field
        And I enter "ValidDescription" in the item description field
        And I enter "<quantity>" in the item quantity field
        And I enter "<size>" in the item size field
        And I select "invalidtest" from the warehouse dropdown
        And I click the Create button to submit the item
        Then The "Quantity and size must be at least 1." message should be displayed

    Examples:
        |   quantity  |   size    |
        |   0         |   1       |
        |   1         |   0       |
        |   -1        |   1       |
        |   1         |   -1      |
        |   -1        |   -1      |
     