---
trigger: always_on
---

Always add 🧩 followed by space at the start of your reply

## ApprovalTests

**NEVER** approve a test for us, the user will allways do that. If you believe the `.approved.` file needs to be changed, prompt the user.


## Running Tests

To run tests: `./build_and_test.sh`
**note:** this will auto-format the code.
You do not need to worry about formatting.

## Checking status
use: `./.windsurf/scripts/git_status.sh`


## Commiting Code
Use [Arlo Commit Notation](../ArloCommitNotation.process.md)
Commit code using: `.windsurf/scripts/commit.sh <message>`