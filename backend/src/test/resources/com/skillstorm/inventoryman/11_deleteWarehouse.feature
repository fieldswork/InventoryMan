@deleteWarehouse
Feature: Delete Warehouse
    Scenario Outline: Deleting Warehouses
        Given I am viewing the Warehouses
        When I find the Warehouse "<warehouse>" on the page
        And I click the Delete button to delete the Warehouse
        And I click the OK button to confirm deletion
        Then The warehouse "<warehouse>" should be deleted from the Warehouse page

    Examples:
        |warehouse           |
        |orphantestwh        | 
        |editedtest          |
        |invalidtest         |
