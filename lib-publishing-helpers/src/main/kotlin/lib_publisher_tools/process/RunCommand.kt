package lib_publisher_tools.process

import java.io.File
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.time.*

private val executionDir = File(".")

@OptIn(ExperimentalTime::class)
fun String.execute(
    workingDir: File = executionDir,
    @ExperimentalTime
    timeout: Duration = Duration.minutes(60)
): String {
    val proc = processBuilder(
        rawCommand = this,
        workingDir = workingDir
    ).start()
    proc.waitFor(timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    return proc.inputStream.use { it.bufferedReader().readText() }.also {
        val exitValue = proc.exitValue()
        if (exitValue != 0) {
            throw Exception("Non zero exit value: $exitValue")
        }
    }
}

@OptIn(ExperimentalTime::class)
fun String.executeAndPrint(
    workingDir: File = executionDir,
    @ExperimentalTime
    timeout: Duration = Duration.minutes(60)
) {
    val proc = processBuilder(rawCommand = this, workingDir = workingDir)
        .redirectInput(ProcessBuilder.Redirect.INHERIT)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
    proc.waitFor(timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    val exitValue = proc.exitValue()
    if (exitValue != 0) {
        throw Exception("Non zero exit value: $exitValue")
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
