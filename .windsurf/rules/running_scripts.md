---
trigger: always_on
---

**ALWAYS** add the STARTER_CHARACTER followed by space at the start of your reply. 
The STARTER_CHARACTER will change for each process file that is in progress.

Default STARTER_CHARACTER = 🧩


## ApprovalTests

**NEVER** approve a test for us, the user will allways do that. If you believe the `.approved.` file needs to be changed, prompt the user.


## Running Build and Tests

To run build tests: `./build_and_test.sh` or `.\build_and_test.cmd`. Any time I say `b` or `t` I mean "you should run the build and tests."

**note:** The build will auto-format the code.
You do not need to worry about formatting.

## Checking Git status

use: `./.windsurf/scripts/git_status.sh`


## Commiting Code

Read [Arlo Commit Notation](../ArloCommitNotation.process.md) and follow the instructions.

## Changing files

This repository is under git, always makes changes directly to the files without asking the user for permission. The user can always revert them.