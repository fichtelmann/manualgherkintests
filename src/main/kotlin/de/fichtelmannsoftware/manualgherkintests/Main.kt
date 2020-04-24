package de.fichtelmannsoftware.manualgherkintests

import de.fichtelmannsoftware.manualgherkintests.parser.TestParser
import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    welcome()
    val fileToParse: File = prepareFile(args[0])
    println("Path to parse: ${fileToParse.absolutePath}")

    val parser = TestParser(File(args[0]))
}

fun prepareFile(filePath: String): File {
    val newFile = File(filePath)
    if (newFile.exists()) {
        return newFile
    }
    throw FileNotFoundException("File '$filePath' was not found")
}

fun welcome() {
    println("########\tManual Gherkin Tests\t########")
}


