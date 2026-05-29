#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "Custom PMD Rule: Flip Comparison Operators"
echo "==========================================="
echo ""

# Check if PMD is in PATH
if command -v pmd &> /dev/null; then
    echo "Using PMD from PATH: $(which pmd)"
    PMD_CMD="pmd"
elif [ -f "$SCRIPT_DIR/pmd-6.55.0/bin/pmd.sh" ]; then
    echo "Using local PMD installation"
    PMD_CMD="$SCRIPT_DIR/pmd-6.55.0/bin/pmd.sh"
else
    echo "ERROR: PMD not found!"
    echo ""
    echo "Please install PMD using one of these methods:"
    echo ""
    echo "1. Using Homebrew (macOS):"
    echo "   brew install pmd"
    echo ""
    echo "2. Download manually:"
    echo "   Visit: https://pmd.github.io/latest/pages/release_notes.html"
    echo "   Download PMD 6.55.0"
    echo "   Extract to: $SCRIPT_DIR/pmd-6.55.0"
    echo ""
    echo "3. Using Maven (if available):"
    echo "   mvn dependency:copy -Dartifact=net.sourceforge.pmd:pmd-dist:6.55.0:zip -DoutputDirectory=."
    echo "   unzip pmd-dist-6.55.0.zip"
    echo ""
    exit 1
fi

echo ""
echo "Scanning example code..."
echo "======================="
echo ""

# Run PMD on example code
$PMD_CMD check \
    -d "$SCRIPT_DIR/example" \
    -R "$SCRIPT_DIR/flip-comparison.xml" \
    -f text

echo ""
echo "To run on ApprovalTests source code:"
echo "====================================="
echo "$PMD_CMD check -d ../approvaltests/src/main/java -R flip-comparison.xml -f text"
echo ""
echo "For JSON output:"
echo "$PMD_CMD check -d ../approvaltests/src/main/java -R flip-comparison.xml -f json"
