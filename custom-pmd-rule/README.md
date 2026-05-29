# Custom Linting Rule: Flip Comparison Operators

A custom rule that detects `>` (greater than) and `>=` (greater than or equal) operators in Java code and suggests flipping them to `<` and `<=` respectively.

## Quick Start

### Find operators with bash script

```bash
./find-comparisons.sh /path/to/java/source
```

Example:
```bash
./find-comparisons.sh ../approvaltests/src/main/java
```

This will display all `>` and `>=` operators with line numbers and file paths.

### Example Output

```
example/TestComparison.java:7:        if (x > y) {
example/TestComparison.java:11:        if (x >= y) {
example/TestComparison.java:25:        if (x > 0 && x < 100) {
example/TestComparison.java:30:        while (x > 0) {
```

## What the Rule Does

- **Detects** `>` operators and suggests flipping to `<`
- **Detects** `>=` operators and suggests flipping to `<=`
- **Warnings only** — does not auto-fix (to prevent accidentally inverting logic)
- Works as a standalone CLI tool

## Files

- `find-comparisons.sh` — Main script to find comparison operators
- `flip-comparison.xml` — PMD configuration file
- `example/TestComparison.java` — Example Java file with violations
- `SETUP.md` — Detailed setup and PMD integration instructions

## Why "Flip"?

If you flip comparison operators, you reverse the logic:
- `x > y` becomes `x < y` (opposite condition)
- `x >= y` becomes `x <= y` (opposite condition)

The rule is useful for:
- Finding all comparison operators in your codebase
- Code analysis and style enforcement
- Testing/educational purposes

## For PMD Integration

See `SETUP.md` for instructions on using this with PMD directly.
