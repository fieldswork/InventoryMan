@filterItems
Feature: Item Filtering
    Scenario Outline: Filtering by Warehouse
        Given I am on the Items page with several items contained in warehouses
        When I select "<warehouse>" in the Filter by Warehouse dropdown to filter items
        Then Only items from the "<warehouse>" warehouse should be displayed in the page

    Examples:
        | warehouse    | 
        | orphantestwh |
        | editedtest   |