# AI Coding Agent Reporter
**note:** All code examples are in Java. Ported from go-approval-tests `reporters/ai_coding_agent.go`.

The `AiCodingAgentReporter` reports a failing approval as text on standard out when the tests are run by an AI coding agent.

## Purpose
AI coding agents (Claude Code, Codex, Cursor, Gemini CLI, ...) can't see a diff tool that pops up on the screen.
They can read console output. When an agent is running the tests, print everything the agent needs to understand and fix (or approve) the failure, instead of launching a diff tool.

## Detection
An AI coding agent is detected when any of these environment variables is set:

| Variable | Agent |
| - | - |
| `AI_AGENT` | generic opt-in |
| `APPROVAL_TESTS_AGENT` | generic opt-in |
| `CLAUDECODE` | Claude Code |
| `CLAUDE_CODE_ENTRYPOINT` | Claude Code |
| `CODEX_SANDBOX` | OpenAI Codex CLI |
| `CURSOR_AGENT` | Cursor |
| `CURSOR_TRACE_ID` | Cursor |
| `GEMINI_CLI` | Gemini CLI |
| `OPENCODE` | opencode |

A variable counts as set when:
1. It exists, and
1. Its value is not blank, and
1. Its value is not a false value: `0`, `f`, `F`, `false`, `FALSE`, `False` (the same values Go's `strconv.ParseBool` reads as false).

So `CLAUDECODE=0` or `AI_AGENT=false` lets a user opt out.

The list is a public, mutable `List<String>` (`AiCodingAgentReporter.ENVIRONMENT_VARIABLES`) so users can add an agent that is not listed.

## Behavior
`report(received, approved)`:
1. If no AI coding agent is detected, return `false` (so the next reporter in a `FirstWorkingReporter` gets a chance).
1. Otherwise print the report below to `System.out` and return `true`.

## Output
```
# APPROVAL TEST FAILED
    approved: /abs/path/Foo.test.approved.txt
    received: /abs/path/Foo.test.received.txt
    approve_with: mv -f /abs/path/Foo.test.received.txt /abs/path/Foo.test.approved.txt

    ## APPROVED
    ```txt
    hello world
    line two
    ```

    ## RECEIVED
    ```txt
    hello world
    line 2
    ```
----------------------
```
- Paths are absolute.
- `approve_with` is `ClipboardReporter.getAcceptApprovalText` (`move /Y` on Windows).
- Each file's content is indented 4 spaces inside a code fence labeled with the file extension (without the dot). A single trailing newline is dropped.
- If a file can't be read (e.g. the approved file does not exist yet), its content is `** Error reading APPROVED file **` / `** Error reading RECEIVED file **`.

## Integration
The reporter is part of the `DefaultFrontLoadedReporter`:
```java
PitReporter.INSTANCE, EnvironmentVariableReporter.INSTANCE, AiCodingAgentReporter.INSTANCE
```
It comes after `EnvironmentVariableReporter`, so an explicit `APPROVAL_TESTS_USE_REPORTER` still wins.
(Go puts it first; Java keeps explicit user configuration ahead of auto-detection.)

It can also be used directly: `@UseReporter(AiCodingAgentReporter.class)`.

## Tests
1. Detects an agent when `CLAUDECODE=1`
1. Does not detect an agent when no variable is set
1. Treats blank and false values (`0`, `false`) as not set
1. Does not report (returns `false`) when no agent is detected
1. Verifies the printed output for an approved and received file
1. Verifies the printed output when the approved file is missing
