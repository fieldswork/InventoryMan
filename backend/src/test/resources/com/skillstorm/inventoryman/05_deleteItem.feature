@deleteItem
Feature: Delete Item
    Scenario Outline: Deleting items
        Given I am viewing items
        When I find the item "<item>" on the Items page
        And I click the Delete button
        And I click the OK button to confirm the deletion
        Then The item "<item>" should be deleted from the Items page

    Examples:
        |item                |
        |editeditem          | 
