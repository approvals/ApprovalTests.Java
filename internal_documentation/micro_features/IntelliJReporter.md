# IntelliJReporter

## Purpose
Automatically detects a running JetBrains IDE process and uses it to display diffs when an approval test fails — no manual configuration required.

## Behavior
1. **Scans running processes**: Uses `ProcessHandle` (via reflection for Java 8 compatibility) to list all running OS processes
2. **Identifies JetBrains IDEs**: Looks for known keywords in process paths (`idea`, `pycharm`, `webstorm`, `phpstorm`, `goland`, `rider`, `clion`, `rubymine`, `appcode`, `datagrip`)
3. **Verifies it's the main executable**: Checks that the path ends with `macos/<keyword>` or contains `bin/<keyword>` or `bin\<keyword>` to avoid matching helper processes
4. **Returns the path**: Returns the first matching executable path, or `""` if none found
5. **Falls back gracefully**: If no JetBrains IDE is running, the reporter path is empty and `GenericDiffReporter` will report itself as unavailable

## Usage Example
```java
// Use the singleton
Approvals.verify("text", new Options().withReporter(IntelliJReporter.INSTANCE));

// Or register as the default reporter
```

## Supported IDEs
- IntelliJ IDEA (`idea`)
- PyCharm (`pycharm`)
- WebStorm (`webstorm`)
- PhpStorm (`phpstorm`)
- GoLand (`goland`)
- Rider (`rider`)
- CLion (`clion`)
- RubyMine (`rubymine`)
- AppCode (`appcode`)
- DataGrip (`datagrip`)

## Diff Command
Uses `diff %s %s` with the detected IDE executable, passing the received file and approved file as arguments.

## Integration
`IntelliJReporter` is part of the reporter auto-detection chain. `findJetBrainsIdes(String[])` is public and testable independently of running processes.
