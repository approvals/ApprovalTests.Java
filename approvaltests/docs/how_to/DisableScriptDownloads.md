<a id="top"></a>

# How to Disable Automatic Script Downloads

## Why

ApprovalTests.Java automatically downloads utility scripts from [ApprovalTests.CommonScripts](https://github.com/approvals/ApprovalTests.CommonScripts) to `.approval_tests_temp/` when tests run. This may be undesirable in CI/CD environments or restricted network environments.

## Solution

Set the environment variable `APPROVALTESTS_DISABLE_SCRIPT_DOWNLOADS=1`:

```bash
export APPROVALTESTS_DISABLE_SCRIPT_DOWNLOADS=1
mvn test
```

Or as a system property:

```bash
mvn test -DAPPROVALTESTS_DISABLE_SCRIPT_DOWNLOADS=1
```

Log files are still created. Only script downloads are prevented.

---

[Back to User Guide](../README.md#top)
