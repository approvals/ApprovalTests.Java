<a id="top"></a>

# How to show nulls in verifyAsJson
<!-- toc -->
<!-- endToc -->

By default, `verifyAsJson`  method does not display fields with `null` values.
To modify this behavior and display null fields, 
follow the example provided below.

<!-- snippet: CustomGsonBuilderShowingNull -->
<a id='snippet-CustomGsonBuilderShowingNull'></a>
```java
Person person = new Person("Max", null, 1);
JsonApprovals.verifyAsJson(person, GsonBuilder::serializeNulls);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/JsonFormattingTest.java#L36-L39' title='Snippet source file'>snippet source</a> | <a href='#snippet-CustomGsonBuilderShowingNull' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
