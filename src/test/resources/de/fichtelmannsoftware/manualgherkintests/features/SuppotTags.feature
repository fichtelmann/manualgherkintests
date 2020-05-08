Feature: The software should support tags inside the feature files
  Inside feature files it is allowes to define tags. These tags mark particular tests.

  As a user I want to setup a tag and a directory and the application will only run these Features or Scenarios which
  are marked with this tag.

  Scenario: Inside a folder of feature files one file is marked with a the tag 'manual' and the others not. The Parser
  shall only parse these files with the tag.
    Given Inside the folder: 'oneFeatureFileIsTagged/' only 1 feature file(s) have the tag '@manual'
    When the user starts the Parser with the directory of feature files: 'oneFeatureFileIsTagged/' and the tag '@manual'
    Then only the feature 'This is the second feature' should be parsed

  Scenario: Inside a folder of feature files is one Scenario marked with a tag 'manual' and others not. The parser
  shall only parse these particular Scenario as a manual Test.
    Given Inside the folder: 'oneScenarioIsTagged/' only 1 scenario file(s) have the tag '@manual'
    When the user starts the Parser with the directory of feature files: 'oneScenarioIsTagged/' and the tag '@manual'
    Then only the feature 'This is the second feature' should be parsed
    And only 1 test case with the description 'Tagged Scenario' should be available
