package de.fichtelmannsoftware.manualgherkintests.poko

data class ManualTestCase(var description: String) {
    var preparation = ""
    var actions = ""
    var expectedResult = ""
    var documentation = ""
    var result = ManualTestResult.FAIL
}
