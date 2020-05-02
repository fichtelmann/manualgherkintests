Feature: Parse a feature file to a ManualTest object
  Use the original Gherkin library from cucumber for parsing the files into the object model.

  Scenario: Creating a TestParser object creates a list with ManualTests
    When Creating a TestParser object from file 'oneFeature3Tests.feature'
    Then TestParser object contains 1 manual test

  Scenario: One feature file creates one ManualTests
    When Creating a TestParser object from file 'oneFeature3Tests.feature'
    Then TestParser object contains 1 manual test

  Scenario: Each Scenario in a feature file is one test case
    When Creating a TestParser object from file 'oneFeature3Tests.feature'
    Then TestParser object contains 1 manual test
    And This ManualTest object has 3 tests

  Scenario: Giving a folder to the TestParser will create one ManualTest per feature file
    Given The folder 'threeFeatures/' contain three feature files
    When Creating a TestParser object from file 'threeFeatures/'
    Then TestParser object contains 1 manual test