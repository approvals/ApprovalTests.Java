<a id="top"></a>

# Query and Queryable

<!-- toc -->
## Contents

    * [Comparison vs. Streams](#comparison-vs-streams)
      * [Other benefits](#other-benefits)
    * [Queryable extends List](#queryable-extends-list)
    * [API](#api)
      * [SelectMany](#selectmany)
        * [SelectMany vs SelectManyArray](#selectmany-vs-selectmanyarray)
        * [Example:](#example)
    * [GroupBy](#groupby)
      * [Full transforms](#full-transforms)
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

### Queryable extends List
Another advantage of Query and Queryable is that Queryable extends List. Here is an example of converting
an Array of Integers to Strings:

Using Query:
<!-- snippet: list_is_queryable -->
<a id='snippet-list_is_queryable'></a>
```java
List<String> strings = Query.select(numbers, n -> "" + n);
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryTest.java#L57-L59' title='Snippet source file'>snippet source</a> | <a href='#snippet-list_is_queryable' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Using Streams:
<!-- snippet: list_from_stream -->
<a id='snippet-list_from_stream'></a>
```java
List<String> strings2 = Arrays.stream(numbers).map(n -> "" + n).collect(Collectors.toList());
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryTest.java#L60-L62' title='Snippet source file'>snippet source</a> | <a href='#snippet-list_from_stream' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### API

#### SelectMany

SelectMany allows you to flatten query results.

For example:  

If each element of your SELECT statement is a List<String> you would get back a List<List<String>>. If you would like to flatten that into a List<String>, the easiest way is to do a Query.selectMany(). 

##### SelectMany vs SelectManyArray

Because of the limitations of the Java language, we could not override the function to allow you to return either a list or an array in your query. As a workaround we overrode the name, so if you are returning a String[] you would need to call selectManyArray.

##### Example:

The following code extracts the words from the following sentences, and orders them alphabetically:

<!-- snippet: queryable_select_many -->
<a id='snippet-queryable_select_many'></a>
```java
Queryable<String> names = Queryable.as("Now is the time", "Fourscore and seven years ago",
    "When in the course of human events");
Queryable<String> allNames = names.selectMany(n -> Arrays.asList(n.split(" "))).orderBy(n -> n);
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L140-L144' title='Snippet source file'>snippet source</a> | <a href='#snippet-queryable_select_many' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

resulting in

<!-- snippet: QueryableTest.testSelectManyCharacters.approved.txt -->
<a id='snippet-QueryableTest.testSelectManyCharacters.approved.txt'></a>
```txt
[0] = Fourscore
[1] = Now
[2] = When
[3] = ago
[4] = and
[5] = course
[6] = events
[7] = human
[8] = in
[9] = is
[10] = of
[11] = seven
[12] = the
[13] = the
[14] = time
[15] = years
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.testSelectManyCharacters.approved.txt#L1-L16' title='Snippet source file'>snippet source</a> | <a href='#snippet-QueryableTest.testSelectManyCharacters.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


### GroupBy

Here is a simple example of grouping words by their first letter.
<!-- snippet: group_by_key -->
<a id='snippet-group_by_key'></a>
```java
Queryable<String> words = Queryable.as("Jack", "and", "Jill", "jumped", "up", "the", "hill");
Queryable<Entry<Character, Queryable<String>>> result = words.groupBy(w -> w.toLowerCase().charAt(0));
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L150-L153' title='Snippet source file'>snippet source</a> | <a href='#snippet-group_by_key' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
producing:
<!-- snippet: QueryableTest.testGroupBy.approved.txt -->
<a id='snippet-QueryableTest.testGroupBy.approved.txt'></a>
```txt
[0] = j=[Jack, Jill, jumped]
[1] = a=[and]
[2] = u=[up]
[3] = t=[the]
[4] = h=[hill]
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.testGroupBy.approved.txt#L1-L5' title='Snippet source file'>snippet source</a> | <a href='#snippet-QueryableTest.testGroupBy.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

#### Full transforms

In a group by, you can select both how the key is selected and how each object for the key is selected,
as well as how the final list is transformed.

Here is an example of doing all three to count the number of words of the same length:
<!-- snippet: group_by_full -->
<a id='snippet-group_by_full'></a>
```java
Queryable<String> words = Queryable.as("One Fish Two Fish Red Fish Blue Fish".split(" "));
Queryable<Entry<Object, Object>> result = words.groupBy(w -> w.length(), w -> w.toLowerCase(),
    r -> r.join("_"));
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L173-L177' title='Snippet source file'>snippet source</a> | <a href='#snippet-group_by_full' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
resulting in
<!-- snippet: QueryableTest.testGroupByCombineWordsOfSimilarLengths.approved.txt -->
<a id='snippet-QueryableTest.testGroupByCombineWordsOfSimilarLengths.approved.txt'></a>
```txt
3 = one_two_red
4 = fish_fish_fish_blue_fish
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.testGroupByCombineWordsOfSimilarLengths.approved.txt#L1-L2' title='Snippet source file'>snippet source</a> | <a href='#snippet-QueryableTest.testGroupByCombineWordsOfSimilarLengths.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## HowTos
* [How to extend Queryable](../how_to/ExtendQueryable.md#top)


---

[Back to User Guide](README.md#top)
