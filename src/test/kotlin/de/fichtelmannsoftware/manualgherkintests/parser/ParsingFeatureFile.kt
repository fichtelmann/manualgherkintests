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
