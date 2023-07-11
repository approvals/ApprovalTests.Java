<a id="top"></a>

# ArrayUtils

<!-- toc -->
## Contents

  * [toArray](#toarray)<!-- endToc -->

## toArray

ArrayUtils has the ability to dynamically determine the type of your list.

<!-- snippet: toArray -->
<a id='snippet-toarray'></a>
```java
List<Comparable> comparables = new ArrayList<>();
comparables.add(null);
comparables.add(1);
comparables.add(3.1415);
comparables.add("Lars");
Comparable[] comparableArray = ArrayUtils.toArray(comparables);
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ArrayUtilsTest.java#L101-L108' title='Snippet source file'>snippet source</a> | <a href='#snippet-toarray' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This works in almost all cases with the notable exceptions of empty arrays, some common interfaces 
which will not be preferenced.
In which case you can always use the overloaded method 
<!-- snippet: toArrayWithClass -->
<a id='snippet-toarraywithclass'></a>
```java
Comparable[] array = ArrayUtils.toArray(comparables, Comparable.class);
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ArrayUtilsTest.java#L109-L111' title='Snippet source file'>snippet source</a> | <a href='#snippet-toarraywithclass' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


[Back to User Guide](README.md#top)
