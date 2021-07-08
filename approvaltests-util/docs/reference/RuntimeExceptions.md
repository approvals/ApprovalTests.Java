<a id="top"></a>

# RuntimeExceptions

<!-- toc -->
## Contents

  * [Why](#why)
  * [1. Throw as error (Exception)](#1-throw-as-error-exception)
    * [Unnecessary Wrapping](#unnecessary-wrapping)
    * [Returning Exceptions](#returning-exceptions)
  * [2. Throw as error (Action/Function)](#2-throw-as-error-actionfunction)
  * [3. Wrapping Actions/Functions](#3-wrapping-actionsfunctions)<!-- endToc -->

## Why

It is our belief that checked exceptions were a mistake in the Java language. Fortunately Java provides two methods for 
unchecked exceptions: runtime exceptions and errors. Exceptions will also show up in the signatures of methods
creating troubles with lambdas. The following are techniques for converting checked exceptions into unchecked exceptions.

## 1. Throw as error (Exception)
The most basic method to do this is to wrap a checked exception in a runtime exception. Here is an example of this:
<!-- snippet: throw_as_error -->
<a id='snippet-throw_as_error'></a>
```java
try {
  methodThatMightThrowCheckedException();
  methodThatMightThrowRuntimeException();
  methodThatMightThrowError();
} catch (Throwable t) {
  throw ObjectUtils.throwAsError(t);
}
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ObjectUtilsTest.java#L66-L74' title='Snippet source file'>snippet source</a> | <a href='#snippet-throw_as_error' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

There are two things to note in this implementation.

### Unnecessary Wrapping
`ObjectUtils.throwAsError` will not wrap errors or runtime exceptions as they are already checked

### Returning Exceptions
`ObjectUtils.throwAsError` pretends that it will return an `Error`. The reason it does this is so that the calling code
can pretend to throw it. In practice, `throwAsError()` will always throw the `Error`. So whether or not you declare 
`throw` at the calling code is irrelevant to the execution, but can be helpful for compilation of the calling code.

## 2. Throw as error (Action/Function)
If you want to run a small piece of code without the overhead of the try/catch block, you can accomplish this through
the use of lambdas. Here are examples with and without return values:
<!-- snippet: throw_as_error_lambda -->
<a id='snippet-throw_as_error_lambda'></a>
```java
ObjectUtils.throwAsError(() -> methodThatMightThrowCheckedException());
int i = ObjectUtils.throwAsError(() -> methodThatMightThrowCheckedExceptionWithReturnValue());
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ObjectUtilsTest.java#L79-L82' title='Snippet source file'>snippet source</a> | <a href='#snippet-throw_as_error_lambda' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## 3. Wrapping Actions/Functions
If the issue is in the signature of the lambda you will need to convert from a lambda whose signature allows checked
exceptions to one that doesn't. To do this you will use either `Actions.unchecked()` / `Functions.unchecked()`

Here's an example which allows us to wrap a lambda that throws an `IOException`:
<!-- snippet: throw_as_unchecked -->
<a id='snippet-throw_as_unchecked'></a>
```java
Queryable<String> paths = files.select(
        Functions.unchecked(
                // throws IOException
                s -> new File(s).getCanonicalPath()
        ));
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/ObjectUtilsTest.java#L88-L94' title='Snippet source file'>snippet source</a> | <a href='#snippet-throw_as_unchecked' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

---

[Back to User Guide](README.md#top)
