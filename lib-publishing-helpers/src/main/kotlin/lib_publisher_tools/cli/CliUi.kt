package lib_publisher_tools.cli

interface CliUi {
    companion object;

    fun printInfo(message: String)
    fun printQuestion(message: String)
    fun requestManualAction(instructions: String)
    fun requestUserConfirmation(yesNoQuestion: String)
}
