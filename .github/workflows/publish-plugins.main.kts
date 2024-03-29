#!/usr/bin/env kotlin

@file:DependsOn("io.github.typesafegithub:github-workflows-kt:1.5.0")

import io.github.typesafegithub.workflows.actions.actions.CheckoutV4
import io.github.typesafegithub.workflows.actions.actions.SetupJavaV3
import io.github.typesafegithub.workflows.actions.gradle.GradleBuildActionV2
import io.github.typesafegithub.workflows.domain.RunnerType
import io.github.typesafegithub.workflows.domain.triggers.PullRequest
import io.github.typesafegithub.workflows.domain.triggers.WorkflowDispatch
import io.github.typesafegithub.workflows.dsl.JobBuilder
import io.github.typesafegithub.workflows.dsl.expressions.expr
import io.github.typesafegithub.workflows.dsl.workflow
import io.github.typesafegithub.workflows.yaml.writeToFile

// GitHub Action runners preinstalled software: https://docs.github.com/en/actions/using-github-hosted-runners/about-github-hosted-runners/about-github-hosted-runners#preinstalled-software

workflow(
    name = "Publish plugins",
    on = listOf(
        manualTrigger(),
        prOpened(branch = "release", "version.txt")
    ),
    sourceFile = __FILE__.toPath()
) {
    job(id = "gradle-plugins-publishing", runsOn = RunnerType.UbuntuLatest) {
        checkout()
        setupJava()
        gradle(
            task = "publishPlugins",
            properties = mapOf(
                "gradle.publish.key" to expr { secrets["gradle_publish_key"]!! },
                "gradle.publish.secret" to expr { secrets["gradle_publish_secret"]!! }
            )
        )


    }
}.writeToFile()

//region Supporting functions

// https://github.com/gradle/gradle-build-action
fun JobBuilder<*>.gradle(
    gradleExecutable: String? = null,
    task: String,
    scan: Boolean = false,
    properties: Map<String, String>
) = uses(
    action = GradleBuildActionV2(
        gradleExecutable = gradleExecutable,
        arguments = buildList {
            add(task)
            if (scan) add("--scan")
            properties.forEach { (key, value) ->
                add("-P$key=$value")
            }
        }.joinToString(separator = " ")
    )
)

fun manualTrigger() = WorkflowDispatch()

fun prOpened(
    branch: String,
    vararg triggerFiles: String
): PullRequest = PullRequest(
    types = listOf(PullRequest.Type.Opened),
    paths = triggerFiles.asList(),
    branches = listOf(branch)
)

// https://github.com/actions/checkout
fun JobBuilder<*>.checkout() = uses(action = CheckoutV4())

// https://github.com/actions/setup-java
fun JobBuilder<*>.setupJava(version: String = "17") = uses(
    action = SetupJavaV3(
        distribution = SetupJavaV3.Distribution.Temurin,
        javaVersion = version
    )
)

//endregion
