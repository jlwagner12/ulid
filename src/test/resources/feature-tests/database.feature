Feature: ULID database integration
  Inserting and selecting entities using ebeans with a database.

  Scenario: PostgreSQL integration
    Given a postgres database
    When I create an entity and insert it into the database,
    Then I should be able to select it and it match what was inserted.

  Scenario: Oracle integration
    Given an oracle database
    When I create an entity and insert it into the database,
    Then I should be able to select it and it match what was inserted.

  Scenario: MS SQL Server v16 integration
    Given a ms-sql database
    When I create an entity and insert it into the database,
    Then I should be able to select it and it match what was inserted.

  Scenario: MS SQL Server v17 integration
    Given a ms-sql database
    When I create an entity and insert it into the database,
    Then I should be able to select it and it match what was inserted.

  Scenario: PostgreSQL w/ e-beans auto-generated id integration
    Given a postgres database
    When I create an entity with a generated id and insert it into the database,
    Then It should have a generated id stored in the database.

  Scenario: Oracle w/ e-beans auto-generated id integration
    Given an oracle database
    When I create an entity with a generated id and insert it into the database,
    Then It should have a generated id stored in the database.

  Scenario: MS SQL Server v16 w/ e-beans auto-generated id integration
    Given a ms-sql database
    When I create an entity with a generated id and insert it into the database,
    Then It should have a generated id stored in the database.

  Scenario: MS SQL Server v17 w/ e-beans auto-generated id integration
    Given a ms-sql database
    When I create an entity with a generated id and insert it into the database,
    Then It should have a generated id stored in the database.

  Scenario: PostgreSQL w/ bad data
    Given a postgres database
    And bad data in the ULID column
    When I query for the entity,
    Then It should fail.

  Scenario: Oracle w/ bad data
    Given an oracle database
    And bad data in the ULID column
    When I query for the entity,
    Then It should fail.

  Scenario: MS SQL Server v16 w/ bad data
    Given a ms-sql database
    And bad data in the ULID column
    When I query for the entity,
    Then It should fail.

  Scenario: MS SQL Server v17 w/ bad data
    Given a ms-sql database
    And bad data in the ULID column
    When I query for the entity,
    Then It should fail.
