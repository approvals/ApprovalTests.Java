# Arlo Commit Notation Process (Draft)

Arlo Commit Notation is a system for prefixing commit messages with a short code that encodes the intent and risk of the commit. This system helps reviewers and maintainers quickly understand the nature of each change.

## Commit Message Structure

```
[prefix] [commit message]
```

- **prefix**: A short code, typically two characters (sometimes more), that encodes information about the commit.
- **commit message**: Description of the change.

## Prefix Format

The prefix generally follows this pattern:

```
[symbol][space][letter(s)]
```

- **symbol**: One of `.`, `-`, `!`, `@`, `***`.
- **letter(s)**: One or more letters, usually a single uppercase or lowercase letter, sometimes two (`dt`).

### Observed Prefixes and Likely Meanings

| Prefix    | Meaning (Inferred)                    |
|-----------|---------------------------------------|
| `. e`     | Environment / Changes that are only used by the developer to use the system (not shipped to user, e.g., scripts, infra) |
| `. d`     | Documentation (not shipped to user)   |
| `. t`     | Test (not shipped to user)            |
| `. r`     | Refactor (tooling-safe refactor) |
| `. C`     | Release/Changelog                     |
| `- r`     | Refactor (unit tested)        |
| `- F`     | Feature (unit tested)         |
| `! r`     | Risky refactor (but single change)                        |
| `! F`     | Risky feature (but single change)                         |
| `@ r`     | Refactor (really risky)            |
| `@ F`     | Feature (really risky)             |
| `***`     | Work in progress (these are intermediate commits in a chain of the thought. The project is usually in a broken state at this point)            |

| `. a`       | Automated/formatting                  |
| `. dt`    | Documentation with Sample Code in a Unit Test        |
| (none)    | Merge, bump, or update (auto/merge)   |

## Inconsistencies
Some of the commits in this repository are not going to adhere to our format. Those are mistakes. Don't base your rules on them.

## Guidelines for Use

- Always use a prefix unless the commit is a merge, bump, or automated update.
- Choose the symbol and letter(s) that best match the intent and risk of the commit.
- Use uppercase letters for features, releases, or high-impact changes.
- Use special symbols (`-`, `!`, `@`, `***`) to indicate increased risk, automation, or special cases.

## Next Steps

- Review all commit prefixes to ensure they match the inferred rules.
- Check commits outside the 200-sample set for consistency.
- If any rules are unclear, clarify with the team or ask the user.

---
**This is a first draft based on observed commit history. Please refine as needed.**
