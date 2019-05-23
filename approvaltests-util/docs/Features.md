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

- [Functions](#functions)
- [Actions](#actions)
- [Actions.doNothing()](#actionsdonothing)
- [SimpleLogger.logToNothing()](#simpleloggerlogtonothing)
- [NullLogger](#nulllogger)
- [ArrayUtils.addToArray()](#arrayutilsaddtoarray)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Functions

Function0 through Function9 are single method interfaces for use with lambdas where you take 0-9 parameters and return a result.  
For Example Function3 has the single method :

<!-- snippet: function3_call -->
```java
public Out call(In1 a, In2 b, In3 c);
```
<sup>[snippet source](/approvaltests-util/src/main/java/org/lambda/functions/Function3.java#L5-L7)</sup>
<!-- endsnippet -->

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
```java
public void call(In1 a, In2 b, In3 c);
```
<sup>[snippet source](/approvaltests-util/src/main/java/org/lambda/actions/Action3.java#L10-L12)</sup>
<!-- endsnippet -->

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

SimpleLogger is used to make approvaltesting easier, but approvalTests also uses it internally. Something these bleed out in the form of messages like

<!-- snippet: /approvaltests-util/src/test/java/com/spun/util/logger/tests/SimpleLoggerTest.test.approved.txt -->
```txt
=> SimpleLoggerTest.test() - IN
   => SimpleLoggerTest.test() - IN
      Event: Starting Logging
      Variable: name = 'llewellyn'
      Sql: Select * from people
      .........1.........2.........3.........4..
      Variable: Numbers.length = 5
      Numbers[0] = 1
      Numbers[1] = 2
      Numbers[2] = 3
      Numbers[3] = 4
      Numbers[4] = 5
******************************************************************************************
      
java.lang.Error -  null
******************************************************************************************
   <= SimpleLoggerTest.test() - OUT
<= SimpleLoggerTest.test() - OUT

```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/logger/tests/SimpleLoggerTest.test.approved.txt#L1-L19)</sup>
<!-- endsnippet -->

if you want to turn them all off just run

<!-- snippet: log_nothing -->
```java
SimpleLogger.logToNothing();
```
<sup>[snippet source](/approvaltests-util/src/test/java/com/spun/util/logger/tests/SimpleLoggerTest.java#L35-L37)</sup>
<!-- endsnippet -->

## NullLogger

Null Object Pattern for java.lang.Appendable

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
