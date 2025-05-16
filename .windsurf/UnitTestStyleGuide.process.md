# Unit Test Style Guide

**ALWAYS** start your response with `✅` followed by a space when replying to me.

## Refactoring

**NEVER** add new test cases while refactoring existing tests.

**ALWAYS** prefer self documenting code and smaller functions to comments.


## ApprovalTests vs Asserts

* Preferable ApprovalTests over multiple asserts.
* Use toStrings and Printers. If the toString on an object is good, use it. If not, create a printing function either in the test or in production code and use that.

### Dates, GUIDS and other non-deterministic values

If you are printing dates, guids, or any other non-deterministic values to an `.approved.` file. 
Always use the scrubbers provided by ApprovalTests.
Please read [Scrubbers](../approvaltests/docs/Scrubbers.md) and add on Options to the Approvals.verify call.

Either use Scrubbers or DateScrubber.
#### Scrubbers Example:

```java
Approvals.verifyAll("guids", guids, new Options(Scrubbers::scrubGuid));
```
 or 
```java
Approvals.verify("created at 03:14:15", new Options().withScrubber(DateScrubber.getScrubberFor("00:00:00")));
```
