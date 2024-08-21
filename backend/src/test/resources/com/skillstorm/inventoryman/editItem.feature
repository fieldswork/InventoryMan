@editItem
Feature: Edit Item
    Scenario Outline: Editing items
        Given I am on the Items page
        When I find the "<item>" warehouse in the Items page
        And I click the Edit button to edit the item
        Then I should be redirected to the Edit Item page
        When I enter "<newName>" in the item name field
        And I enter "<newDescription>" in the item description field
        And I enter "<newQuantity>" in the item quantity field
        And I enter "<newSize>" in the item size field
        And I select "<newWarehouse>" in the item warehouse dropdown
        And I click the Update button to submit the item
        Then I should be redirected to the Warehouses page if the edited item is valid
        And If I navigate to the Items page
        And I should see the edited item "<newName>" in the Warehouses page

    Examples:
        |item                   |   newName         |  newCapacity    |  newWarehouse   |
        |validitem              |   editedtest      |  20             |  validtest      |
        |validitem2             |   orphantest      |  30             |  orphantest     |
