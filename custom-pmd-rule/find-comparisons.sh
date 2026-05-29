#!/bin/bash

# Simple bash script to find greater-than and greater-than-or-equal operators
# and suggest flipping them to less-than variants

if [ $# -eq 0 ]; then
    echo "Usage: $0 <file_or_directory>"
    echo ""
    echo "Finds greater-than (>) and greater-than-or-equal (>=) operators"
    echo "and suggests flipping them to < and <= respectively"
    exit 1
fi

TARGET="$1"

echo "Scanning for comparison operators in: $TARGET"
echo "============================================="
echo ""

# Find all > operators (but not >= or >)
echo "=== Greater Than (>) operators ==="
grep -rn --include="*.java" " > " "$TARGET" | grep -v "//" | head -20
echo ""

# Find all >= operators
echo "=== Greater Than Or Equal (>=) operators ==="
grep -rn --include="*.java" ">=" "$TARGET" | grep -v "//" | head -20
echo ""

echo "To automatically flip operators, you would need:"
echo "  > should become <"
echo "  >= should become <="
echo ""
echo "Warning: Simply flipping operators will reverse the logic of your comparisons!"
