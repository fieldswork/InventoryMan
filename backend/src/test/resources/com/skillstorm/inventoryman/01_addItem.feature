@addItem
Feature: Add item
    Scenario Outline: Adding items
        Given I am on the Add an Item page
        When I enter "<name>" in the added item name field
        And I enter "<description>" in the added item description field
        And I enter "<quantity>" in the added item quantity field
        And I enter "<size>" in the added item size field
        And I select "<warehouse>" from the warehouse dropdown for the added item
        And I click the Create button to submit the added item
        Then I should be redirected to the Warehouses page if the item is valid

    Examples:
        |name                   |   description        |  quantity    |   size    |   warehouse    |
        |validitem              |   validdescription   |  1           |   5       |   validtest    |
        |validitem2             |   validdescription2  |  2           |   10      |   validtest2   |
        