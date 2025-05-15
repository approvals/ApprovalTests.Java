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
* ALWAYS run tests before commiting
* NEVER commit code if tests are failing
* ALWAYS suggest a commit message and confirm with the user before commiting.


### Message
Choose the most important piece and use it as the title.
If extra details are need add them after a line break.
Add the Co-authors. Ask if you haven't confirmed for this session.

Sample:

> **Commit Message:** . e added rules
 

### Co-Authors

| name | github_id |
|---- | ---- |
| Llewellyn | isidore |
| Lars | LarsEckart | 

Co-authors go 1 per line at the bottom of the message, 
in the format:
``` 
Co-authored-by: <name> <<github_id>@users.noreply.github.com>
```
### script

Commit code: `.windsurf/scripts/commit.sh <message>`