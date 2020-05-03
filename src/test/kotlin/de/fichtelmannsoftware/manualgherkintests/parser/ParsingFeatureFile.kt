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


class ParsingFeatureFile : En {
    companion object {
        lateinit var actualTestParser: TestParser
    }

    init {
        When("Creating a TestParser object from file {string}") { filepath: String? ->
            if (filepath != null) {
                val file = File(ParsingFeatureFile::class.java.getResource(filepath).path)
                assertTrue(file.exists())
                actualTestParser = TestParser(file)
            } else {
                fail("File/Folder not found: $filepath")
            }
        }

        Then("TestParser object contains {int} manual test") { testCount: Int? ->
            assertEquals(testCount, actualTestParser.manualTests.size)
        }

        Then("This ManualTest object has {int} tests") { testCount: Int? ->
            assertEquals(testCount, actualTestParser.manualTests[0].testCases.size)
        }

        Given("The folder {string} contain {int} feature files") { filepath: String?, fileCount: Int? ->
            if (filepath != null) {
                val file = File(ParsingFeatureFile::class.java.getResource(filepath).path)
                assertTrue(file.exists())
                assertTrue(file.isDirectory)
                val featureFiles = file.listFiles()!!.filter { it.extension == "feature" }
                assertEquals(fileCount, featureFiles.size)
            } else {
                fail("File/Folder not found: $filepath")
            }
        }
    }
}
