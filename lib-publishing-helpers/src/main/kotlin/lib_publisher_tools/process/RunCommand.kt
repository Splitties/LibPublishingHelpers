package lib_publisher_tools.process

import java.io.File
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes

private val executionDir = File(".")

class NonZeroExitCodeException(val value: Int) : Exception("Non zero exit value: $value")

fun String.execute(
    workingDir: File = executionDir,
    timeout: Duration = 60.minutes
): String {
    val proc = processBuilder(
        rawCommand = this,
        workingDir = workingDir
    ).start()
    proc.waitFor(timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    return proc.inputStream.use { it.bufferedReader().readText() }.also {
        val exitValue = proc.exitValue()
        if (exitValue != 0) {
            throw NonZeroExitCodeException(exitValue)
        }
    }
}

fun String.executeAndPrint(
    workingDir: File = executionDir,
    timeout: Duration = 60.minutes
) {
    val proc = processBuilder(rawCommand = this, workingDir = workingDir)
        .redirectInput(ProcessBuilder.Redirect.INHERIT)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
    proc.waitFor(timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    val exitValue = proc.exitValue()
    if (exitValue != 0) {
        throw NonZeroExitCodeException(exitValue)
    }
}

private val rawCommandPattern = Pattern.compile("\"([^\"]*)\"|(\\S+)")

private fun processBuilder(rawCommand: String, workingDir: File = executionDir): ProcessBuilder {
    val command = rawCommandPattern.matcher(rawCommand).let { m ->
        generateSequence {
            when {
                m.find() -> if (m.group(1) != null) m.group(1) else m.group(2)
                else -> null
            }
        }
    }.toList()
    return ProcessBuilder(command)
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
}
