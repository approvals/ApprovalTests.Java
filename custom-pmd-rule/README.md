# Custom Linting Rule: Flip Comparison Operators

A custom rule that detects `>` (greater than) and `>=` (greater than or equal) operators in Java code and refactors them by flipping both the operator AND operands to their `<` and `<=` equivalents.

## Quick Start

### Scan for operators (default)

```bash
./find-comparisons.sh
```

Scans all `src/main/java` and `src/test/java` directories in the project.

Or scan a specific directory:
```bash
./find-comparisons.sh custom-pmd-rule/example
```

### Refactor with `--fix`

```bash
./find-comparisons.sh --fix
```

Automatically refactors all comparison operators with proper operand swapping.

## What the Rule Does

### Refactoring Behavior

This is a **pure refactoring** that preserves logic by swapping operands:

| Original | Refactored | Logic |
|----------|-----------|-------|
| `x > y` | `y < x` | Equivalent ✓ |
| `x >= y` | `y <= x` | Equivalent ✓ |
| `this.getValue() > 5` | `5 < this.getValue()` | Equivalent ✓ |
| `items.length >= 1` | `1 <= items.length` | Equivalent ✓ |
| `getList().size() > 0` | `0 < getList().size()` | Equivalent ✓ |

### Protected Cases

The script intelligently handles:
- ✅ **Simple comparisons** — `x > y`
- ✅ **Method calls** — `obj.getValue() > 5`
- ✅ **Chained calls** — `obj.getList().size() > 0`
- ✅ **Property access** — `items.length >= 1`
- ✅ **Complex expressions** — `getParameterTypes().length >= 1`
- ✅ **Preserves generics** — `List<Integer>` unchanged
- ✅ **Mixed code** — Generics and comparisons on same line

## Test Cases

Comprehensive test file included: `example/ComparisonTest.java`

Tests verify:
- Simple variable comparisons
- Method calls and chained calls
- Property access patterns (like `array.length`)
- Complex expressions with multiple dots and parentheses
- Generic type preservation
- Mixed generics and comparisons on same line

Run the test:
```bash
./find-comparisons.sh --fix custom-pmd-rule/example
```

## Files

- `find-comparisons.sh` — Main script to scan and refactor comparison operators
- `example/TestComparison.java` — Example with simple comparisons and generics
- `example/ComparisonTest.java` — Comprehensive test cases
- `flip-comparison.xml` — PMD configuration (reference)
- `SETUP.md` — Additional setup and integration instructions

## Logic Preservation

The refactoring swaps both the operator AND the operands, so the logical meaning is preserved:

**Before:** `if (x > y) { ... }`  
**After:** `if (y < x) { ... }`  
**Result:** Same condition, just expressed differently

This makes it a safe refactoring—no behavior changes, only code rearrangement.

## Usage

```bash
# Scan all src directories
./find-comparisons.sh

# Scan specific directory
./find-comparisons.sh /path/to/src

# Refactor all operators
./find-comparisons.sh --fix

# Refactor specific directory
./find-comparisons.sh --fix /path/to/src

# Review changes before committing
git diff
```
