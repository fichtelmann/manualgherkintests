package de.fichtelmannsoftware.manualgherkintests.console

import de.fichtelmannsoftware.manualgherkintests.parser.TestParser
import java.io.File

class ManualTestConsole(args: Array<String>) {
    init {
        welcome()
        if (args.contains(Companion.argInput)) {
            val indexOfInput = args.indexOf(Companion.argInput)
            val filePath = args[indexOfInput + 1]
            println("Loading filepath: '$filePath'")
            load(filePath)
        }
    }

    fun load(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            val tests = TestParser(file).manualTests
            println("${ANSI_GREEN}Loading feature file successful.")
        } else {
            println("${ANSI_RED}File '$file' not found.")
        }
    }

    fun welcome() {
        println("########\tManual Gherkin Tests\t########")
    }

    companion object {
        const val argInput = "-i"
        const val ANSI_RED = "\u001B[31m";
        const val ANSI_GREEN = "\u001B[32m";
    }
}