# Unit Test Style Guide

## Approvals vs Asserts

* Preferable tests over multiple asserts.
* Use toStrings and Printers. If the toString on an object is good, use it. If not, create a printing function either in the test or in production code and use that.

### Dates, GUIDS and other non-deterministic values

Use [Scrubbers](../approvaltests/docs/Scrubbers.md) to remove non-deterministic values from the output.