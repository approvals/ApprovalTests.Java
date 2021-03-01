<a id="top"></a>

# Queryable

<!-- toc -->
## Contents

  * [Extending Queryable](#extending-queryable)<!-- endToc -->

## Extending Queryable

You have a function you wish was part of Queryable.
<!-- snippet: custom-query -->
<a id='snippet-custom-query'></a>
```java
public static Queryable<String> findFirstWordsOnly(List<String> words)
{
  return Query.select(words, w -> {
    int i = w.indexOf(' ');
    if (i == -1)
    {
      return w;
    }
    else
    {
      return w.substring(0, i);
    }
  });
}
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L31-L46' title='Snippet source file'>snippet source</a> | <a href='#snippet-custom-query' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
You can add this to Queryable by implementing the `com.lambda.utils.Extendable` interface.
<!-- snippet: implementing-extendable -->
<a id='snippet-implementing-extendable'></a>
```java
public static class CustomQuery implements Extendable<List<String>>
{
  private List<String> caller;

  @Override public void setCaller(List<String> caller) {
    this.caller = caller;
  }
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L15-L23' title='Snippet source file'>snippet source</a> | <a href='#snippet-implementing-extendable' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
Now you can add extension methods that are **not static**
<!-- snippet: extendable-query -->
<a id='snippet-extendable-query'></a>
```java
public Queryable<String> findFirstWordsOnly() {
  return findFirstWordsOnly(caller);
}
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L25-L29' title='Snippet source file'>snippet source</a> | <a href='#snippet-extendable-query' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
and now you can call it as such
<!-- snippet: custom-query-example -->
<a id='snippet-custom-query-example'></a>
```java
Queryable<String> list = Queryable.as("One fish", "two fish", "red fish", "blue fish");
Queryable<String> firstWordsOnlyWithExtension = list.select(String::toUpperCase).use(CustomQuery.class).findFirstWordsOnly();
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L51-L54' title='Snippet source file'>snippet source</a> | <a href='#snippet-custom-query-example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
whereas previously you had to use
<!-- snippet: custom-query-example-static -->
<a id='snippet-custom-query-example-static'></a>
```java
Queryable<String> firstWordsOnlyStatic = CustomQuery.findFirstWordsOnly(Query.select(list, String::toUpperCase));
```
<sup><a href='/approvaltests-util-tests/src/test/java/org/lambda/query/QueryableTest.java#L55-L57' title='Snippet source file'>snippet source</a> | <a href='#snippet-custom-query-example-static' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

---

[Back to User Guide](README.md#top)
