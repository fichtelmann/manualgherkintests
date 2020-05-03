/*
 * Copyright (c) Kay Fichtelmann 2020.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.fichtelmannsoftware.manualgherkintests.parser

import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTest
import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTestCase
import io.cucumber.gherkin.Gherkin
import io.cucumber.messages.IdGenerator
import io.cucumber.messages.Messages
import io.cucumber.messages.Messages.GherkinDocument
import java.io.File
import java.io.FileNotFoundException
import java.util.stream.Collectors


class TestParser(path: File) {
    val manualTests = mutableListOf<ManualTest>()
    private val idGenerator = IdGenerator.Incrementing()

    init {
        if (path.exists()) {
            if (path.isFile) {
                manualTests.add(parseFeatureFile(path))
            } else if (path.isDirectory) {
                val featureFiles = path.listFiles()!!.filter { it.extension == "feature" }
                featureFiles.forEach { manualTests.add(parseFeatureFile(it)) }
            }
        } else {
            throw FileNotFoundException("The given file/directory '$path' does not exists.")
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