Feature: Employee workflow step
  An employee wants to add, edit, and delete a workflow step

  Background:
    Given the employee Test begins using the application
    And the user is not currently logged in
    When the user begins to log in as an employee
    And the employee sets the username to "Test E"
    And the employee sets the password to "password"
    And the employee sets the company to "TEST_COMPANY"
    And the employee logs in
    Then no error should occur

  Scenario: Existing employee adds a workflow step, edits it, and then deletes it
    Given the employee Test begins using the application
    And the user begins entering a new workflow
    And the user sets the workflow's name to "CUCUMBER_WORKFLOW"
    And the user sets the workflow's sort order to "1"
    And the user sets the workflow's description to "Cucumber Workflow"
    And the user adds the new workflow
    Then no error should occur
    And the user begins entering a new workflow step
    And the user sets the workflow step's workflow name to the last workflow added
    And the user sets the workflow step's name to "CUCUMBER_WORKFLOW_STEP"
    And the user sets the workflow step's type to "MANUAL_TRANSITION"
    And the user sets the workflow step to be the default
    And the user sets the workflow step's sort order to "1"
    And the user sets the workflow step's description to "Cucumber Workflow"
    And the user adds the new workflow step
    Then no error should occur
    And the user begins specifying a workflow step to edit
    And the user sets the workflow step's workflow name to the last workflow added
    And the user sets the workflow step's name to the last workflow step added
    And the user begins editing the workflow step
    Then no error should occur
    And the user sets the workflow step's sort order to "2"
    And the user sets the workflow step's description to "Cucumber Edited Workflow Step"
    And the user finishes editing the workflow step
    Then no error should occur
    And the user begins deleting a workflow step
    And the user sets the workflow step's workflow name to the last workflow added
    And the user sets the workflow step's name to the last workflow step added
    And the user deletes the workflow step
    Then no error should occur
    And the user begins deleting a workflow
    And the user sets the workflow's name to the last workflow added
    And the user deletes the workflow
    Then no error should occur

  Scenario: Existing employee adds a workflow with a duplicate name and receives an error
    Given the employee Test begins using the application
    When the user begins entering a new workflow step
    And the user sets the workflow step's workflow name to "EMPLOYEE_STATUS"
    And the user sets the workflow step's type to "MANUAL_TRANSITION"
    And the user sets the workflow step's name to "ACTIVE"
    And the user sets the workflow step to be the default
    And the user sets the workflow step's sort order to "1"
    And the user adds the new workflow step
    Then the execution error DuplicateWorkflowStepName should occur
