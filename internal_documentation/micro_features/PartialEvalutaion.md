# Partial Evaluation (Custom ApprovalTests Jar)

> Status: brainstorm / interface spec only. Implementation is intentionally not covered yet.

Lets a user select the exact ApprovalTests entry points they use and build a custom jar that contains only the code reachable from those entry points. Everything else is removed by **Tree Shaking**.

## Purpose
Users want a trimmed ApprovalTests for any of these reasons:
- **Smaller jar**: less footprint.
- **Fewer dependency conflicts**: optional third-party libraries (Gson, Jackson, etc.) are only needed if a selected entry point uses them.
- **Smaller audit surface**: less code to review for security or license compliance.
- **Constrained runtimes**: Android, GraalVM native-image, and similar environments where unused code causes problems.

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

`Options` methods (scrubbers, file extensions, reporters, etc.) are ordinary entry points too:
```
org.approvaltests.core.Options.withScrubber(org.approvaltests.core.Scrubber)
org.approvaltests.core.Options.forFile()
```

### Tree Shaking
Code is removed by **Tree Shaking**:
1. Every entry point that was **not** selected has its body replaced with a stub that throws a `RuntimeException`. The message explains how to enable it in the config (see [Excluded Code Behavior](#excluded-code-behavior)).
2. Every runtime discovery set to `remove` or `ignore` is handled as described in [Runtime Discoveries](#runtime-discoveries).
3. Starting from what is still reachable, any code nothing reaches anymore (classes, methods, and third-party dependencies) is shaken out of the jar.

### Runtime Discoveries
Some behavior is triggered by the environment, reflection, or configuration, not by a direct call. Examples:
- the `CI` environment variable
- default reporter auto-detection, and each diff tool it can find
- the `PackageSettings` lookup
- other reflectively loaded pieces (namers, writers, etc.) as they are identified

Every runtime discovery is set **explicitly** to one of three modes. A discovery that is missing from the config is treated as `remove`.

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
The custom jar is a **drop-in replacement**:
- It uses the same package names, class names, and public method signatures.
- User code compiles unchanged against it.

Calling an unselected entry point, or triggering a runtime discovery set to `remove`, throws a `RuntimeException` that explains how to enable it in the config. It never fails with a `NoClassDefFoundError`:
```
org.approvaltests.Approvals.verifyAll(java.lang.String, java.lang.Object[]) is not included in this partial evaluation of ApprovalTests.
To enable it, add this to "entryPoints" in approvaltests-selection.json and rebuild:
  "org.approvaltests.Approvals.verifyAll(java.lang.String, java.lang.Object[])"
```
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
1. Clones `https://github.com/approvals/ApprovalTests.Java.git` into a temp folder.
2. Checks out the tag that matches `approvaltestsVersion` (tags are plain version numbers, e.g. `31.0.0`).
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
- `approvaltests-<version>.jar`: `approvaltests` and `approvaltests-util` merged into one jar. As in the regular ApprovalTests jar, the `.java` source files for the included classes sit next to the `.class` files.
- `approvaltests-<version>.pom` (optional)
- `approvaltests-<version>-sources.jar` (optional)
- A build report (included entry points, included classes, required dependencies, size compared to the full jar).

## Future Work
- **Codebase detection tool**: scans a user's codebase (source or compiled tests) to find which ApprovalTests entry points and reporters are used, then pre-fills the Selector Page or the config file.
