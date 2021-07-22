<a id="top"></a>

# Query and Queryable

<!-- toc -->
## Contents

    * [Comparison vs. Streams](#comparison-vs-streams)
      * [Other benefits](#other-benefits)
  * [HowTos](#howtos)<!-- endToc -->
## What it is
Query (and the Queryable wrapper) is an alternative to Streams. In other words it is an implementation of Map/Reduce 
based on the naming style of MS Linq.

### Comparison vs. Streams
Here is an example of filtering a list of numbers for only even numbers, sorted in reverse order:

<!-- snippet: query_example -->
<a id='snippet-query_example'></a>
```java
Integer[] numbers = Range.get(1, 20);
Integer[] evenQueryNumbers = Query.where(numbers, n -> n % 2 == 0).orderBy(OrderBy.Order.Descending, n -> n)
    .asArray();
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryTest.java#L34-L38' title='Snippet source file'>snippet source</a> | <a href='#snippet-query_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Here is the exact same function but using Java Streams:

<!-- snippet: stream_example -->
<a id='snippet-stream_example'></a>
```java
Integer[] evenStreamNumbers = Arrays.stream(numbers).filter(n -> n % 2 == 0)
    .sorted((o1, o2) -> o2.compareTo(o1)).toArray(Integer[]::new);
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryTest.java#L39-L42' title='Snippet source file'>snippet source</a> | <a href='#snippet-stream_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Another example using sum() vs. reduce():

<!-- snippet: query_sum_example -->
<a id='snippet-query_sum_example'></a>
```java
String[] names = {"Llewellyn", "Scott"};
int lengthsFromQuery = Query.sum(names, n -> n.length()).intValue();
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryTest.java#L46-L49' title='Snippet source file'>snippet source</a> | <a href='#snippet-query_sum_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Here is the exact same function but using Java Streams:

<!-- snippet: stream_sum_example -->
<a id='snippet-stream_sum_example'></a>
```java
int lengthsFromStream = (int) Arrays.stream(names).map(n -> n.length()).reduce(0, (a, b) -> a + b);
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryTest.java#L50-L52' title='Snippet source file'>snippet source</a> | <a href='#snippet-stream_sum_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

#### Other benefits
* Simpler interface
* Queryable<T> extends List<T>
* Queryable is customizable
* Uses SQL syntax

## HowTos
* [How to extend Queryable](../how_to/ExtendQueryable.md#top)


---

[Back to User Guide](README.md#top)
