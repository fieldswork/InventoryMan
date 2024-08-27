@browserNavigation
Feature: Navigation between pages
    Scenario Outline: Navigating with the browser back/forward buttons
        Given I am on the landing page of InventoryMan
        When I select the Warehouses page from the navigation bar
        And I select the Items page from the navigation bar
        And I navigate back using the browser back button
        Then I should see the Warehouses page
        When I navigate forward using the browser forward button
        Then I should see the Items page