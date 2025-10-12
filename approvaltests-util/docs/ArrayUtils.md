<a id="top"></a>

# ArrayUtils

<!-- toc -->
## Contents

  * [toArray](#toarray)<!-- endToc -->

## toArray

ArrayUtils has the ability to dynamically determine the type of your list.

<!-- snippet: toArray -->
<a id='snippet-toArray'></a>
```java
List<Comparable> comparables = new ArrayList<>();
comparables.add(null);
comparables.add(1);
comparables.add(3.1415);
comparables.add("Lars");
Comparable[] comparableArray = ArrayUtils.toArray(comparables);
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ArrayUtilsTest.java#L105-L112' title='Snippet source file'>snippet source</a> | <a href='#snippet-toArray' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

This works in almost all cases with the notable exceptions of empty arrays, some common interfaces 
which will not be preferenced.
In which case you can always use the overloaded method 
<!-- snippet: toArrayWithClass -->
<a id='snippet-toArrayWithClass'></a>
```java
Comparable[] array = ArrayUtils.toArray(comparables, Comparable.class);
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ArrayUtilsTest.java#L113-L115' title='Snippet source file'>snippet source</a> | <a href='#snippet-toArrayWithClass' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


[Back to User Guide](README.md#top)
