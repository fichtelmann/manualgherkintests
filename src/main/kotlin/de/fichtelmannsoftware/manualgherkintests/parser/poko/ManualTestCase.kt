package de.fichtelmannsoftware.manualgherkintests.parser.poko

data class ManualTestCase(var description: String) {
    var preparation = ""
    var actions = ""
    var expectedResult = ""
    var documentation = ""
    var result = ManualTestResult.FAIL
}
