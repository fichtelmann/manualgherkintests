package de.fichtelmannsoftware.manualgherkintests.parser

import java.io.File

class TestParser(path: File) {

    enum class TestStepType {
        Feature,
        Description,
        Preparation,
        Action,
        Expected,
        Unknown
    }

    init {
        if (path.isFile) {
            parseFeatureFile(path)
        }
    }

    private fun parseFeatureFile(featureFile: File) {
        featureFile.forEachLine {
            when (identifyType(it)) {
                TestStepType.Feature -> println("Feature: $it")
                TestStepType.Description -> println("Description: $it")
                TestStepType.Preparation -> println("Prepare: $it")
                TestStepType.Action -> println("Please do: $it")
                TestStepType.Expected -> println("Expect: $it")
                else -> println(it)
            }
        }
    }

    /**
     * Check if a particular string is inside the given line and
     * @return the matching TestStepType.
     */
    private fun identifyType(line: String): TestStepType {
        return when {
            line.trim().startsWith(STRING_FEATURE, ignoreCase = true) -> TestStepType.Feature
            line.trim().startsWith(STRING_SCENARIO, ignoreCase = true) -> TestStepType.Description
            line.trim().startsWith(STRING_GIVEN, ignoreCase = true) -> TestStepType.Preparation
            line.trim().startsWith(STRING_WHEN, ignoreCase = true) -> TestStepType.Action
            line.trim().startsWith(STRING_THEN, ignoreCase = true) -> TestStepType.Expected
            else -> TestStepType.Unknown
        }
    }

    companion object {
        const val STRING_FEATURE = "Feature"
        const val STRING_SCENARIO = "Scenario"
        const val STRING_GIVEN = "Given"
        const val STRING_WHEN = "When"
        const val STRING_THEN = "Then"
    }
}