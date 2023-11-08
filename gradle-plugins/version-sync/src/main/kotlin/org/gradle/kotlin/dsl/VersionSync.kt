package org.gradle.kotlin.dsl

import org.gradle.api.initialization.Settings
import org.splitties.incubator.gradle.VersionSyncExtension

fun Settings.versionSync(configure: VersionSyncExtension.() -> Unit) {
    extensions.getByType<VersionSyncExtension>().configure()
}
