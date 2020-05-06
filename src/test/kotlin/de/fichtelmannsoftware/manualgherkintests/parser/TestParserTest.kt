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

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileNotFoundException

internal class TestParserTest {
    @Test
    @DisplayName("Test constructor if input is a single file")
    internal fun constructor_InputSingleFile() {
        val testFile = File(TestParserTest::class.java.getResource("oneFeature3Tests.feature").path)
        val testObject = TestParser(testFile)
        val expectedAmoutOfTestedFeatures = 1
        val expectedFeatureName = "This is a feature"
        val expectedAmountOfTests = 3

        assertNotNull(testObject)
        assertEquals(expectedAmoutOfTestedFeatures, testObject.manualTests.size)
        val manualTestToAssert = testObject.manualTests[0]
        assertEquals(expectedFeatureName, manualTestToAssert.feature)
        val listOfTests = manualTestToAssert.testCases
        assertEquals(expectedAmountOfTests, listOfTests.size)
    }

    @Test
    @DisplayName("Test constructor if input file not exists")
    internal fun constructor_InputFileNotExists() {
        val expectedMessage = "The given file/directory 'notExisting.feature' does not exists."
        val testFile = File("notExisting.feature")

        val exception: Exception = assertThrows(FileNotFoundException::class.java) {
            TestParser(testFile)
        }
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    @DisplayName("Test constructor if input is a directory with three files")
    internal fun constructor_InputDirectory() {
        val testDirectory = File(TestParserTest::class.java.getResource("threeFeatures/").path)
        val expectedAmoutOfTestedFeatures = 3
        val expectedFeatureNames = arrayOf(
            "This is the first feature",
            "This is the second feature",
            "This is the third feature"
        )

        val testObject = TestParser(testDirectory)
        assertNotNull(testObject)
        assertEquals(expectedAmoutOfTestedFeatures, testObject.manualTests.size)

        testObject.manualTests.map { it.feature }.forEach {
            assertTrue(expectedFeatureNames.contains(it))
        }
    }

    @Test
    @DisplayName("Test the constructor if the second parameter for tags is given")
    internal fun constructor_SecondParameter() {
        val testDirectory = File(TestParserTest::class.java.getResource("oneFeatureFileIsTagged/").path)
        val expectedAmoutOfTestedFeatures = 1
        val expectedFeatureName = "This is the second feature"
        val tagName = "@manual"

        val parser = TestParser(testDirectory, tagName)

        assertTrue(testDirectory.exists())
        assertEquals(expectedAmoutOfTestedFeatures, parser.manualTests.size)
        assertEquals(expectedFeatureName, parser.manualTests[0].feature)
    }

    @Test
    @DisplayName("NegativeTest the constructor if the second parameter for tags is NOT given")
    internal fun constructor_SecondParameter_negative() {
        val testDirectory = File(TestParserTest::class.java.getResource("oneFeatureFileIsTagged/").path)
        val expectedAmoutOfTestedFeatures = 3
        val expectedNameThirdFeature = "This is the third feature"

        val parser = TestParser(testDirectory)

        assertTrue(testDirectory.exists())
        assertEquals(expectedAmoutOfTestedFeatures, parser.manualTests.size)
        assertEquals(expectedNameThirdFeature, parser.manualTests[0].feature)
    }
}