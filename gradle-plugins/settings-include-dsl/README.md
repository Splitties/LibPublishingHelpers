# Settings include DSL

## Example

Write this:

```.gradle.kts
include {
    "someProject" {
        "thingA"()
        "thingB"()
    }
    "whatever"()
}
```

Instead of that:

```gradle.kts
include("someProject:thingA")
include("someProject:thingB")
include("whatever")
```

## How to add to your project

Go in the `settings.gradle.kts` file, located at the root of your Gradle project.

Add the following line into the `plugins { … }` block (add one at the top of the file it there's none):

```gradle.kts
    id("org.splitties.settings-include-dsl") version "0.2.6"
```
