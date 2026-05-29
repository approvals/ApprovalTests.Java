#!/bin/bash

# Find greater-than (>) and greater-than-or-equal (>=) operators in Java source files
# Usage: ./find-comparisons.sh [--fix] [optional_path]
# Default: scans all src directories in the project
# --fix: automatically flips > to < and >= to <=

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
FIX_MODE=false
TARGETS=()

# Parse arguments
for arg in "$@"; do
    if [ "$arg" = "--fix" ] || [ "$arg" = "-f" ]; then
        FIX_MODE=true
    else
        TARGETS+=("$arg")
    fi
done

# If no targets specified, use default src directories
if [ ${#TARGETS[@]} -eq 0 ]; then
    # Default: scan all src/main/java and src/test/java directories
    while IFS= read -r -d '' src_dir; do
        TARGETS+=("$src_dir")
    done < <(find "$SCRIPT_DIR" -type d -path "*/src/main/java" -o -type d -path "*/src/test/java" -print0)

    if [ ${#TARGETS[@]} -eq 0 ]; then
        echo "No src directories found. Usage: $0 [--fix] [path]"
        exit 1
    fi
fi

if [ "$FIX_MODE" = true ]; then
    echo "🔧 Refactoring comparison operators"
    echo "===================================="
    echo ""

    FIXED_COUNT=0

    for TARGET in "${TARGETS[@]}"; do
        if [ ! -d "$TARGET" ]; then
            echo "Warning: Directory not found: $TARGET"
            continue
        fi

        # Find all Java files with comparison operators
        FILES=$(find "$TARGET" -type f -name "*.java" -exec grep -l -E '>|>=' {} \; 2>/dev/null)

        for FILE in $FILES; do
            # Count occurrences before
            BEFORE_GTE=$(grep -o ">=" "$FILE" | wc -l)
            BEFORE_GT=$(grep -o " > " "$FILE" | wc -l)

            # Use perl for more powerful regex that handles:
            # - Simple identifiers: x >= y
            # - Method calls: obj.method() >= 5
            # - Complex expressions with parentheses/dots
            # BUT excludes generics like List<Integer>

            # Replace >= with <= AND swap operands
            # Negative lookbehind (?<!<) ensures we don't match inside generics
            perl -i.bak -pe 's/(\w+(?:\.\w+)*(?:\([^)]*\))?)\s*>=\s*(\w+)(?!>)/\2 <= \1/g unless /<[^>]*>/' "$FILE"

            # Replace > with < AND swap operands
            # Negative lookbehind (?<!<) and lookahead (?!>) to avoid generics
            perl -i.bak -pe 's/(\w+(?:\.\w+)*(?:\([^)]*\))?)\s*>\s*(\w+)(?!>)/\2 < \1/g unless /<[^>]*>/' "$FILE"

            rm -f "$FILE".bak

            AFTER_GTE=$(grep -o ">=" "$FILE" | wc -l)
            AFTER_GT=$(grep -o " > " "$FILE" | wc -l)

            FIXED=$((BEFORE_GTE - AFTER_GTE + BEFORE_GT - AFTER_GT))
            if [ "$FIXED" -gt 0 ]; then
                FIXED_COUNT=$((FIXED_COUNT + FIXED))
                echo "✓ Refactored $FIXED operators in: $FILE"
            fi
        done
    done

    echo ""
    echo "Refactored $FIXED_COUNT comparison operators"
    echo "⚠️  Review changes with: git diff"
else
    echo "Scanning for comparison operators"
    echo "=================================="
    echo ""

    for TARGET in "${TARGETS[@]}"; do
        if [ ! -d "$TARGET" ]; then
            echo "Warning: Directory not found: $TARGET"
            continue
        fi

        echo "Directory: $TARGET"
        echo "---"

        # Find all > operators (but not >=)
        GT_COUNT=$(grep -r --include="*.java" " > " "$TARGET" 2>/dev/null | grep -v "//" | wc -l)
        if [ "$GT_COUNT" -gt 0 ]; then
            echo "Greater Than (>) — $GT_COUNT occurrences:"
            grep -rn --include="*.java" " > " "$TARGET" 2>/dev/null | grep -v "//" | sed 's/^/  /'
            echo ""
        fi

        # Find all >= operators
        GTE_COUNT=$(grep -r --include="*.java" ">=" "$TARGET" 2>/dev/null | grep -v "//" | wc -l)
        if [ "$GTE_COUNT" -gt 0 ]; then
            echo "Greater Than Or Equal (>=) — $GTE_COUNT occurrences:"
            grep -rn --include="*.java" ">=" "$TARGET" 2>/dev/null | grep -v "//" | sed 's/^/  /'
            echo ""
        fi
    done

    echo "To fix these, run: $0 --fix"
    echo ""
    echo "Flipping suggestions:"
    echo "  > should become <"
    echo "  >= should become <="
    echo ""
    echo "⚠️  Warning: Flipping operators reverses the logic of your comparisons!"
fi
