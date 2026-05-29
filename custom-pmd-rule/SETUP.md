# Custom Rule Setup: Flip Comparison Operators

This directory contains a custom linting rule that detects `>` and `>=` comparison operators and suggests flipping them to `<` and `<=`.

## Quick Start

### Option 1: Simple Bash Script (Works Now)

```bash
chmod +x find-comparisons.sh
./find-comparisons.sh /path/to/java/source
```

This will scan for `>` and `>=` operators and display them with line numbers.

Example:
```bash
./find-comparisons.sh example
```

### Option 2: PMD Integration (Requires Additional Setup)

If you want to use PMD directly:

```bash
# Install PMD (if not already installed)
brew install pmd

# Run PMD on your code
pmd check -d /path/to/source -R flip-comparison.xml -f text
```

Note: The custom Java-based rule requires PMD 6.x API compatibility. The flip-comparison.xml file is configured for PMD's built-in rules.

## Files

- `find-comparisons.sh` — Bash script to find comparison operators
- `example/TestComparison.java` — Example Java file with violations
- `flip-comparison.xml` — PMD configuration (partial)
- `src/main/java/com/approvaltests/FlipComparisonRule.java` — Custom Java rule (needs API fix for PMD 7.x)
- `pom.xml` — Maven build config

## How It Works

The bash script:
1. Recursively searches for `>` and `>=` operators in Java files
2. Excludes comments (lines with `//`)
3. Displays file path, line number, and the line content
4. Shows warnings about flipping logic

## Autofix

The bash script does not autofix. For autofix capabilities, you would need:
1. A properly compiled custom PMD rule (requires fixing the PMD 7.x API compatibility)
2. Or use a text editor's find-and-replace with regex

Example regex replacements:
- Find: `(\S+)\s*>=\s*(\S+)` Replace with: `$2 <= $1`  
- Find: `(\S+)\s*>\s*(\S+)` Replace with: `$2 < $1`

## Testing

Run on the example file:
```bash
./find-comparisons.sh example
```

Expected output:
```
example/TestComparison.java:9:        if (x > y) {
example/TestComparison.java:13:        if (x >= y) {
example/TestComparison.java:26:        while (x > 0) {
```
