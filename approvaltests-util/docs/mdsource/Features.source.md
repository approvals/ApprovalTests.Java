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
For Example Function3 has the single method 

snippet: function3_call

The first 3 have native java equivalents (that are hard to remember).  
If it is preferred to use those, but can't remember their name, they are listed in the javadocs.  

   Function0 -> java.util.function.Supplier  
   Function1 -> java.util.function.Function  
   Function2 -> java.util.function.BiFunction

## Actions

Action0 through Action9 are single method interfaces for use with lambdas where you take 0-9 parameters and all results are via side-effect (void return).


For Example Action3 has the single method  

snippet: action3_call

The first 3 have native java equivalents (that are hard to remember).  
If it is preferred to use those, but can't remember their name, they are listed in the javadocs.      

   Action0 -> java.lang.Runnable  
   Action1 -> java.util.function.Consumer  
   Action2 -> java.util.function.BiConsumer


## Actions.doNothing()

Action0 thru Action9 all have an implementation of the null object pattern for your convenience.


## SimpleLogger.logToNothing()

SimpleLogger is used to make approvaltesting easier, but approvalTests also uses it internally. Something these bleed out in the form of messages like

snippet: /approvaltests-util/src/test/java/com/spun/util/logger/tests/SimpleLoggerTest.test.approved.txt

if you want to turn them all off just run

snippet: log_nothing

## NullLogger

Null Object Pattern for java.lang.Appendable

## ArrayUtils.addToArray()

Sometimes you wish you could add to an array the same way you can add to a list.
snippet: add_to_array

will result in a new copy of the array with the added items
snippet: add_to_array_result

[Back to User Guide](README.md#top)
