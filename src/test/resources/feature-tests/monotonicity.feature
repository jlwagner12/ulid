Feature: ULID Monotonicity

  Scenario: Several ULIDs created within the same millisecond
    Given several random ULIDs, generated as quickly as possible,
    Then those created within the same millisecond should be monotonic.
