package org.splitties.incubator.gradle

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.named
import java.io.File
import java.io.Serializable

fun Project.putVersionInCode(
    outputDirectory: Provider<Directory>,
    writer: VersionFileWriter,
) {
    val version = project.version.toString()
    tasks.named<Delete>("clean").configure {
        doLast { writer.write(version, outputDirectory.get()) }
    }
    val file = writer.targetFile(outputDirectory.get())
    // We write the file as soon as possible, so it's visible in the IDE as soon as possible.
    // This is compatible with Gradle configuration cache.
    if (file.exists().not()) {
        writer.write(version, outputDirectory.get())
        return
    }
    val existingCode = file.readText()
    val newCode = writer.generate(version)
    if (newCode != existingCode) {
        outputDirectory.get().asFile.mkdirs()
        file.writeText(newCode)
        return
    }
}

abstract class VersionFileWriter(
    protected val fileName: String
) {
    fun write(version: String, outputDir: Directory) {
        val dir = outputDir.asFile
        if (dir.exists().not()) dir.mkdirs()
        _write(version, outputDir)
    }
    protected abstract fun _write(version: String, outputDir: Directory)

    abstract fun generate(version: String): String

    fun targetFile(outputDir: Directory): File {
        return outputDir.file(fileName).asFile
    }

    class Kotlin(
        fileName: String,
        private val `package`: String,
        private val public: Boolean = false,
        private val propertyName: String,
        private val const: Boolean = false
    ) : VersionFileWriter(fileName), Serializable {

        init {
            check(fileName.endsWith(".kt"))
        }

        override fun _write(version: String, outputDir: Directory) {
            val kotlinCode = generate(version)
            outputDir.file(fileName).asFile.writeText(kotlinCode)
        }

        override fun generate(version: String): String {
            val keywords = buildString {
                append(if (public) "public " else "internal ")
                append(if (const) "const val" else "val")
            }
            return """
                    package $`package`
                    
                    $keywords $propertyName = "$version"
                    
                """.trimIndent()
        }
    }
}
