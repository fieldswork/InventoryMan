@utilBarRendering
Feature: Utilization Bar
    Scenario Outline: Checking if utilization bar renders
        Given I am on the page with the Warehouses cards
        When the warehouse "<warehouse>" is displaying on the page
        Then I should see the utilization bar for the "<warehouse>" warehouse displaying "<utilization>" utilization

    Examples:
        | warehouse | utilization |
        | validtest | 50%         |
        