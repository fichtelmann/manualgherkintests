Feature: The software should support tags inside the feature files
  Inside feature files it is allowes to define tags. These tags mark particular tests.

  As a user I want to setup a tag and a directory and the application will only run these Features or Scenarios which
  are marked with this tag.

  Scenario: Inside a folder of feature files one file is marked with a the tag 'manual' and the others not. The Parser
  shall only parse these files with the tag.
    Given Inside the folder: 'oneFeatureFileIsTagged/' only 1 feature file(s) have the tag '@manual'
    When the user starts the Parser with the directory of feature files: 'oneFeatureFileIsTagged/'
    Then only the feature 'This is the second feature' should be parsed