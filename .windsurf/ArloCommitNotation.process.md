### Arlo Commit Notation
ALWAYS add the 2 charactor prefix to commit messages.

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


**Note:* if the changes only involve e,d, or t. The risk is always a `.` as those are not shipped to the end user.

---

### Intentions

Following the risk symbol, a letter denotes the intention of the commit:

| Letter  | Intention     | Description                                       |
| ------- | ------------- | ------------------------------------------------- |
| `F` | Feature       | Add or modify a feature.                          |
| `B` | Bugfix        | Fix a bug or error.                               |
| `r` | Refactoring   | Improve code structure without changing behavior. |
| `d` | Documentation | Update or add documentation.                      | `e` | Enviroment | changes for the dev to produce code. CI, scripts, etc... |  

**Note on Casing**:

* **Uppercase** (`F`, `B`): User-visible change; include in changelogs.
* **Lowercase** (`r`, `d`): Internal changes not visible to end-users.

---

## Commit Message Format

The general format for commit messages is:

```
<Risk Symbol> <Intention Letter> <Commit Description>
```

**Examples**:

* `. F Add user login feature`
* `- B Fix null pointer exception in payment module`
* `! r Refactor authentication logic`
* `. d Update README with setup instructions`

