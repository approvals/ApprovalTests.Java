<a id="top"></a>

# How to customize another objects `toString()` 
<!-- toc -->
## Contents

    * [Printable wrappers](#printable-wrappers)
    * [Wrap a individual object](#wrap-a-individual-object)
      * [Label](#label)
      * [Alternative `toString()` Function](#alternative-tostring-function)
    * [Wrap an array of objects](#wrap-an-array-of-objects)
    * [Named objects](#named-objects)<!-- endToc -->

### Printable wrappers

Sometimes an objects `toString()` are not what is desired for the give situation. This is particularly true with the parameter names used with `CombinationApprovals` 
Printable wrappers gives you a way to control this.

### Wrap a individual object
#### Label

If you just wanted to wrap a single object. You can create a printable wrapper by:
 
<!-- snippet: printable_single_label -->
<a id='snippet-printable_single_label'></a>
```java
Printable<Integer> one = new Printable(1, "one");
assertEquals("one", one.toString());
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.java#L14-L17' title='Snippet source file'>snippet source</a> | <a href='#snippet-printable_single_label' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

to access the value, simply call

<!-- snippet: printable_access -->
<a id='snippet-printable_access'></a>
```java
Integer value = one.get();
assertEquals(1, value);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.java#L18-L21' title='Snippet source file'>snippet source</a> | <a href='#snippet-printable_access' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

#### Alternative `toString()` Function

You can also write a different function(lambda) to create the `toString()` and then apply it to the object.

<!-- snippet: printable_single_lambda -->
<a id='snippet-printable_single_lambda'></a>
```java
Printable<Integer> two = new Printable(2, i -> "#" + i + ")");
assertEquals("#2)", two.toString());
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.java#L22-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-printable_single_lambda' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Wrap an array of objects

For convenience, if you want to do this to a whole list of items we have 

<!-- snippet: printable_array_lambda -->
<a id='snippet-printable_array_lambda'></a>
```java
Printable<Integer>[] numbers = Printable.create(n -> "#" + n, 1, 2, 3, 4, 5);
Approvals.verifyAll("Custom toString method", numbers, p -> String.format("%s -> %s", p, p.get()));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.java#L36-L39' title='Snippet source file'>snippet source</a> | <a href='#snippet-printable_array_lambda' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

would produce

<!-- snippet: PrintableTest.testCreate.approved.txt -->
<a id='snippet-PrintableTest.testCreate.approved.txt'></a>
```txt
Custom toString method


#1 -> 1
#2 -> 2
#3 -> 3
#4 -> 4
#5 -> 5
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.testCreate.approved.txt#L1-L8' title='Snippet source file'>snippet source</a> | <a href='#snippet-PrintableTest.testCreate.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Named objects 

We also has a convenience builder if you want each object to have it's own label.

<!-- snippet: printable_array_labels -->
<a id='snippet-printable_array_labels'></a>
```java
Printable<Integer>[] labeled = Printable.with().label(1, "first").label(2, "second").label(3, "third")
    .label(4, "forth").label(5, "fifth").toArray();
Approvals.verifyAll("Labeled", labeled, p -> String.format("%s -> %s", p, p.get()));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.java#L44-L48' title='Snippet source file'>snippet source</a> | <a href='#snippet-printable_array_labels' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

would produce:

<!-- snippet: PrintableTest.testLabels.approved.txt -->
<a id='snippet-PrintableTest.testLabels.approved.txt'></a>
```txt
Labeled


first -> 1
second -> 2
third -> 3
forth -> 4
fifth -> 5
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/combinations/PrintableTest.testLabels.approved.txt#L1-L8' title='Snippet source file'>snippet source</a> | <a href='#snippet-PrintableTest.testLabels.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->


---

[Back to User Guide](../README.md#top)
