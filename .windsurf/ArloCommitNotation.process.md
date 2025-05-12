### Arlo Commit Notation
ALWAYS add the 2 character prefix to commit messages.

## Overview

Arlo's Commit Notation (ACN) is a system that prefixes commit messages with concise codes to convey:

1. **Risk Level**: The degree of confidence in the change's safety.
2. **Intention**: The purpose or nature of the change.

This notation aids in rapid assessment of commits, facilitating safer and more efficient code reviews and integrations.

---

### Risk Levels

Each commit message starts with a symbol indicating its risk level:

| Symbol | Risk Level | Description                                                     |
| ------ | ---------- | --------------------------------------------------------------- |
| `.`    | Safe       | This did not break production   |
| `-`    | Tested  |  All code in this change was unit tested |
| `!`    | Single Action      | Not fully tested, but only 1 thing has changed      |
| `@`    | Risky     | Catch all for everything above single action |

**Note:** If the changes only involve intentions `e` (Environment), `d` (Documentation), or `r` (Refactoring), the risk is typically `.` as these are often not shipped directly to the end user or change production behavior.

---

### Intentions

Following the risk symbol, a letter denotes the intention of the commit:

| Letter  | Intention     | Description                                       |
| ------- | ------------- | ------------------------------------------------- |
| `F` | Feature       | Add or modify a feature.                          |
| `B` | Bugfix        | Fix a bug or error.                               |
| `r` | Refactoring   | Improve code structure without changing behavior. |
| `d` | Documentation | Update or add documentation.                      |
| `e` | Environment    | Changes for the dev environment (e.g., CI, scripts, setup). |

**Note on Casing**:

* **Uppercase** (`F`, `B`): User-visible change; include in changelogs.
* **Lowercase** (`r`, `d`): Internal changes not visible to end-users.

---

## Commit Message Format

The general format for commit messages is:

```
<Risk Symbol> <Intention Letter> <Title>

[Optional Body: Explain the 'why' and 'what' in more detail.]

[Optional Co-authors section, see below]
```

*   **Title**: A concise summary of the change, starting with the ACN prefix.
*   **Body**: Optional. Provides more context, reasoning, or details about the change. Separate from the title with a blank line.
*   **Co-authors**: Optional. List contributors as described in the Co-authorship section. Separate from the body (or title, if no body) with a blank line.

**Examples**:

*   `. F Add user login feature`
*   `- B Fix null pointer exception in payment module`
*   `! r Refactor authentication logic`
*   `. d Update README with setup instructions`
*   `. e Add commit script

    Added a script to streamline the commit process.`

---

## Co-authorship


If you are not sure who to credit, ask for a list of the co-authors.
Credit co-authors.

**Format**:
```
Co-authored-by: Name <github_id@users.noreply.github.com>
```

**Example**:
```
- B Fix null pointer exception in payment module

The fix addresses the case where the user profile is not fully loaded
before accessing payment details. Added null checks.

Co-authored-by: Lars <LarsEckart@users.noreply.github.com>
```
