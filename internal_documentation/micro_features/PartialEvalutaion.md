# Partial Evaluation (Custom ApprovalTests Jar)

> Status: brainstorm / interface spec only. Implementation is intentionally not covered yet.

Lets a user select the exact ApprovalTests entry points they use and build a custom jar that contains only the code reachable from those entry points.

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

### Runtime Components
Some code is reached through reflection or configuration, not through direct calls. These are also selected explicitly:
- **Reporters**: e.g. `DiffReporter`, `QuietReporter`, `AutoApproveReporter`, specific diff tools.
- **Default reporter auto-detection**: whether to include the "first working reporter" discovery and which candidates it may consider.
- **PackageSettings** lookup.
- Other reflectively loaded pieces (namers, writers, etc.) as they are identified.

### Excluded Code Behavior
The custom jar is a **drop-in replacement**:
- It uses the same package names, class names, and public method signatures.
- User code compiles unchanged against it.

Calling an entry point or runtime component that was not selected fails **at runtime** with a clear message, never with a `NoClassDefFoundError`:
```
org.approvaltests.Approvals.verifyAll(java.lang.String, java.lang.Object[]) is not included in this custom build of ApprovalTests.
Add it to approvaltests-selection.json and rebuild, or use the full ApprovalTests jar.
```

## Selector Page
- It lists every available entry point, grouped by class (`Approvals`, `JsonApprovals`, `CombinationApprovals`, `Options`, ...).
- It lists runtime components (reporters, etc.) in their own section.
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
  "runtimeComponents": {
    "reporters": [
      "org.approvaltests.reporters.DiffReporter",
      "org.approvaltests.reporters.QuietReporter"
    ],
    "defaultReporterAutoDetection": true,
    "packageSettings": true
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
- **build**: Reads the config file and produces the custom jar.
- **list**: Prints all available entry points and runtime components for a version. The Selector Page is built from the same data.
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
