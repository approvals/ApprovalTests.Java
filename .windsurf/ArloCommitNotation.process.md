# Arlo Commit Notation Process (Draft)

Arlo Commit Notation is a system for prefixing commit messages with a short code that encodes the intent and risk of the commit. This system helps reviewers and maintainers quickly understand the nature of each change.

## Commit Message Structure

```
[hash] [prefix] [commit message]
```

- **hash**: The git commit hash.
- **prefix**: A short code, typically two characters (sometimes more), that encodes information about the commit.
- **commit message**: Description of the change.

## Prefix Format

The prefix generally follows this pattern:

```
[symbol][space][letter(s)]
```

- **symbol**: One of `.`, `-`, `!`, `@`, `***`, `v`, or none.
- **letter(s)**: One or more letters, usually a single uppercase or lowercase letter, sometimes two (`dt`).

### Observed Prefixes and Likely Meanings

| Prefix    | Meaning (Inferred)                    |
|-----------|---------------------------------------|
| `. e`     | Enhancement, engineering, or edit (not shipped to user, e.g., scripts, infra) |
| `. d`     | Documentation (not shipped to user)   |
| `. t`     | Test (not shipped to user)            |
| `. r`     | Refactor (not shipped to user, or tooling-safe refactor) |
| `. C`     | Release/Changelog                     |
| `- r`     | Refactor (possibly higher risk)        |
| `- F`     | Feature (possibly higher risk)         |
| `! r`     | Risky refactor                        |
| `! F`     | Risky feature                         |
| `@ r`     | Refactor (special context)            |
| `@ F`     | Feature (special context)             |
| `***`     | Major/experimental/unknown            |
| `v`       | Version bump/release                  |
| `a`       | Automated/formatting                  |
| `. dt`    | Documentation for DynamicTest         |
| (none)    | Merge, bump, or update (auto/merge)   |

> **Note:** The exact meaning of each prefix may depend on team conventions. The above are inferred from commit messages and may need confirmation.

## Guidelines for Use

- Always use a prefix unless the commit is a merge, bump, or automated update.
- Choose the symbol and letter(s) that best match the intent and risk of the commit.
- Use uppercase letters for features, releases, or high-impact changes.
- Use special symbols (`-`, `!`, `@`, `***`) to indicate increased risk, automation, or special cases.
- For version bumps, use `v` followed by the version number.

## Next Steps

- Review all commit prefixes to ensure they match the inferred rules.
- Check commits outside the 200-sample set for consistency.
- If any rules are unclear, clarify with the team or ask the user.

---
**This is a first draft based on observed commit history. Please refine as needed.**
