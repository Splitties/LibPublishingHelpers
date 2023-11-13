# Version sync

Synchronizes the `version` in multi-module projects, and allows to also generate code with the version inside.

## The 2 things this Gradle plugin does

### 1. Set version to all modules/projects from a single file

By default, the version is retrieved from the first line of the `version.txt` at the root of the project,
and it set to all the modules/projects (in the `version` property that all Gradle modules/projects have).

It is possible to override the version in a given project if needed.

You can also change the version file like this:

```gradle.kts
versionSync { versionFile = file("library-version.txt") }
```

#### Why a version **file**?

The reason we put the version in a plain text file is because it allows it to be used as CI triggers too,
and any tool can easily read it without having to parse Kotlin or Groovy based Gradle files.

### 2. Allow embedding the version in the code of a project

A Gradle plugin, or anything you make might need to know its own version.

For example, the SqlDelight Gradle plugin needs to know its version for driver dependencies for host/end app projects. ([This is how they have been doing it](https://github.com/cashapp/sqldelight/blob/2e76f949c7b333fa9ae62d0525f27d7bb8a10092/sqldelight-compiler/build.gradle#L58-L93))

With the Version sync Gradle plugin, you can do it easily from your `build.gradle.kts` file:

```gradle.kts
import org.splitties.gradle.putVersionInCode

// Rest of your build code...

sourceSets { main { kotlin.srcDir("build/gen") } } // Add custom directory for generated code

putVersionInCode(
    outputDirectory = layout.dir(provider { file("build/gen") }), // Output in the same custom directory
    writer = VersionFileWriter.Kotlin(
        fileName = "Version.kt",
        `package` = "splitties.wff",
        // Optional `public` parameter (defaults to `false`, generating `internal` visibility).
        propertyName = "thisProjectVersion",
        const = true // Defaults to false. Recommended to be left to false if made public.
    )
)
```

## How to add to your project

Go in the `settings.gradle.kts` file, located at the root of your Gradle project.

Add the following line into the `plugins { … }` block (add one at the top of the file it there's none):

```gradle.kts
    id("org.splitties.version-sync") version "0.2.6"
```
