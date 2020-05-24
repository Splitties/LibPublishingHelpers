# LibPublishingHelpers

This is a library for library projects.

It is designed to be included as a dependency in a Kotlin 1.3.70+ script (e.g. `Releasing.main.kts`).

It brings helpers/tools to make a readable and maintainable releasing script, avoiding errors in the critical process of tagging and releasing a library.

## Why

Because releasing new versions of Splitties, refreshVersions and other library projects is a chore to me, and I want it to be `fun` instead.

## Status of the project

_For now, there's no release of the built library on a public repository as I'm experimenting locally._

While non dev releases are stable enough to be used safely enough (but at your own risk), the API is not stable, which means it might change from a release to another, requiring updates to the source code.

To check what's on the table for that project, check out the [TODO file](TODO.md).

## Features

_This may change over time._

### Running arbitrary commands

Example: `"ls -al".execute()` will run the `ls -al` command.

### Running VCS actions

Commit files, checkout a branch, pull, pull…

(only git is supported for now)

### CLI UI

Ansi Colors, abstraction of confirmations, questions with choices selection…

Type `CliUi.` in your IDE, and discover capabilities from autocomplete.

### Check user input

The `checkIsValidVersionString()` extension function for `String?` can help you ensure a valid version has been entered in a versioning + releasing script (outlawing non digital prefixes, illegal characters such as spaces, etc.).

### Interact with other apps

Open urls with `openUrl(…)`.

## License

This library is published under Apache License version 2.0 which you can see [here](LICENSE).
