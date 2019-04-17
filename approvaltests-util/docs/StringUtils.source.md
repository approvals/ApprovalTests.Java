<a id="top"></a>

# StringUtils



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [Joining Collections](#joining-collections)
- [Joining Collections with a function](#joining-collections-with-a-function)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Joining Collections

Sometimes you have a list that needs to become a string.
For example
snippet: join_collection
will produce
snippet: /approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.testJoinCollection.approved.txt
by invoking the toString on each object in the collection.

## Joining Collections with a function
if toString isn't enough, you can pass in a lambda to do the extra transformation as well.
For example:
snippet: join_collection_with_lambda
will Produce
snippet: /approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.testJoinCollectionWithFunction.approved.txt

---

[Back to User Guide](README.md#top)
