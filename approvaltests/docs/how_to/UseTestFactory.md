<a id="top"></a>

# How to Use TestFactory

<!-- toc -->
## Contents

  * [When to use Approvals::verifyAll()](#when-to-use-approvalsverifyall)
  * [Steps](#steps)
  * [Tables](#tables)<!-- endToc -->

## Explanation
Approvals uses the stack trace to figure out the name of the `.approved.` file.
Dynamic tests require some intervention to capture this at the right time.

Because of this, you will always have to use the Options when calling `verify()` as well.

## Java
Here is an example of how to do this in Java:

snippet: java_dynamic_test

## Kotlin
Here is an example of how to do this in Kotlin:

snippet: kotlin_dynamic_test

---

[Back to User Guide](/doc/README.md#top)
