package de.fichtelmannsoftware.manualgherkintests.parser.poko


data class ManualTest(var feature: String, var description: String = "") {
    val testCases = mutableListOf<ManualTestCase>()

}

enum class ManualTestResult {
    FAIL,
    PASS
}
