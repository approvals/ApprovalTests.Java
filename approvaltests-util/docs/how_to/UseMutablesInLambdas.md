<a id="top"></a>

# Use to use mutable variables with lamdbas

<!-- toc -->
## Contents

  * [Why use logs?](#why-use-logs)
  * [Capturing logs in test](#capturing-logs-in-test)
  * [See also](#see-also)<!-- endToc -->


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

snippet: single_element_array

## Mutable<T> solution

Mutable allows for easy getting/setting/updating.

For example:

snippet: mutable_example

Will produce the following:

snippet: MutableTest.exampleOfMutable.approved.txt

