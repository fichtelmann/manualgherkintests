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

import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.*
import java.io.File

class SupportTags : En {
    companion object {
        lateinit var actualParser: TestParser
    }

    init {
        Given("Inside the folder: {string} only {int} (feature|scenario) file\\(s) have the tag {string}") { featureFolder: String, amountOfFilesWithTag: Int, tagName: String ->

            val directory = File(ParsingFeatureFile::class.java.getResource(featureFolder).path)
            var tagCounter = 0
            assertTrue(directory.exists())
            directory.listFiles()!!.filter { it.extension == "feature" }.forEach {
                if (it.readText(Charsets.UTF_8).contains(tagName)) {
                    tagCounter++
                }
            }
            assertEquals(amountOfFilesWithTag, tagCounter)
        }

        When("the user starts the Parser with the directory of feature files: {string} and the tag {string}") { path: String, tagName: String ->

            val directory = File(SupportTags::class.java.getResource(path).path)
            actualParser = TestParser(directory, tagName)
            assertNotNull(actualParser)
        }

        Then("only the feature {string} should be parsed") { featureName: String? ->
            assertEquals(1, actualParser.manualTests.size)
            assertEquals(featureName, actualParser.manualTests[0].feature)
        }

        Then(
            "only {int} test case with the description {string} should be available"
        ) { amountOfTestcases: Int?, expectedDescription: String? ->

            assertEquals(amountOfTestcases, actualParser.manualTests[0].testCases.size)
            assertEquals(expectedDescription, actualParser.manualTests[0].testCases[0].description)
        }
    }
}