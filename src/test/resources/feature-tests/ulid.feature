Feature: ULID

  Scenario Outline: Converting a bad ULID string
    Given a bad ULID string, "<ulid>",
    And it is requested to be converted to an actual ULID.
    Then a failure is expected.
    Examples:
      | ulid                       |
      |                            |
      | 8ZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | 9ZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | AZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | BZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | CZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | DZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | EZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | FZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | GZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | HZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | JZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | KZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | MZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | NZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | PZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | QZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | RZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | SZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | TZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | VZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | WZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | XZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | YZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | ZZZZZZZZZZZZZZZZZZZZZZZZZZ |
      | 01F99GM3AAFN3KR86D6JGPKXPI |
      | 01F99GM3AJ5Q1G13PFWMR8WN3i |
      | 01F99GM3AKF17ECK3WTXB6VMSL |
      | 01F99GM3AKGWVZ3Y7GR5NWHVEl |
      | 01F99GM3AKMQ5TPF65BD85CB3O |
      | 01F99GM3AM41J2GN3DTVN5YAKo |
      | 01F99GM3AMPYXJNMGPWP14J0KU |
      | 01F99GM3AM1JN13NEAQ3ARG77u |
      | 01F99GM3AM1JN13NEAQ3ARG7u  |
