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

package de.fichtelmannsoftware.manualgherkintests.console

import de.fichtelmannsoftware.manualgherkintests.parser.TestParser
import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTest
import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTestCase
import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTestResult
import java.io.File
import java.io.FileNotFoundException
import java.util.function.Consumer

class ManualTestConsole(args: Array<String>) {
    lateinit var tests: List<ManualTest>

    init {
        welcome()
        if (args.contains(argInput)) {
            val indexOfInput = args.indexOf(argInput)
            val filePath = args[indexOfInput + 1]
            println("Loading filepath: '$filePath'")
            load(filePath)
        }
    }

    fun load(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            tests = TestParser(file).manualTests
            println("${ANSI_GREEN}Loading feature file successful.")
        } else {
            println("${ANSI_RED}File '$filePath' not found.")
            throw FileNotFoundException(filePath)
        }
    }

    fun start(testlist: List<ManualTest> = this.tests) {
        if (testlist.isEmpty()) {
            println("${ANSI_RED}No tests found!")
        } else {
            println("${ANSI_PURPLE}#####\tTest suite started for ${testlist.size} features.\t#####")
            runTestFeatures(testlist)
        }
    }

    private fun runTestFeatures(testlist: List<ManualTest>) {
        for (testScenatioNo in 0..testlist.size - 1) {
            println("${ANSI_PURPLE}#####\tTest feature ${testScenatioNo + 1} of ${testlist.size}\t#####")
            val actualTest = testlist[testScenatioNo]
            println(prefix + actualTest.feature)
            println(prefix + actualTest.description)
            for (testNo in 0..actualTest.testCases.size - 1) {
                println("${ANSI_PURPLE}#####\tTest ${testNo + 1} of ${actualTest.testCases.size}\t#####")
                runTest(actualTest.testCases[testNo])
            }

        }
    }

    private fun runTest(manualTestCase: ManualTestCase) {
        println(prefix + manualTestCase.description)
        println("$preparePrefix${manualTestCase.preparation}")
        println("$actionPrefix${manualTestCase.actions}")
        println(expectedPrefix)
        println("${ANSI_PURPLE}Expected result: ${ANSI_WHITE}${manualTestCase.expectedResult}")
        println("${ANSI_PURPLE}Please enter actual result: ${ANSI_WHITE}")
        manualTestCase.documentation = readLine().toString()
        manualTestCase.result = getFinalTestResultFromUser()
        printResult(manualTestCase.result)
    }

    /**
     * Ask the user for y or n (yes or no) if the test matches the expectations and
     * @return ManualTestResult
     */
    private fun getFinalTestResultFromUser(): ManualTestResult {
        var resultInput: String
        do {
            println(resultPrefix)
            resultInput = readLine().toString().toUpperCase()
        } while (resultInput != "N" && resultInput != "Y")
        val testResult = when (resultInput) {
            "Y" -> ManualTestResult.PASS
            "N" -> ManualTestResult.FAIL
            else -> ManualTestResult.FAIL
        }
        return testResult
    }

    /**
     * Print out welcome message to the console.
     */
    fun welcome() {
        println("${ANSI_PURPLE}########\tManual Gherkin Tests\t########")
    }

    fun printResult(actualResult: ManualTestResult, prefix: String = "") {
        if (actualResult == ManualTestResult.PASS) {
            println("""$ANSI_GREEN$prefix####################
                                 |$prefix####### PASS #######
                                 |$prefix####################$ANSI_WHITE
            """.trimMargin())
        } else {
            println("""$ANSI_RED$prefix####################
                               |$prefix####### FAIL #######
                               |$prefix####################$ANSI_WHITE
            """.trimMargin())
        }
    }

    /**
     * Print out a report for Manual tests.
     */
    fun printReport(testlist: List<ManualTest> = this.tests) {
        println("""$ANSI_PURPLE##################################
                              |############# REPORT #############
                              |##################################""".trimMargin())
        var featureCount = 0
        testlist.forEach {
            var testCount = 0
            println("$ANSI_PURPLE\n#############\tFeature ${++featureCount}/${testlist.size}\t#############")
            println("${it.feature}\n${it.description}")
            it.testCases.forEach(Consumer { testcase ->
                println("$ANSI_PURPLE\t\n#############\tTest ${++testCount}/${it.testCases.size}\t#############")
                println("\tDescription: ${testcase.description}")
                println("\tPreparation: ${testcase.preparation}")
                println("\tActions: ${testcase.actions}")
                println("\tActual result: ${testcase.documentation}")
                println("\tExpected result: ${testcase.expectedResult}")
                printResult(testcase.result, "\t")
            })
            println("$ANSI_PURPLE\n#########\tFeature ${featureCount}/${testlist.size} tested\t##########")
        }
    }

    companion object {
        const val argInput = "-i"
        const val ANSI_RED = "\u001B[31m"
        const val ANSI_GREEN = "\u001B[32m"
        const val ANSI_PURPLE = "\u001B[35m"
        const val ANSI_WHITE = "\u001B[37m"

        const val prefix = "$ANSI_PURPLE\t# $ANSI_WHITE"
        const val preparePrefix = "${ANSI_PURPLE}Please prepare: $ANSI_WHITE"
        const val actionPrefix = "${ANSI_PURPLE}Do: $ANSI_WHITE"
        const val expectedPrefix = "${ANSI_PURPLE}What happen after that?$ANSI_WHITE"
        const val resultPrefix = "${ANSI_PURPLE}Did the test match expectations [y/N]: $ANSI_WHITE"
    }
}