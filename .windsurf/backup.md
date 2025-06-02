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

* ALWAYS run tests before commiting
* NEVER commit code if tests are failing
* ALWAYS suggest a commit message and confirm with the user before commiting.


### Message
Choose the most important piece and use it as the title.
If extra details are need add them after a line break.
Add the Co-authors. 
Ask if you haven't confirmed for this session, save the confirmation in `.windsurf/coauthors.txt`.

Sample:

> **Commit Message:** . e added rules
 

### Co-Authors

| name | github_id |
|---- | ---- |
| Llewellyn | isidore |
| Lars | LarsEckart | 
| Jay | JayBazuzi |
| Scott | ScottBob |
| Lada | lexler |

Co-authors go 1 per line at the bottom of the message, 
Credit co-authors.

**Format**:
```
Co-authored-by: <name> <<github_id>@users.noreply.github.com>
```

**Example**:
```
- B Fix null pointer exception in payment module

The fix addresses the case where the user profile is not fully loaded
before accessing payment details. Added null checks.

Co-authored-by: Lars <LarsEckart@users.noreply.github.com>
```
