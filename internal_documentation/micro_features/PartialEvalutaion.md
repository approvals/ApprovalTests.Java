# Partial Evaluation (Custom ApprovalTests Jar)

> Status: interface spec, plus a working first pass of the CLI (see [Current Implementation](#current-implementation-first-pass)).

Lets a user select the exact ApprovalTests entry points they use and build a custom jar that contains only the code reachable from those entry points. Everything else is removed by **Tree Shaking**.

## Purpose
Users want a trimmed ApprovalTests for any of these reasons:
- **Smaller jar**: less footprint.
- **Fewer dependency conflicts**: optional third-party libraries (Gson, Jackson, etc.) are only needed if a selected entry point uses them.
- **Smaller audit surface**: less code to review for security or license compliance.
- **Constrained runtimes**: Android, GraalVM native-image, and similar environments where unused code causes problems.

## Workflow
- **Develop** against the full ApprovalTests jar, as usual.
- **Release** with the partial jar. The CLI runs at release time, to build a jar containing only what your selection uses.

Discoverability is not a goal of the partial jar. Unused code is simply deleted, not replaced with helpful errors. While developing you have the full jar, so everything is available.

## Overview
The feature has two parts. They are connected by a single **selection config file**:

```
[Selector Page]  --writes-->  approvaltests-selection.json  --read by-->  [CLI]  --builds-->  custom jar (+ pom, + sources)
```

- The **Selector Page** is a static web page. It does not build anything.
- The **CLI** runs locally and does the build.
- The config file is meant to be committed to the user's repo, so the jar can be rebuilt (for example, after upgrading ApprovalTests).

## Concepts

### Entry Point
A specific public method overload, identified by its fully qualified signature:
```
org.approvaltests.Approvals.verify(java.lang.String)
org.approvaltests.Approvals.verify(java.lang.Object, org.approvaltests.core.Options)
org.approvaltests.JsonApprovals.verifyAsJson(java.lang.Object)
```
Selection is always at the **method/overload** level. Selecting `Approvals.verify(String)` does **not** select `Approvals.verify(Object)`.

A public static field can also be an entry point. It has no parentheses:
```
org.approvaltests.reporters.DiffReporter.INSTANCE
```

`Options` methods (scrubbers, file extensions, reporters, etc.) are ordinary entry points too:
```
org.approvaltests.core.Options.withScrubber(org.approvaltests.core.Scrubber)
org.approvaltests.core.Options.forFile()
```

### Tree Shaking
Code is removed by **Tree Shaking**:
1. Starting from the selected entry points (and the runtime discoveries set to `keep` or `remove`), find everything that is reachable.
2. Delete every method and class that is not reachable, including public methods on the API classes. Unselected entry points are deleted, not stubbed.
3. Third-party dependencies that only the deleted code used go away with it.

### Runtime Discoveries
Some behavior is triggered by the environment, reflection, or configuration, not by a direct call. Examples:
- the `CI` environment variable
- default reporter auto-detection, and each diff tool it can find
- the `PackageSettings` lookup
- other reflectively loaded pieces (namers, writers, etc.) as they are identified

Every runtime discovery is set **explicitly** to one of three modes. A discovery that is missing from the config is treated as `remove`.

The key `"*"` sets the mode for every discovery at once, e.g. `"*": "keep"`.

| Mode | Detection code | When the discovery is triggered |
|---|---|---|
| `keep` | Kept | It works normally. |
| `remove` | Kept | It throws a `RuntimeException` with a message on how to enable it. |
| `ignore` | Tree-shaken out | Nothing happens. The code is gone, so it behaves as if the discovery was never triggered. |

Example: the `CI` environment variable
- **keep**: it works.
- **remove**: it still checks, and throws an exception if `CI` is set in the environment.
- **ignore**: it doesn't check and doesn't care whether `CI` is set. That code is gone.

### Excluded Code Behavior
The partial jar keeps the same package names, class names and signatures for everything it contains. It is a drop-in replacement **only for code that uses what was selected**.

Anything not selected is simply gone:
- Code that calls it fails to compile against the partial jar.
- Code already compiled against the full jar fails with `NoSuchMethodError` / `NoClassDefFoundError`.

So the selection must cover everything your code calls. Run your tests against the partial jar before you release. The [codebase detection tool](#future-work) is meant to make the selection complete automatically.

A runtime discovery set to `remove` still throws a `RuntimeException` that explains how to enable it:
```
Runtime discovery "environment.CI" is set to remove in this partial evaluation of ApprovalTests, and the CI environment variable is set.
To enable it, set it in "runtimeDiscoveries" in approvaltests-selection.json and rebuild:
  "environment.CI": "keep"
Or use "ignore" to not check for it at all.
```

## Selector Page
- It lists every available entry point, grouped by class (`Approvals`, `JsonApprovals`, `CombinationApprovals`, `Options`, ...).
- It lists runtime discoveries in their own section. Each one has a `keep` / `remove` / `ignore` choice, and the default is `remove`.
- It has search and filter by name.
- Selecting a method shows what else it pulls in: the classes it reaches and any third-party dependencies (e.g. "requires Gson").
- It shows a running summary of the selection: number of entry points, estimated size, and required third-party dependencies.
- It can **load** an existing config file to edit it.
- It can **export** the config file (download or copy).
- The list of entry points is generated per ApprovalTests version.

## Selection Config File
Default name: `approvaltests-selection.json`

```json
{
  "approvaltestsVersion": "31.0.0",
  "entryPoints": [
    "org.approvaltests.Approvals.verify(java.lang.String)",
    "org.approvaltests.Approvals.verify(java.lang.Object, org.approvaltests.core.Options)",
    "org.approvaltests.JsonApprovals.verifyAsJson(java.lang.Object)",
    "org.approvaltests.core.Options.withScrubber(org.approvaltests.core.Scrubber)"
  ],
  "runtimeDiscoveries": {
    "environment.CI": "keep",
    "packageSettings": "keep",
    "reporters.autoDetection": "keep",
    "reporters.DiffReporter": "keep",
    "reporters.QuietReporter": "keep",
    "reporters.IntelliJReporter": "ignore"
  },
  "output": {
    "directory": "build/approvaltests-custom",
    "version": "31.0.0-custom",
    "pom": true,
    "sources": true,
    "install": "none"
  }
}
```

## CLI
The CLI is a **standalone** executable jar. It is not a Maven or Gradle plugin.

### Source of ApprovalTests Code
The CLI does not bundle ApprovalTests. For each run it:
1. Gets a checkout of `https://github.com/approvals/ApprovalTests.Java.git` at `approvaltests-partial/.local/approvaltests-<version>/`:
   - If that folder doesn't exist yet, it shallow-clones the tag that matches `approvaltestsVersion` (tags are plain version numbers, e.g. `31.0.0`).
   - If it already exists, it fetches the tag, then force-checks it out, `git reset --hard` and `git clean`. It does not clone again.
   - With `--current`, it uses the latest `master` instead of the tag, at `approvaltests-partial/.local/approvaltests-master/`. The report shows the commit, e.g. `master@63044b7`, and the jar is named `approvaltests-master-custom.jar`.
2. Applies Tree Shaking to that checkout. The checkout is kept, so `git -C approvaltests-partial/.local/approvaltests-<version> diff` shows exactly what changed.
3. Builds the custom jar from that checkout.

Because of this, one CLI can build any ApprovalTests version, and the config version never has to match the CLI's version.

```
approvaltests-partial build   --config approvaltests-selection.json [output flags]
approvaltests-partial list    [--version 31.0.0] [--format text|json]
approvaltests-partial explain --config approvaltests-selection.json [--entry-point <signature>]
```

### Commands
- **build**: Reads the config file and produces the custom jar using Tree Shaking.
- **list**: Prints all available entry points and runtime discoveries for a version. The Selector Page is built from the same data.
- **explain**: Shows why each class or dependency is included, i.e. which entry point reaches it. Useful for audits and for trimming a selection further.

### Output Flags
Output is configurable through flags or the `output` section of the config file. Flags override the config file.

| Flag | Description |
|---|---|
| `--output <dir>` | Output directory |
| `--artifact-version <v>` | Version string for the custom artifact (default `<approvaltestsVersion>-custom`) |
| `--pom` / `--no-pom` | Generate a POM that lists only the third-party dependencies actually needed |
| `--sources` / `--no-sources` | Also produce a `-sources.jar` for the included code |
| `--install none\|maven-local` | Optionally install into the local Maven repository |

### Outputs
All outputs go into `output.directory`, which is resolved relative to the config file.
- `approvaltests-<version>.jar`: `approvaltests` and `approvaltests-util` merged into one jar. As in the regular ApprovalTests jar, the `.java` source files for the included classes sit next to the `.class` files.
- `approvaltests-<version>.pom` (optional)
- `approvaltests-<version>-sources.jar` (optional)
- `approvaltests-<version>-report.html`: an HTML report comparing the partial build to the original (see [Report](#report)).

### Report
The HTML report contains:
- **Summary**: source files kept / deleted / changed / untouched, lines of source kept, compiled `.class` files and bytes, jar size, and third-party packages still imported. Each one is compared to the original.
- **Inputs**: the selection file and its location, the ApprovalTests version and tag, the selected entry points in readable form (`Approvals.verify(String)`), the runtime discoveries with what each mode means, the output location, and the raw JSON.
- **By module** and **By package**: files and lines before and after, with a kept/removed bar.
- **Third-party dependencies**: each third-party package imported by the original, and whether it's still referenced.
- **Changed files**: every modified file, sorted by lines removed, with an expandable diff against the original.
- **Deleted files**: grouped by package.

## Current Implementation (first pass)
Lives in `approvaltests-partial/`, a standalone Maven module (Java 17). It is not part of the main reactor build.

### Running It
From your project folder (the one with `approvaltests-selection.json`):
```bash
path/to/ApprovalTests.Java/approvaltests-partial/CreatePartailApprovals.sh [--current] [approvaltests-selection.json]
path/to/ApprovalTests.Java/approvaltests-partial/CreatePartailApprovals.sh explain [--current] <class or package prefix> [approvaltests-selection.json]
```
- The config file defaults to `approvaltests-selection.json` in the current folder.
- The script compiles the CLI on every run (about 3 seconds), so it never runs a stale build.
- `--current` builds from the latest `master` on GitHub instead of the `approvaltestsVersion` tag. Use it to try changes to ApprovalTests before they are released.
- `explain` prints, for every kept class matching the prefix, the chain of reasons back to an entry point.
- All projects share checkouts in `approvaltests-partial/.local/` (set with `-Dapprovaltests.partial.local=<dir>`), so no `.local/` folder appears in your project. `CheckedExceptionsTest` scans the repo for ApprovalTests sources, so it skips `.local` folders to avoid picking up the shaken copies.
- `list`, the output flags, and the `pom` / `sources` / `install` settings are not implemented yet.

Build and test the tool itself with `approvaltests-partial/build_and_test.sh`.

### Build Pipeline
1. Check out the tag (clone, or fetch and reset).
2. Build the original `approvaltests-util` and `approvaltests` jars, to get the baseline for the report.
3. Snapshot the original sources, also for the report.
4. Tree Shaking (`TreeShaker`).
5. Build again with `-Dmaven.test.skip=true`, since the shaken code no longer supports the repo's own tests.
6. Merge both jars into `approvaltests-<version>.jar` (`JarBuilder`), and write the HTML report (`HtmlReport`).

For the StarterProject (`Approvals.verify(String)`, `JsonApprovals.verifyAsJson(Object)` and `DiffReporter.INSTANCE`), a run takes about 13 seconds. The result is 231 of 448 files, 44% of the source lines and a 451 KB jar (48% of the original 948 KB), and the shaken code compiles.

### How Tree Shaking Works
Tree Shaking works on the source code, using JavaParser and its symbol solver. Runtime discoveries are all treated as `keep` for now.

**Mark** (find what's reachable, starting from the entry points):
- Method calls are resolved with the symbol solver. If a call can't be resolved (e.g. an argument comes from a third-party library), every method with that name and number of arguments is kept, to be safe. This is limited to the class the call names, e.g. `Approvals.verify(...)` only keeps `Approvals.verify` overloads. A call with no class prefix uses the enclosing classes and static imports. If nothing matches there, it falls back to keeping by name everywhere.
- When a call passes a lambda or method reference, every overload with the same name and number of arguments in that class is kept. The symbol solver sometimes picks the wrong overload for lambdas.
- Any type referenced from reachable code is kept, along with all its fields, initializers and constructors.
- Instance methods of kept types are kept if they are abstract, `@Override`, implement an inherited abstract method, have a name that is called anywhere, or belong to a type with a third-party supertype.
- A string literal that equals one of our fully qualified class names keeps that class. This is the stand-in for reflection.

**Sweep** (remove what isn't reachable):
- Files with no kept types are deleted.
- Every unreached method and nested type is deleted, including unselected public methods on API classes like `Approvals`. Nothing is stubbed.
- Imports pointing at removed types or members are removed.
- Files are rewritten with formatting preserved, so the diff stays minimal.

### Known Gaps
- **Too many dependencies are kept.** Of 16 third-party packages, 9 are still imported (Gson, servlet, Apache, JUnit, Quartz, SLF4J, ...). This comes from the safe choices above: unresolved calls kept by name, and all fields and constructors of kept classes kept.
- **Computed class names are missed.** Reflection by class names built at runtime (e.g. reporters from `APPROVAL_TESTS_USE_REPORTER`) isn't detected.
- **Runtime behavior is untested.** The partial jar compiles but hasn't been run against the StarterProject's tests yet.

## Future Work
- **Codebase detection tool**: scans a user's codebase (source or compiled tests) to find which ApprovalTests entry points and reporters are used, then pre-fills the Selector Page or the config file.
