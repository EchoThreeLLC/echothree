Feature: Employee entity attribute
  An employee wants to add and delete an entity attribute

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user logs in as an employee with the username "Test E" and password "password" and company "TEST_COMPANY"
    Then no error should occur

  Scenario: Existing employee adds an entity attribute and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new entity attribute
    And the user sets the entity attribute's component vendor to ECHOTHREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's entity attribute type to INTEGER
    And the user sets the entity attribute to track revisions when modified
    And the user sets the entity attribute's sort order to "1"
    And the user adds the new entity attribute
    Then no error should occur
    And the user begins deleting an entity attribute
    And the user sets the entity attribute's component vendor to ECHOTHREE
    And the user sets the entity attribute's entity type to Item
    And the user sets the entity attribute's name to the last entity attribute added
    And the user deletes the entity attribute
    Then no error should occur
