package lib_publisher_tools.versioning

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun String?.checkIsValidVersionString() {
    contract {
        returns() implies (this@checkIsValidVersionString != null)
    }
    when {
        this.isNullOrEmpty() -> throw IllegalStateException("No version entered.")
        any { it == ' ' } -> throw IllegalStateException("Versions can't contain spaces.")
        startsWith('v') -> throw IllegalStateException("Please, don't include v prefix.")
        first().isDigit().not() -> throw IllegalStateException("Should start with a digit.")
        all {
            it.isLetterOrDigit() || it == '.' || it == '-'
        }.not() -> throw IllegalStateException("Only digits, letters, dots and dashes are allowed.")
    }
}
