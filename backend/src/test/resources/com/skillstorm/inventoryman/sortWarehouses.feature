@sortWarehouses
Feature: Sort warehouses
    # Scenario Outline: Sorted warehouses
    #     Given I am in the Warehouses page
    #     When I select Name (Alphabetical) in the Sort By dropdown 
    #     Then Warehouses should be displayed in the page by alphabetical order

    Scenario Outline: Sorted warehouses
        Given I am in the Warehouses page
        When I select "<sortCriteria>" in the Sort By dropdown to sort Warehouses
        Then Warehouses should be displayed in the page by "<order>" order

    # Scenario Outline: Unsorted warehouses
    #     Given I am in the Warehouses page
    #     When I select "<sortCriteria>" in the Sort By dropdown 
    #     Then Warehouses should NOT be displayed in the page by "<order>" order

    # Examples:
    # |sortCriteria           |   order           |
    # |Name (Alphabetical)    |   alphabetical    |
    # |Utilization (%)        |   utilization     |

