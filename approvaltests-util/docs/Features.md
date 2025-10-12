<a id="top"></a>

# Features

<!-- toc -->
## Contents

  * [toArray](#toarray)
  * [Queryable and Extendable](#queryable-and-extendable)
  * [Functions](#functions)
  * [Actions](#actions)
  * [Actions.doNothing()](#actionsdonothing)
  * [SimpleLogger.logToNothing()](#simpleloggerlogtonothing)
  * [NullLogger](#nulllogger)
  * [ArrayUtils.addToArray()](#arrayutilsaddtoarray)<!-- endToc -->

## toArray
see [ArrayUtils.toArray](ArrayUtils.md#toArray) 
## Queryable and Extendable
see [Query](how_to/ExtendQueryable.md#top) a group of map-reduce functions based on the linq (sql) syntax.

## Functions

Function0 through Function9 are single method interfaces for use with lambdas where you take 0-9 parameters and return a result.  
For Example Function3 has the single method :

<!-- snippet: function3_call -->
<a id='snippet-function3_call'></a>
```java
public Out call(In1 a, In2 b, In3 c);
```
<sup><a href='/approvaltests-util/src/main/java/org/lambda/functions/Function3.java#L5-L7' title='Snippet source file'>snippet source</a> | <a href='#snippet-function3_call' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The first 3 have native java equivalents (that are hard to remember).  
If it is preferred to use those, but can't remember their name, they are listed in the javadocs.  

```
   Function0 -> java.util.function.Supplier  
   Function1 -> java.util.function.Function  
   Function2 -> java.util.function.BiFunction
```
## Actions

Action0 through Action9 are single method interfaces for use with lambdas where you take 0-9 parameters and all results are via side-effect (void return).


For Example Action3 has the single method:  

<!-- snippet: action3_call -->
<a id='snippet-action3_call'></a>
```java
public void call(In1 a, In2 b, In3 c);
```
<sup><a href='/approvaltests-util/src/main/java/org/lambda/actions/Action3.java#L11-L13' title='Snippet source file'>snippet source</a> | <a href='#snippet-action3_call' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The first 3 have native java equivalents (that are hard to remember).  
If it is preferred to use those, but can't remember their name, they are listed in the javadocs.      

```
   Action0 -> java.lang.Runnable  
   Action1 -> java.util.function.Consumer  
   Action2 -> java.util.function.BiConsumer
```

## Actions.doNothing()

Action0 thru Action9 all have an implementation of the null object pattern for your convenience.


## SimpleLogger.logToNothing()
 see [SimpleLogger.logToNothing()](reference/SimpleLogger.md#simpleloggerlogtonothing)

## NullLogger

Null Object Pattern for java.lang.Appendable

## ArrayUtils.addToArray()

Sometimes you wish you could add to an array the same way you can add to a list.
<!-- snippet: add_to_array -->
<a id='snippet-add_to_array'></a>
```java
Integer[] numbers = {1, 2, 3};
numbers = ArrayUtils.addToArray(numbers, 4, 5, 6);
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ArrayUtilsTest.java#L18-L21' title='Snippet source file'>snippet source</a> | <a href='#snippet-add_to_array' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

will result in a new copy of the array with the added items
<!-- snippet: add_to_array_result -->
<a id='snippet-add_to_array_result'></a>
```java
Integer[] resulting = {1, 2, 3, 4, 5, 6};
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ArrayUtilsTest.java#L22-L24' title='Snippet source file'>snippet source</a> | <a href='#snippet-add_to_array_result' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

[Back to User Guide](README.md#top)
