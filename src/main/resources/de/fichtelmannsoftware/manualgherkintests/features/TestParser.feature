Feature: Parse a feature file to a ManualTest object
  Different strings trigger different test cases.

  Scenario: Reading 'Feature' creates a title for the particular test case
    Given 'Feature' is written down into a single feature file
    When TestParser gets the feature file 'TestParser.feature'
    Then TestParser creates a title which contains 'Parse a feature file to a ManualTest object'