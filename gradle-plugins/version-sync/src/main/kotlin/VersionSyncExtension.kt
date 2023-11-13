package org.splitties.gradle

import org.gradle.api.provider.Property
import java.io.File

interface VersionSyncExtension {
    val versionFile: Property<File>
}
