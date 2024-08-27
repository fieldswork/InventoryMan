@orphanRemoval
Feature: Orphan removal
    Scenario Outline: Verifying orphaned items have been removed from the database
        Given I am on the items page after the orphantestwh warehouse has been deleted
        Then I should not see the orphaned item "<item>" in the items list

    Examples:
        | item                |
        | orphantestitem      |
