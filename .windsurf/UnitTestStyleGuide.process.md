# Unit Test Style Guide

## Refactoring

**NEVER** add new test cases while refactoring existing tests.

**ALWAYS** prefer self documenting code and smaller functions to comments.

## Approvals vs Asserts

* Preferable tests over multiple asserts.
* Use toStrings and Printers. If the toString on an object is good, use it. If not, create a printing function either in the test or in production code and use that.

### Dates, GUIDS and other non-deterministic values

If you are printing dates, guids, or any other non-deterministic values to an `.approved.` file. 
Please read [Scrubbers](../approvaltests/docs/Scrubbers.md) and add on Options to the Approvals.verify call.