# Creating the Arlo Commit Notation Process file
Arlo's commit notation is a prefix, a two-character prefix to a get message that helps us to determine both the risk and the intent of each commit. Our goal here is to data mine our existing commits to put together a process file describing how to use it and where to use it.

The document we are building is `.windsurf/ArloCommitNotation.process.md`.

## Interacting with Git
Two mechanisms in which to interact with our Git repo. One is the Git command line and the other is the GitHub CLI. You are welcome to use either.

## To Do
- [x] Gather data
- [ ] Infer the rules

## Gathering Data
Using the CLI, get 200 recent examples from this repo of the commit notation and hashes and put them into a file. Add the data you need about the commit that you think is relevant. Put all of this into a file called `example_commits.txt` in the `.windsurf` directory.

## Infer the rules
Using the data you have collected, infer the rules of the Arlo Commit Notation. Put all of this into a file called `ArloCommitNotation.process.md` in the `.windsurf` directory.
After you have inferred the rules, check the commits to see if they match your expectations. Then check the commits other than the 200 in the example_commits.txt file to see if they still match expectations.
Afterwards, if there are any rules you are uncertain about, ask the user questions. You can ask as many questions as you want but ask one question at a time.