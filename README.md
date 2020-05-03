[![Version](https://img.shields.io/badge/version-0.1.0-yellow)](https://github.com/fichtelmann/manualgherkintests)

[![License: LGPL 2.1](https://img.shields.io/badge/license-GPL--v3-blue.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html)

# Manual Gherikin Tests

This software contains a parser (based on the Cucumber Gherkin parser) to an own simple object model.
And a small console application which is guiding the user through the tests.

### Background

Sometimes it is easier to make a manual test instead of an automated test. This software should help to define these manual tests and guide a tester through all these tests.

## Technologies

- Used is as mentioned before the great Gherkin parser.
- Building the application with gradle

## How to start
1. simply clone this project to your IDE (e.g. IntelliJ)
1. Run the `main()` from `Main.kt` with the following argument `-i src/test/resources/de/fichtelmannsoftware/manualgherkintests/parser/oneFeature3Tests.feature`

### How to use the parser
```kotlin
//any feature file or folder with feature files
val file = File("oneFeature3Tests.feature") 
val tests = TestParser(file).manualTests
println("Feature of the first test is: ${tests[0].feature}")
```  
### How to use the manual test console
```kotlin
//args should be '-i' and path to a feature file
val manualTestConsole = ManualTestConsole(args)
manualTestConsole.start()
manualTestConsole.printReport()
```  

## Outlook
What happens next:
1. separate parser and bash application
1. Better handling of parameters
1. creating a file based output as result (XML, JSON)

## Thanks

Thanks to the creators of [Gherkin parser](https://cucumber.io/docs/gherkin/) and Cucumber, which
provided the initial idea to use it for my manual testing tool.

Thanks also to the awesome team of [Kotlin](https://kotlinlang.org/).