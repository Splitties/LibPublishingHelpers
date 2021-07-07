package lib_publisher_tools.open

import lib_publisher_tools.process.execute

fun openUrl(url: String) {
    val osName = System.getProperty("os.name").lowercase()
    val isMacOs: Boolean = "mac" in osName
    val command = if (isMacOs) "open $url" else {
        val isWindows: Boolean = "win" in osName
        if (isWindows) {
            """start "" "$url""""
        } else "xdg-open $url"
    }
    command.execute()
}
