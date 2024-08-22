@editWarehouse
Feature: Edit warehouse
    Scenario Outline: Editing warehouse
        Given I am on the Warehouses page
        When I find the "<name>" warehouse in the Warehouses page
        And I click the Edit button to edit the warehouse
        Then I should be redirected to the Edit Warehouse page
        When I enter "<newName>" in the warehouse name field
        And I enter "<newCapacity>" in the warehouse capacity field
        And I click the Update button to submit the warehouse
        Then I should be redirected to the Warehouses page if the edited warehouse is valid
        And I should see the edited warehouse "<newName>" in the Warehouses page

    Examples:
        |name                   |   newName         |  newCapacity    |
        |validtest              |   editedtest      |  123            |
        |validtest2             |   orphantestwh    |  456            |