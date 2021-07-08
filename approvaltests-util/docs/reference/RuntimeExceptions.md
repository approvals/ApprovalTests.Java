<a id="top"></a>

# RuntimeExceptions

<!-- toc -->
## Contents

* [Extending Queryable](#extending-queryable)<!-- endToc -->

## Why

It is our belief that checked exceptions were a mistake in the Java language. Fortunately Java provides two methods for 
unchecked exceptions: runtime exceptions and errors. Exceptions will also show up in the signatures of methods
creating troubles with lambdas. The following are techniques for converting checked exceptions into unchecked exceptions.

## 1. Throw as error (Exception)
The most basic method to do this is to wrap a checked exception in a runtime exception. Here is an example of this:
snippet: throw_as_error

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
snippet: throw_as_error_lambda

## 3. Wrapping Actions/Functions
If the issue is in the signature of the lambda you will need to convert from a lambda whose signature allows checked
exceptions to one that doesn't. To do this you will use either `Actions.unchecked()` / `Functions.unchecked()`

Here's an example which allows us to wrap a lambda that throws an `IOException`:
snippet: throw_as_unchecked

---

[Back to User Guide](README.md#top)
