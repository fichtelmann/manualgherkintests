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
