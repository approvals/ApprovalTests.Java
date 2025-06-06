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
<!-- snippet: join_collection -->
<a id='snippet-join_collection'></a>
```java
List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
String text = StringUtils.join(number, ", ");
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.java#L66-L69' title='Snippet source file'>snippet source</a> | <a href='#snippet-join_collection' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
will produce
<!-- snippet: /approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollection.approved.txt -->
<a id='snippet-/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollection.approved.txt'></a>
```txt
1, 2, 3, 4, 5
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollection.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollection.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
by invoking the .toString() on each object in the collection.

## Joining Collections with a function
if .toString() isn't enough, you can pass in a lambda to do the extra transformation as well.
For example:
<!-- snippet: join_collection_with_lambda -->
<a id='snippet-join_collection_with_lambda'></a>
```java
List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
String text = StringUtils.join(number, ", ", n -> StringUtils.padNumber(n, 3));
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.java#L75-L78' title='Snippet source file'>snippet source</a> | <a href='#snippet-join_collection_with_lambda' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
will Produce
<!-- snippet: /approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollectionWithFunction.approved.txt -->
<a id='snippet-/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollectionWithFunction.approved.txt'></a>
```txt
001, 002, 003, 004, 005
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollectionWithFunction.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-util-tests/src/test/java/com/spun/util/StringUtilsTest.testJoinCollectionWithFunction.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

---

[Back to User Guide](README.md#top)
