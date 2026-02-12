#!/usr/bin/env python3
"""Generate Java diff reporter classes from diff_reporters.csv.

Run via: mise run generate_diff_reporters
"""

import csv
from collections import OrderedDict
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent
PROJECT_ROOT = SCRIPT_DIR.parent
CSV_PATH = PROJECT_ROOT / 'approvaltests' / 'DiffTools' / 'diff_reporters.csv'
BASE_OUTPUT = (PROJECT_ROOT / 'approvaltests' / 'src' / 'main' / 'java'
               / 'org' / 'approvaltests' / 'reporters')

GENERATED_HEADER = '// Generated from diff_reporters.csv -- do not edit manually.'

OS_TO_PACKAGE = {
    'Mac': 'macosx',
    'Windows': 'windows',
    'Linux': 'linux',
}

OS_TO_JAVA_CLASS = {
    'Mac': 'Mac',
    'Windows': 'Windows',
    'Linux': 'Linux',
}


def screaming_to_pascal(name):
    """Convert SCREAMING_SNAKE_CASE to PascalCase.

    Examples:
        BEYOND_COMPARE_4 -> BeyondCompare4
        KDIFF3 -> Kdiff3
        DIFF_MERGE -> DiffMerge
    """
    return ''.join(part.capitalize() for part in name.split('_'))


def java_escape_path(path):
    """Escape backslashes for Java string literals."""
    return path.replace('\\', '\\\\')


def class_name_for_row(row):
    """Individual reporter class name: ReportWith{PascalName}{Os}."""
    return f'ReportWith{screaming_to_pascal(row["name"])}{row["os"]}'


def group_class_name(group_name):
    """Group aggregator class name: ReportWith{GroupPascalCase}."""
    return f'ReportWith{screaming_to_pascal(group_name)}'


def os_aggregator_class_name(os_name):
    """OS aggregator class name: ReportWithDiffToolOn{Os}."""
    return f'ReportWithDiffToolOn{os_name}'


def file_type_java(file_types):
    """Map CSV file_types column to Java field name."""
    return {'TEXT': 'TEXT', 'IMAGE': 'IMAGE', 'TEXT_AND_IMAGE': 'TEXT_AND_IMAGE'}[file_types]


def os_guard_condition(os_name):
    """Return the Java early-return condition for an OS aggregator."""
    if os_name == 'Windows':
        return '!SystemUtils.isWindowsEnvironment()'
    elif os_name == 'Mac':
        return '!SystemUtils.isMacEnvironment()'
    else:
        return 'SystemUtils.isWindowsEnvironment() || SystemUtils.isMacEnvironment()'


def write_java(path, content):
    """Write a Java source file, creating directories as needed."""
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding='utf-8')
    print(f'  {path.relative_to(BASE_OUTPUT)}')


# ---------------------------------------------------------------------------
# Generators
# ---------------------------------------------------------------------------

def generate_diff_programs(rows):
    """Generate DiffPrograms.java with static DiffInfo constants per OS."""
    os_groups = OrderedDict()
    for row in rows:
        os_groups.setdefault(row['os'], []).append(row)

    lines = [GENERATED_HEADER]
    lines.append('package org.approvaltests.reporters;')
    lines.append('')
    lines.append('import com.spun.util.ArrayUtils;')
    lines.append('')
    lines.append('import java.util.List;')
    lines.append('')
    lines.append('public class DiffPrograms')
    lines.append('{')
    lines.append('  private static final List<String> TEXT           = GenericDiffReporter.TEXT_FILE_EXTENSIONS;')
    lines.append('  private static final List<String> IMAGE          = GenericDiffReporter.IMAGE_FILE_EXTENSIONS;')
    lines.append('  private static final List<String> TEXT_AND_IMAGE = ArrayUtils.combine(TEXT, IMAGE);')

    for os_name, os_rows in os_groups.items():
        java_cls = OS_TO_JAVA_CLASS[os_name]
        lines.append(f'  public static class {java_cls}')
        lines.append('  {')
        for row in os_rows:
            name = row['name']
            path = java_escape_path(row['path'])
            args = row['arguments'].strip()
            ft = file_type_java(row['file_types'])
            if args:
                lines.append(f'    public static DiffInfo {name} = new DiffInfo(')
                lines.append(f'        "{path}", "{args}", {ft});')
            else:
                lines.append(f'    public static DiffInfo {name} = new DiffInfo(')
                lines.append(f'        "{path}", {ft});')
        lines.append('  }')

    lines.append('}')
    lines.append('')
    write_java(BASE_OUTPUT / 'DiffPrograms.java', '\n'.join(lines))


