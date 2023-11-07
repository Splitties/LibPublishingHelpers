package org.gradle.kotlin.dsl

import org.gradle.api.initialization.Settings
import org.splitties.incubator.gradle.ModuleParentScope

inline fun Settings.include(block: ModuleParentScope.() -> Unit) {
    ModuleParentScope(settings = this, name = "").block()
}
