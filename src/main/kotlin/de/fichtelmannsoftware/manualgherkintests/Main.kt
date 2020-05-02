package de.fichtelmannsoftware.manualgherkintests

import de.fichtelmannsoftware.manualgherkintests.console.ManualTestConsole
import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val manualTestConsole = ManualTestConsole(args)
    manualTestConsole.start()
    manualTestConsole.printReport()
}

fun prepareFile(filePath: String): File {
    val newFile = File(filePath)
    if (newFile.exists()) {
        return newFile
    }
    throw FileNotFoundException("File '$filePath' was not found")
}
