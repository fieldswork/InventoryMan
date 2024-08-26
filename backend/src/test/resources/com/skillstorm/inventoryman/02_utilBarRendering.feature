@utilBarRendering
Feature: Utilization Bar
    Scenario Outline: Checking if utilization bar renders
        Given I am on the Warehouses page with a warehouse "<warehouse>"
        Then I should see the utilization bar for the "<warehouse>" warehouse displaying "<utilization>" utilization

    Examples:
        | warehouse | utilization |
        | validtest | 50%         |
        