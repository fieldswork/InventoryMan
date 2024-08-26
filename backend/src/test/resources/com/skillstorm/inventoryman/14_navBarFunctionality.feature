@navBarFunctionality
Feature: Navigation Bar Functionality
    Scenario Outline: Verifying the different navigation tabs in the navigation bar work and highlight correctly
        Given I am on the InventoryMan landing page
        When I select the InventoryMan button 
        Then I should see that no other buttons are highlighted
        And that I am on the landing page for InventoryMan
        When I select the Warehouses button
        Then I should see that the Warehouses button is highlighted
        And that I am on the Warehouses page
        When I select the Items button
        Then I should see that the Items button is highlighted
        And that I am on the Items page
        When I select the Add Warehouse button
        Then I should see that the Add Warehouse button is highlighted
        And that I am on the Add Warehouse page
        When I select the Add Item button
        Then I should see that the Add Item button is highlighted
        And that I am on the Add Item page

    Examples: