<a id="top"></a>

# Use to use mutable variables with lamdbas

<!-- toc -->
## Contents

  * [The problem](#the-problem)
  * [Single Element array solution](#single-element-array-solution)
  * [Mutable<T> solution](#mutablet-solution)<!-- endToc -->


## The problem

If you are using a value that changes in a lambda, Java will not compile.

For example: 
```java
int i = 1;
Function0<Integer> counter = () -> i++;
```
Will give a compilation error:

```
Variable used in lambda expression should be final or effectively final
```

Despite Java wanting the expressions to be final, the truth is that the objects themselves can have mutable state. This means that the workaround is to put the mutable state inside an effectively final object.

`Mutable<T>` is a solution to this problem.

## Single Element array solution

The most common solution to this is to use a single element array. While this works, we find it to be ugly.

For example:

<!-- snippet: single_element_array -->
<a id='snippet-single_element_array'></a>
```java
final int[] i = {1};
Function0<Integer> counter = () -> i[0]++;
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/utils/MutableTest.java#L12-L15' title='Snippet source file'>snippet source</a> | <a href='#snippet-single_element_array' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Mutable<T> solution

Mutable allows for easy getting/setting/updating.

For example:

<!-- snippet: mutable_example -->
<a id='snippet-mutable_example'></a>
```java
Mutable<String> i = new Mutable<>("Brian");
Scheduler scheduler = new Scheduler(() -> i.get());
scheduler.addEvent();
i.update(n -> "Mr. " + n);
scheduler.rsvp();
i.set("Steve");
scheduler.bookHotel();
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/utils/MutableTest.java#L20-L28' title='Snippet source file'>snippet source</a> | <a href='#snippet-mutable_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will produce the following:

<!-- snippet: MutableTest.exampleOfMutable.approved.txt -->
<a id='snippet-MutableTest.exampleOfMutable.approved.txt'></a>
```txt
adding event as Brian
rsvping as Mr. Brian
booking hotel as Steve
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/utils/MutableTest.exampleOfMutable.approved.txt#L1-L3' title='Snippet source file'>snippet source</a> | <a href='#snippet-MutableTest.exampleOfMutable.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

