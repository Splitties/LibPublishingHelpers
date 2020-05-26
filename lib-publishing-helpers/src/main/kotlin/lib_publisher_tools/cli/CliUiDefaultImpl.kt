package lib_publisher_tools.cli

import java.util.concurrent.CancellationException

val CliUi.Companion.defaultImpl: CliUi get() = CliUiDefaultImpl

private object CliUiDefaultImpl : CliUi {

    override fun printInfo(message: String) {
        print(AnsiColor.WHITE.boldHighIntensity)
        print(AnsiColor.GREEN.background)
        print(message)
        println(AnsiColor.RESET)
    }

    override fun printQuestion(message: String) {
        print(AnsiColor.WHITE.boldHighIntensity)
        print(AnsiColor.BLUE.background)
        print(message)
        println(AnsiColor.RESET)
    }

    override fun requestManualAction(instructions: String) {
        print(AnsiColor.WHITE.boldHighIntensity)
        print(AnsiColor.BLUE.background)
        print(instructions)
        println(AnsiColor.RESET)
        requestUserConfirmation("Done?")
    }

    override fun requestUserConfirmation(yesNoQuestion: String) {
        print(AnsiColor.WHITE.boldHighIntensity)
        print(AnsiColor.BLUE.background)
        print("$yesNoQuestion Y/n")
        println(AnsiColor.RESET)
        readLine()?.trimEnd().let { input ->
            if (input != "Y" && !"yes".equals(input, ignoreCase = true)) {
                println("Process aborted."); throw CancellationException()
            }
        }
    }

    override fun warn(message: String) {
        print(AnsiColor.WHITE.boldHighIntensity)
        print(AnsiColor.YELLOW.backgroundHighIntensity)
        print(message)
        println(AnsiColor.RESET)
    }

    override fun showError(message: String) {
        System.err.println(message)
    }

    override fun showUnexpectedException(e: Exception) {
        System.err.println(e)
    }
}
