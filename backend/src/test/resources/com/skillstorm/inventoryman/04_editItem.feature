@editItem
Feature: Edit Item
    Scenario Outline: Editing items
        Given I am on the Items page
        When I find the item "<oldName>" in the Items page
        And I click the Edit button to edit the item
        Then I should be redirected to the Edit Item page
        When I enter the new name "<newName>" in the item name field
        And I enter the new description "<newDescription>" in the item description field
        And I enter the new quantity "<newQuantity>" in the item quantity field
        And I enter the new size "<newSize>" in the item size field
        And I select the new item "<newWarehouse>" in the item warehouse dropdown
        And I click the Update button to submit the edited item
        Then I should be redirected to the Warehouses page if the edited item is valid
        And If I navigate to the Items page
        And I should see the edited item "<newName>" in the Items page

    Examples:
        |newName                |   newDescription     |  newQuantity |   newSize |   newWarehouse    |   oldName    |
        |orphantestitem         |   item 1             |  5           |   1       |   orphantestwh    |   validitem  |
        |editeditem             |   item 2             |  10          |   2       |   editedtest      |   validitem2 |
