package lib_publisher_tools.cli

interface CliUi {
    companion object;

    fun printInfo(message: String)
    fun printQuestion(message: String)

    fun askIfYes(yesNoQuestion: String): Boolean
    fun <R> askChoice(optionsWithValues: List<Pair<String, R>>): R

    fun requestManualAction(instructions: String)
    fun requestUserConfirmation(yesNoQuestion: String)

    fun warn(message: String)
    fun showError(message: String)
    fun showUnexpectedException(e: Exception)
}

/**
 * If an [Exception] is thrown from [block]:
 * - If it's an [IllegalStateException] and has a message, it will be shown as an error.
 * - Otherwise, it will be shown as an unexpected exception.
 *
 * Then, [block] will be re-run.
 *
 * Once [block] returns successfully, its last expression is returned from this function.
 *
 * Note that [IllegalStateException] can be produced with [error] from Kotlin stdlib).
 */
inline fun <R> CliUi.runUntilSuccessWithErrorPrintingOrCancel(block: () -> R): R {
    while (true) {
        try {
            return block()
        } catch (e: IllegalStateException) {
            val message = e.message
            if (message != null) {
                showError(message = message)
            } else {
                showUnexpectedException(e)
            }
        } catch (e: Exception) {
            showUnexpectedException(e)
        }
        requestUserConfirmation("Retry?")
    }
}
