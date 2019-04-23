<a id="top"></a>

# Features



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [Test Commit Revert](#test-commit-revert)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Test Commit Revert

This is a convenience class to follow the practice 'Test Commit/Revert' from Kent Beck. Simply adding
snippet: test_commit_revert
to your test class will invoke 1 of 2 options

*  On success
A dialog will appear asking for a commit message. Once given it will commit all files with that message
![prompt](images/commit_dialog.png)

* On failure
It will revert all changes

Note: this currently only works with git


---

[Back to User Guide](README.md#top)
