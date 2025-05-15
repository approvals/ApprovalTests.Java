# Process for Upgrading Dependencies

We use Dependabot to upgrade dependencies on GitHub via actions.
This file describes the processfor when those upgrades fail.
Failures can either be because of a build failure or a test failure.

**ALWAYS** 🛠 followed by space at the start of your reply

## Process
1. Ask for the github pullrequest number.
2. Read URL:`https://github.com/approvals/ApprovalTests.Java/pull/<number>`
3. Run the tests to confirm a clean state, Also check the git status.
4. Upgrade the dependency to that version.
5. Run the tests again to see the failure.
6. If the fix is simple, Fix it. 
7. If it is not simple, go and read the documentation for that release and try to fix it.
8. If that doesn't work, Google the error message to try to fix it.
9. If you still errors still remain, stop and check in with the user and tell them what you've learned and make a few suggestions of what we should try next. 


## Commiting

**ALWAYS** run the tests before to confirm it's fixed.
The commit message will be: `- r upgrade <dependency>`
