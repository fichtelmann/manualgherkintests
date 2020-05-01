package de.fichtelmannsoftware.manualgherkintests.parser

import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTest
import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTestCase
import io.cucumber.gherkin.Gherkin
import io.cucumber.messages.IdGenerator
import io.cucumber.messages.Messages
import io.cucumber.messages.Messages.GherkinDocument
import java.io.File
import java.util.stream.Collectors


class TestParser(path: File) {
    val manualTests = mutableListOf<ManualTest>()
    private val idGenerator = IdGenerator.Incrementing()

    init {
        if (path.isFile) {
            manualTests.add(parseFeatureFile(path))
        }
    }

    /**
     * Use Gherkin to parse the feature file and
     * @return a ManualTest object.
     *
     * HowTo: https://github.com/cucumber/gherkin-java/blob/master/src/test/java/io/cucumber/gherkin/GherkinTest.java
     */
    private fun parseFeatureFile(featureFile: File): ManualTest {
        val paths = listOf(featureFile.absolutePath)
        val includeSource = false
        val includeAst = true
        val includePickles = true
        val envelopes =
            Gherkin.fromPaths(paths, includeSource, includeAst, includePickles, idGenerator)
                .collect(
                    Collectors.toList<Messages.Envelope>()
                )
        // Get the AST
        val gherkinDocument: GherkinDocument = envelopes[0].gherkinDocument

        // Get the Feature node of the AST
        val feature = gherkinDocument.feature
        val manualTest: ManualTest = convertFeatureToManualTest(feature)

        return manualTest
    }

    /**
     * Use the Feature object from Gherkin to parse all Szenarios and
     * @return a ManualTest object.
     */
    private fun convertFeatureToManualTest(feature: GherkinDocument.Feature?): ManualTest {
        val manualTest = ManualTest(feature!!.name, feature.description)
        for (i in 0..feature.childrenCount - 1) {
            val scenario = feature.getChildren(i).scenario
            val testCase = ManualTestCase(scenario.name)
            scenario.stepsList.forEach {
                when (it.keyword.trim()) {
                    STRING_KEYWORD_GIVEN -> testCase.preparation += it.text
                    STRING_KEYWORD_WHEN -> testCase.actions += it.text
                    STRING_KEYWORD_THEN -> testCase.expectedResult += it.text
                    else -> error("${it.keyword} is not supported.")
                }
            }
            manualTest.testCases.add(testCase)
        }
        return manualTest
    }

    companion object {
        const val STRING_KEYWORD_WHEN = "When"
        const val STRING_KEYWORD_THEN = "Then"
        const val STRING_KEYWORD_GIVEN = "Given"
    }
}