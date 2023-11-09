package org.splitties.incubator.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register
import org.splitties.incubator.gradle.VersionFileWriter.Kotlin.Visibility.Internal
import org.splitties.incubator.gradle.VersionFileWriter.Kotlin.Visibility.Public
import java.io.Serializable

fun Project.putVersionInCode(
    taskName: String = "syncVersion",
    outputDirectory: Provider<Directory>,
    writer: VersionFileWriter,
    vararg triggerTasks: TaskProvider<*>,
) {
    val syncVersionTask = tasks.register<FileWriterTask>(taskName) {
        version.set(project.version.toString())
        this.outputDirectory.set(outputDirectory)
        this.writer.set(writer)
    }
    triggerTasks.forEach {
        it.configure { dependsOn(syncVersionTask) }
    }
    if (writer.exists(outputDirectory.get()).not()) {
        // If there's no file at all, write it now.
        //TODO: Revisit since property name and value can drift out of sync until triggerTasks run.
        writer.write(project.version.toString(), outputDirectory.get())
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

    fun exists(outputDir: Directory): Boolean {
        val file = outputDir.file(fileName).asFile
        return file.exists()
    }

    class Kotlin(
        fileName: String,
        private val `package`: String,
        private val visibility: Visibility = Internal,
        private val propertyName: String,
        private val const: Boolean = false
    ) : VersionFileWriter(fileName), Serializable {
        enum class Visibility {
            Internal, Public
        }

        init {
            check(fileName.endsWith(".kt"))
        }

        override fun _write(version: String, outputDir: Directory) {
            val kotlinCode = generate(version)
            outputDir.file(fileName).asFile.writeText(kotlinCode)
        }

        fun generate(version: String): String {
            val keywords = buildString {
                when (visibility) {
                    Internal -> append("internal")
                    Public -> append("public")
                }
                append(if (const) " const val" else " val")
            }
            return """
                    package $`package`
                    
                    $keywords $propertyName = "$version"
                    
                """.trimIndent()
        }
    }
}

internal abstract class FileWriterTask : DefaultTask() {

    @get:Input
    abstract val writer: Property<VersionFileWriter>

    @get:Input
    abstract val version: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun writeVersionFileIntoDirectory() {
        writer.get().write(version.get(), outputDirectory.get())
    }
}
