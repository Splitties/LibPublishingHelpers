package lib_publisher_tools.clipboard

import lib_publisher_tools.process.execute

fun String.copyToClipboard() {
    val escapedText = this.escape(charsToEscape = "\"`$")

    val osName = System.getProperty("os.name").lowercase()
    val isMacOs: Boolean = "mac" in osName
    val isWindows: Boolean = "win" in osName
    when {
        isMacOs -> listOf("sh", "-c", "echo \"$escapedText\" | pbcopy")
        isWindows -> listOf("cmd", "/C", "echo \"$escapedText\" | clip") //TODO: Test that
        else -> listOf("sh", "-c", "echo \"$escapedText\" | xclip -sel clip") //TODO: Test that
    }.execute()
}

private fun String.escape(charsToEscape: String): String = buildString {
    append(this@escape)
    for (i in lastIndex downTo 0) {
        val c = this[i]
        if (c in charsToEscape) insert(i, '\\')
    }
}
