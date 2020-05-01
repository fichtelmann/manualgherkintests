package de.fichtelmannsoftware.manualgherkintests.parser

import de.fichtelmannsoftware.manualgherkintests.poko.ManualTest
import de.fichtelmannsoftware.manualgherkintests.poko.ManualTestCase
import java.io.File

class TestParser(path: File) {
    var manualTest = ManualTest()

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
        val testCases = mutableListOf<ManualTestCase>()
        var actualTestCase = ManualTestCase()

        featureFile.forEachLine {
            when (identifyType(it)) {
                TestStepType.Feature -> actualTestCase.feature += prepareString(it, TestStepType.Feature)
                TestStepType.Description -> actualTestCase.description += prepareString(it, TestStepType.Description)
                TestStepType.Preparation -> actualTestCase.preparation += prepareString(it, TestStepType.Preparation)
                TestStepType.Action -> actualTestCase.actions += prepareString(it, TestStepType.Action)
                TestStepType.Expected -> actualTestCase.expectedResult += prepareString(it, TestStepType.Expected)
                else -> println(it)
            }
        }
        println(actualTestCase.feature)
        println(actualTestCase.actions)
    }

    /**
     * Trim the string search for the particular keyword and reduce the beginning of the string.
     * If a colon is near the keyword it will be removed also.
     */
    private fun prepareString(actualLine: String, testStepType: TestStepType): String {
        var newLine = actualLine.trim()
        var position = when (testStepType) {
            TestStepType.Feature -> STRING_FEATURE.length
            TestStepType.Description -> STRING_SCENARIO.length
            TestStepType.Preparation -> STRING_WHEN.length
            TestStepType.Action -> STRING_GIVEN.length
            TestStepType.Expected -> STRING_THEN.length
            else -> INVALID_POSITION
        }

        if (position != INVALID_POSITION) {
            val indexColon = actualLine.indexOf(":")
            if (indexColon != INVALID_POSITION && (indexColon == position || indexColon == position + 1)) {
                position = indexColon + 1
            }
            newLine = newLine.subSequence(position, newLine.length).toString()
        }
        return newLine.trim()
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
        const val INVALID_POSITION = -1
    }
}