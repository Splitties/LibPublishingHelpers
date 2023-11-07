package org.splitties.incubator.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class SettingsIncludeDslPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = Unit
}