def generate_individual_reporter(row):
    """Generate a single reporter class extending GenericDiffReporter."""
    cls = class_name_for_row(row)
    os_pkg = OS_TO_PACKAGE[row['os']]
    os_cls = OS_TO_JAVA_CLASS[row['os']]
    field = row['name']

    content = f"""{GENERATED_HEADER}
package org.approvaltests.reporters.{os_pkg};

import org.approvaltests.reporters.DiffPrograms.{os_cls};
import org.approvaltests.reporters.GenericDiffReporter;

public class {cls} extends GenericDiffReporter
{{
  public static final {cls} INSTANCE = new {cls}();
  public {cls}()
  {{
    super({os_cls}.{field});
  }}
}}
"""
    write_java(BASE_OUTPUT / os_pkg / f'{cls}.java', content)


def generate_group_aggregator(group_name, group_rows):
    """Generate a group aggregator class extending FirstWorkingReporter."""
    cls = group_class_name(group_name)

    imports = set()
    for row in group_rows:
        rcls = class_name_for_row(row)
        pkg = OS_TO_PACKAGE[row['os']]
        imports.add(f'import org.approvaltests.reporters.{pkg}.{rcls};')

    refs = [f'{class_name_for_row(r)}.INSTANCE' for r in group_rows]

    lines = [GENERATED_HEADER]
    lines.append('package org.approvaltests.reporters;')
    lines.append('')
    for imp in sorted(imports):
        lines.append(imp)
    lines.append('')
    lines.append(f'public class {cls} extends FirstWorkingReporter')
    lines.append('{')
    lines.append(f'  public static final {cls} INSTANCE = new {cls}();')
    lines.append(f'  public {cls}()')
    lines.append('  {')

    if len(refs) == 1:
        lines.append(f'    super({refs[0]});')
    else:
        lines.append('    super(')
        for i, ref in enumerate(refs):
            comma = ',' if i < len(refs) - 1 else ''
            lines.append(f'        {ref}{comma}')
        lines.append('    );')

    lines.append('  }')
    lines.append('}')
    lines.append('')
    write_java(BASE_OUTPUT / f'{cls}.java', '\n'.join(lines))


def generate_os_aggregator(os_name, os_rows):
    """Generate an OS aggregator class extending FirstWorkingReporter."""
    cls = os_aggregator_class_name(os_name)
    os_pkg = OS_TO_PACKAGE[os_name]
    guard = os_guard_condition(os_name)

    refs = [f'{class_name_for_row(r)}.INSTANCE' for r in os_rows]
    snippet_name = f'{os_name.lower()}_diff_reporters'

    lines = [GENERATED_HEADER]
    lines.append(f'package org.approvaltests.reporters.{os_pkg};')
    lines.append('')
    lines.append('import com.spun.util.SystemUtils;')
    lines.append('import org.approvaltests.reporters.FirstWorkingReporter;')
    lines.append('')
    lines.append(f'public class {cls} extends FirstWorkingReporter')
    lines.append('{')
    lines.append(f'  public static final {cls} INSTANCE = new {cls}();')
    lines.append(f'  public {cls}()')
    lines.append('  {')
    lines.append('    super(')
    lines.append('    // @formatter:off')
    lines.append(f'        // begin-snippet: {snippet_name}')
    for i, ref in enumerate(refs):
        comma = ',' if i < len(refs) - 1 else ''
        lines.append(f'        {ref}{comma}')
    lines.append('        // end-snippet')
    lines.append('    // @formatter:on')
    lines.append('    );')
    lines.append('  }')
    lines.append('  @Override')
    lines.append('  public boolean report(String received, String approved)')
    lines.append('  {')
    lines.append(f'    if ({guard})')
    lines.append('    { return false; }')
    lines.append('    return super.report(received, approved);')
    lines.append('  }')
    lines.append('}')
    lines.append('')
    write_java(BASE_OUTPUT / os_pkg / f'{cls}.java', '\n'.join(lines))


# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------

def main():
    print('Generating diff reporter classes from diff_reporters.csv...')
    print()

    with open(CSV_PATH, newline='', encoding='utf-8') as f:
        rows = list(csv.DictReader(f))

    # 1. DiffPrograms.java
    generate_diff_programs(rows)

    # 2. Individual reporter classes
    for row in rows:
        generate_individual_reporter(row)

    # 3. Group aggregator classes
    groups = OrderedDict()
    for row in rows:
        gn = row['group_name'].strip()
        if gn:
            groups.setdefault(gn, []).append(row)
    for gn, grs in groups.items():
        generate_group_aggregator(gn, grs)

    # 4. OS aggregator classes
    os_groups = OrderedDict()
    for row in rows:
        os_groups.setdefault(row['os'], []).append(row)
    for on, ors in os_groups.items():
        generate_os_aggregator(on, ors)

    print()
    print('Done!')


if __name__ == '__main__':
    main()
