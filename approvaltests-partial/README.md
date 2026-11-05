# approvaltests-partial

Builds a slimmed-down ApprovalTests jar that contains only the code your project actually uses.

You develop against the full ApprovalTests jar as usual. When you release, this tool builds a custom jar from your usages and removes everything else by **Tree Shaking**. For the [StarterProject](https://github.com/approvals/ApprovalTests.java.StarterProject) on 31.0.0, the jar goes from 948 KB to 252 KB, and Jackson, XStream, Quartz and others drop out. On the latest `master` it's 223 KB, and Velocity is gone too. See the [sample report](docs/sample-report.html).

## Requirements

- Java 17+ and Maven, to run the tool
- `git`, plus network access to clone [ApprovalTests.Java](https://github.com/approvals/ApprovalTests.Java)
- A Maven project (`pom.xml`) for the project you want to slim down

## How To Use It

Run these from **your project's folder**. The paths below assume ApprovalTests.Java is cloned next to your project.

### 1. Create the selection file

```bash
../ApprovalTests.Java/approvaltests-partial/CreateSelectionJson.sh
```

This scans every `.java` file from the current folder down and writes `approvaltests-selection.json`. That file lists the ApprovalTests version and every method, constructor, field and class you use. Commit it with your project.

```json
{
  "approvaltestsVersion": "31.0.0",
  "entryPoints": [
    "org.approvaltests.Approvals.verify(java.lang.String)",
    "org.approvaltests.JsonApprovals.verifyAsJson(java.lang.Object)",
    "org.approvaltests.reporters.DiffReporter.INSTANCE",
    "..."
  ],
  "runtimeDiscoveries": { "*": "keep" },
  "output": { "directory": "target/approvaltests-custom", "version": "31.0.0-custom", "...": "..." }
}
```

Check anything listed under "Could not resolve" by hand. Rerunning it later updates the version and entry points, and keeps your `runtimeDiscoveries` and `output` settings.

### 2. Build the partial jar

```bash
../ApprovalTests.Java/approvaltests-partial/CreatePartailApprovals.sh
```

This clones ApprovalTests at your version's tag, removes everything your selection doesn't reach, and builds. It writes these to `target/approvaltests-custom/`:
- `approvaltests-<version>-custom.jar`: `approvaltests` and `approvaltests-util` in one jar (sources included)
- `approvaltests-<version>-custom-report.html`: what was removed and why (see the [sample report](docs/sample-report.html))

### 3. Test against the partial jar

Run your tests with the partial jar in place of the real ones:

```bash
mvn test -Dmaven.test.dependency.excludes=com.approvaltests:approvaltests,com.approvaltests:approvaltests-util \
         -Dmaven.test.additionalClasspath=$PWD/target/approvaltests-custom/approvaltests-31.0.0-custom.jar
```

If something is missing, it fails with `NoSuchMethodError` or `NoClassDefFoundError`. Add the missing entry point to `approvaltests-selection.json` and rebuild.

## More Commands

| Command | What it does |
|---|---|
| `CreatePartailApprovals.sh --current` | Builds from the latest `master` on GitHub instead of the release tag. Use it to try fixes before they are released. |
| `CreatePartailApprovals.sh explain <class or package>` | Prints why each matching class was kept, as a chain back to one of your entry points. |
| `CreatePartailApprovals.sh explain --current <class or package>` | The same, against the latest `master`. |

Example:

```text
$ CreatePartailApprovals.sh explain com.spun.util.database.ResultSetWriter
com.spun.util.database.ResultSetWriter is kept because:
    com.spun.util.database.ResultSetWriter <- referenced in its body
    org.approvaltests.writers.ResultSetApprovalWriter.writeReceivedFile(File) <- it is @Override
    ...
    org.approvaltests.JsonApprovals.verifyAsJson(Object) <- entry point org.approvaltests.JsonApprovals.verifyAsJson(java.lang.Object)
```

## Good To Know

- **Unused code is deleted, not stubbed.** The partial jar is a drop-in replacement only for what you selected. Develop against the full jar, and release with the partial one.
- **Imports count as usage.** Java needs every imported class to exist, so the detector includes unused imports. Remove them if you don't want those classes.
- **Runtime discoveries are all kept for now** (`"*": "keep"`), e.g. reporter auto-detection and test-framework detection.
- **Where the code is:** the ApprovalTests checkout lives in `approvaltests-partial/.local/` (gitignored). Run `git -C .local/approvaltests-<version> diff` to see exactly what was removed.
- **Gradle** projects aren't supported by `CreateSelectionJson.sh` yet.

## Working On The Tool

- `./build_and_test.sh` builds and tests this module. It's a standalone Maven module, not part of the main ApprovalTests build.
- [PartialEvalutaion.md](../internal_documentation/micro_features/PartialEvalutaion.md) is the spec and the current implementation notes.
- [PartialEvalutaionArchitecture.md](../internal_documentation/micro_features/PartialEvalutaionArchitecture.md) has the architecture diagrams.
