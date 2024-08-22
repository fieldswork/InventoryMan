@sortItems
Feature: Sort items
    Scenario Outline: Sorted items
        Given I am in the Items page
        When I select "<sortCriteria>" in the Sort By dropdown to sort Items
        Then Items should be displayed in the page by "<order>" order

    Examples:
        |sortCriteria           |   order           |
        |Name (Alphabetical)    |   alphabetical    |
        |Quantity               |   quantity        |
        |Item Size              |   size            |
        |Total Size             |   total size      |