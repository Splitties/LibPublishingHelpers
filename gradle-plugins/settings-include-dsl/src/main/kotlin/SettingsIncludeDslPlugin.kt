package org.splitties.incubator.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

@Suppress("unused") // referenced in build.gradle.kts
class SettingsIncludeDslPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = Unit
}
