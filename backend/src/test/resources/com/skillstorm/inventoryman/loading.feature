@loading
Feature: Has the website loaded?
    Scenario: The website has loaded
        Given I attempt to load the website
        When the website loads
        Then I should see the title "InventoryMan"