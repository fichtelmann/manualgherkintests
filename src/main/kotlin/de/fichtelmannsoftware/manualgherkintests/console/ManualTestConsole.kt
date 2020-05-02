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
        println("$expectedPrefix")
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