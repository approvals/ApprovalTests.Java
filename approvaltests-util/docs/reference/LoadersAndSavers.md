<a id="top"></a>

# Loaders and Savers

<!-- toc -->
## Contents

  * [Interfaces](#interfaces)
  * [Loaders](#loaders)
  * [Savers](#savers)
  * [Common Usage Patterns](#common-usage-patterns)
  * [Testing](#testing)
  * [ExecutableCommand](#executablecommand)<!-- endToc -->
## What it is

Loaders and Savers is a very simple abstraction to help separate business logic from a database / services layer.

Loaders tend to work similarly in functionality to a stored procedure.

Savers allow you to save changes.

## Interfaces

The Loader interface looks like:
<!-- snippet: loader_interface -->
<a id='snippet-loader_interface'></a>
```java
public T load();
```
<sup><a href='/approvaltests-util/src/main/java/com/spun/util/persistence/Loader.java#L5-L7' title='Snippet source file'>snippet source</a> | <a href='#snippet-loader_interface' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

And the Saver interface looks like:
<!-- snippet: saver_interface -->
<a id='snippet-saver_interface'></a>
```java
public T save(T save);
```
<sup><a href='/approvaltests-util/src/main/java/com/spun/util/persistence/Saver.java#L5-L7' title='Snippet source file'>snippet source</a> | <a href='#snippet-saver_interface' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Loaders
A `Loader` tends to wrap data access layers to a database, file system, or Web service.
By wrapping the data retrieval in a `Loader` interface it becomes easy to swap it out for testing and other polymorphism.
This also helps to separate your business logic from the implementation of your service layer.

## Savers

The `Saver` also tends to wrap data access layers to a: database, file system, or Web service.
This is the "write" as opposed to the `Loader`'s "read".

The `Saver` returns the saved object.
This is needed because some savers do not mutate the object, but actually create a new instance.
Often saving mutates or does not alter the saved object at all.
In these instances the `Saver` simply returns what is passed in.

## Common Usage Patterns
Most current code hides the data access methods. For example:
```java
public JSON loadCustomer(QueryParameter p) {
    // ... lots of logic
}
```
Using the `Loader`s and `Saver`s you can convert this to two methods, the second of which is very easy to test and extend.
```java
public JSON loadCustomer(QueryParameter p) {
    return loadCustomer(new CustomerQuery(p), new CustomerSaver());
}
public JSON loadCustomer(Loader<Customer> customerLoader, Saver<Customer> customerSaver) {
    // ... lots of logic
}
```

By adding this small seam the main business logic is much easier to test as it is isolated away from the database and service layer.

## Testing
The simplest way to mock a `Loader` or `Saver` is with a lambda.
For example, if we wanted to test what happens when the query does not find a customer, we could do the following call:
```java
loadCustomer(c -> null, s -> s);
```

## ExecutableCommand

`ExecutableCommand`s extend `Loader`s to allow for easy testing of the actual `Loader`.

## Links 
* [How to Encapsulate Data calls with Loads and Savers ](../../../approvaltests/docs/how_to/EncapsulatingWithLoadersAndSavers.md)
* [Overview of refactoring with Loaders and Savers](../how_to/LoadersAndSavers.md)
---

[Back to User Guide](README.md#top)
