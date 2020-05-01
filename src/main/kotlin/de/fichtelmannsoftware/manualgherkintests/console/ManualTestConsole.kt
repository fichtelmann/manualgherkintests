package de.fichtelmannsoftware.manualgherkintests.console

import de.fichtelmannsoftware.manualgherkintests.parser.TestParser
import de.fichtelmannsoftware.manualgherkintests.parser.poko.ManualTest
import java.io.File
import java.io.FileNotFoundException

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

    fun start(testlist: List<ManualTest> = tests) {
        if (testlist.isEmpty()) {
            println("${ANSI_RED}No tests found!")
        } else {
            println("${ANSI_PURPLE}#####\tTest suite started for ${testlist.size} tests.\t#####")
            runTests(testlist)
        }
    }

    private fun runTests(testlist: List<ManualTest>) {
        for (testNo in 0..testlist.size - 1) {
            println("${ANSI_PURPLE}#####\tTest ${testNo + 1} of ${testlist.size}\t#####")
            val actualTest = testlist[testNo]
            val prefix = "$ANSI_PURPLE\t# $ANSI_WHITE"
            println(prefix + actualTest.feature)
            println(prefix + actualTest.description)
        }
    }

    fun welcome() {
        println("########\tManual Gherkin Tests\t########")
    }

    companion object {
        const val argInput = "-i"
        const val ANSI_RED = "\u001B[31m"
        const val ANSI_GREEN = "\u001B[32m"
        const val ANSI_PURPLE = "\u001B[35m"
        const val ANSI_WHITE = "\u001B[37m"
        const val ANSI_RESET = "\u001B[0m"

    }
}