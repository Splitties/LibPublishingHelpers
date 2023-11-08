package org.splitties.incubator.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType

@Suppress("unused") // referenced in build.gradle.kts
class VersionSyncPlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
        target.extensions.create<VersionSyncExtension>(name = "versionSync")
        target.gradle.settingsEvaluated {
            val extension = extensions.getByType<VersionSyncExtension>()
            val version = extension.versionFile.convention(rootDir.resolve("version.txt")).map { file ->
                file.bufferedReader().use { it.readLine() }
            }
            gradle.beforeProject {
                project.version = version.get()
            }
        }
        //TODO: Register extension or whatever to allow generating a property with:
        // - given visibility (default to, if any?)
        // - given name (default to, if any?)
        // - given package
    }
}
