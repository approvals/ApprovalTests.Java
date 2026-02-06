# ApprovalTests.Java Agent Instructions

## Build & Test
- **Full build:** `./build_and_test` (takes 1-2 min; auto-formats code via Maven formatter plugin)
- **Single test:** `mvn test -pl approvaltests-tests -Dtest=ClassName#methodName`
- Tests run with `en_US` locale (surefire config). Java target is 1.8.

## Architecture
Multi-module Maven project: **approvaltests** (core library), **approvaltests-util** (shared utilities), **approvaltests-tests** and **approvaltests-util-tests** (tests). Core API: `org.approvaltests.Approvals` with `verify()`, `verifyAll()`, `verifyAsJson()`. Pattern: Writers (generate output) → Namers (file naming) → Reporters (show diffs on failure).

## Key Rules
- **NEVER** approve a test (update `.approved.*` files) automatically — always prompt the user. Commit `.approved.*` files; never commit `.received.*` files.
- Make file changes directly without asking permission (repo is under git, user can revert).
- The build auto-formats code — do not worry about formatting.

## Code Style
- Allman brace style (opening brace on new line). camelCase methods, PascalCase classes.
- Uses custom `org.lambda` utilities over Java Streams in core code.
- Heavy use of `public static` overloaded methods for the main API.
- Do not add unnecessary comments. Follow existing patterns in neighboring files.
