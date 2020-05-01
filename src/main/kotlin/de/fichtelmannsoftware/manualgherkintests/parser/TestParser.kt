package de.fichtelmannsoftware.manualgherkintests.parser

import de.fichtelmannsoftware.manualgherkintests.poko.ManualTest
import io.cucumber.gherkin.Gherkin
import io.cucumber.messages.IdGenerator
import io.cucumber.messages.Messages
import io.cucumber.messages.Messages.GherkinDocument
import java.io.File
import java.util.stream.Collectors


class TestParser(path: File) {
    val idGenerator = IdGenerator.Incrementing()
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
        println("Feature: $feature")

        // Get the first Scenario node of the Feature node
        val scenario = feature.getChildren(0).scenario
        println("Scenario: $scenario")
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