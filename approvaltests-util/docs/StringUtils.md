<!--
This file was generate by MarkdownSnippets.
Source File: /approvaltests-util/docs/StringUtils.source.md
To change this file edit the source file and then re-run the generation using either the dotnet global tool (https://github.com/SimonCropp/MarkdownSnippets#markdownsnippetstool) or using the api (https://github.com/SimonCropp/MarkdownSnippets#running-as-a-unit-test).
-->
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
```java
List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
String text = StringUtils.join(number, ", ");
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.java#L52-L55)</sup>
<!-- endsnippet -->
will produce
<!-- snippet: /approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.testJoinCollection.approved.txt -->
```txt
1, 2, 3, 4, 5
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.testJoinCollection.approved.txt#L1-L1)</sup>
<!-- endsnippet -->
by invoking the .toString() on each object in the collection.

## Joining Collections with a function
if .toString() isn't enough, you can pass in a lambda to do the extra transformation as well.
For example:
<!-- snippet: join_collection_with_lambda -->
```java
List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
String text = StringUtils.join(number, ", ", n -> StringUtils.padNumber(n, 3));
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.java#L60-L63)</sup>
<!-- endsnippet -->
will Produce
<!-- snippet: /approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.testJoinCollectionWithFunction.approved.txt -->
```txt
001, 002, 003, 004, 005
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/tests/StringUtilsTest.testJoinCollectionWithFunction.approved.txt#L1-L1)</sup>
<!-- endsnippet -->

---

[Back to User Guide](README.md#top)
