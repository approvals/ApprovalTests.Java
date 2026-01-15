<a id="top"></a>

# How to Configure the Project Directory

<!-- toc -->
## Contents

  * [Introduction](#introduction)
  * [Problem Description](#problem-description)
  * [Solution: Using APPROVALTESTS_PROJECT_DIRECTORY](#solution-using-approvaltests_project_directory)
    * [As an Environment Variable](#as-an-environment-variable)
    * [As a System Property](#as-a-system-property)
  * [Common Scenarios](#common-scenarios)
    * [Maven Surefire with Forked Test Execution](#maven-surefire-with-forked-test-execution)
    * [CI Systems with Custom Working Directories](#ci-systems-with-custom-working-directories)<!-- endToc -->

## Introduction
This documentation explains how to configure ApprovalTests.Java to locate your source files when tests run from a non-standard working directory.

## Problem Description
ApprovalTests.Java needs to locate your test source files to determine where to place approval files. By default, it searches from the current working directory. However, in some scenarios, the working directory is not the project root:

1. **Maven Surefire with forked test execution**: When using `<workingDirectory>target/fork_dir_${surefire.forkNumber}</workingDirectory>` for parallel or isolated test execution
2. **CI systems**: When build tools execute tests from the `target` directory or other subdirectories

When the working directory differs from the project root, ApprovalTests may fail with an error like:
> "Didn't find [TestClass] under .../target/fork_dir_2"

## Solution: Using APPROVALTESTS_PROJECT_DIRECTORY

ApprovalTests provides a configuration option to explicitly specify the project root directory. You can set `APPROVALTESTS_PROJECT_DIRECTORY` as either an environment variable or a system property.

### As an Environment Variable

Set the environment variable to point to your project root:

**Unix/Linux/macOS:**
```bash
export APPROVALTESTS_PROJECT_DIRECTORY=/path/to/your/project
mvn test
```

**Windows:**
```cmd
set APPROVALTESTS_PROJECT_DIRECTORY=C:\path\to\your\project
mvn test
```

**Windows PowerShell:**
```powershell
$env:APPROVALTESTS_PROJECT_DIRECTORY="C:\path\to\your\project"
mvn test
```

### As a System Property

Alternatively, pass it as a Java system property:

```bash
mvn test -DAPPROVALTESTS_PROJECT_DIRECTORY=/path/to/your/project
```

## Common Scenarios

### Maven Surefire with Forked Test Execution

If you're using Maven Surefire with forked test execution, configure the system property in your `pom.xml`:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.0.0</version>
  <configuration>
    <forkCount>2</forkCount>
    <parallel>methods</parallel>
    <workingDirectory>target/fork_dir_${surefire.forkNumber}</workingDirectory>
    <systemPropertyVariables>
      <APPROVALTESTS_PROJECT_DIRECTORY>${project.basedir}</APPROVALTESTS_PROJECT_DIRECTORY>
    </systemPropertyVariables>
  </configuration>
</plugin>
```

### CI Systems with Custom Working Directories

If your CI system runs tests from the `target` directory, set the environment variable to the parent directory:

**Relative path example:**
```bash
export APPROVALTESTS_PROJECT_DIRECTORY=..
mvn test
```

**Absolute path example (GitHub Actions):**
```yaml
- name: Run tests
  env:
    APPROVALTESTS_PROJECT_DIRECTORY: ${{ github.workspace }}
  run: mvn test
```

**Absolute path example (Jenkins):**
```groovy
environment {
    APPROVALTESTS_PROJECT_DIRECTORY = "${WORKSPACE}"
}
```

