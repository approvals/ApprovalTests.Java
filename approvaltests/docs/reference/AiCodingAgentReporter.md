<a id="top"></a>

# AI Coding Agent Reporter

<!-- toc -->
## Contents

  * [What and Why](#what-and-why)
  * [Detection](#detection)
  * [Output](#output)
  * [Using it](#using-it)
  * [Links](#links)<!-- endToc -->

## What and Why

AI coding agents (Claude Code, Codex, Cursor, Gemini CLI, opencode, ...) can't see a diff tool that opens on the screen, but they can read console output.
When ApprovalTests detects that an AI coding agent is running the tests, the `AiCodingAgentReporter` prints everything the agent needs to understand the failure, and to fix or approve it, to standard out instead of opening a diff tool.

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

A blank value, or a false value (`0`, `f`, `F`, `false`, `FALSE`, `False`), does not count. So `CLAUDECODE=0` turns it off.

To detect an agent that is not listed, add its variable to `AiCodingAgentReporter.ENVIRONMENT_VARIABLES`.

## Output

````
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
````

On Windows, `approve_with` uses `move /Y`.

## Using it

The reporter is part of the [DefaultFrontLoadedReporter](FrontLoadedReporter.md#top), so it works with no configuration.
It comes after the [`APPROVAL_TESTS_USE_REPORTER` environment variable](../how_to/ConfigureReporters.md#method-4-environment-variables), so an explicitly configured reporter still wins.

You can also use it directly:

```java
@UseReporter(AiCodingAgentReporter.class)
```

## Links

* [Reporters](Reporters.md#top)
* [Front Loaded Reporters](FrontLoadedReporter.md#top)
