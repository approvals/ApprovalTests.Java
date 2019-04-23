<!--
This file was generate by MarkdownSnippets.
Source File: /approvaltests-util/docs/mdsource/Features.source.md
To change this file edit the source file and then re-run the generation using either the dotnet global tool (https://github.com/SimonCropp/MarkdownSnippets#markdownsnippetstool) or using the api (https://github.com/SimonCropp/MarkdownSnippets#running-as-a-unit-test).
-->
<a id="top"></a>

# Features



<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [ArrayUtils.addToArray()](#arrayutilsaddtoarray)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## ArrayUtils.addToArray()

Sometimes you wish you could add to an array the same way you can add to a list.
<!-- snippet: add_to_array -->
```java
Integer[] numbers = {1, 2, 3};
numbers = ArrayUtils.addToArray(numbers, 4, 5, 6);
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/tests/ArrayUtilsTest.java#L19-L22)</sup>
<!-- endsnippet -->

will result in a new copy of the array with the added items
<!-- snippet: add_to_array_result -->
```java
Integer[] resulting = {1, 2, 3, 4, 5, 6};
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/tests/ArrayUtilsTest.java#L23-L25)</sup>
<!-- endsnippet -->

[Back to User Guide](README.md#top)
