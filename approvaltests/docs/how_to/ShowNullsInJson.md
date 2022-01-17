<a id="top"></a>

# How to show nulls in verifyAsJson
<!-- toc -->
<!-- endToc -->

By default, `verifyAsJson` will not display `null` fields. If you wish to change that behaviour, 
here is an example on how to do that.

<!-- snippet: CustomGsonBuilderShowingNull -->
<a id='snippet-customgsonbuildershowingnull'></a>
```java
Person person = new Person("Max", null, 1);
Approvals.verifyAsJson(person, GsonBuilder::serializeNulls, GsonBuilder.class);
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/JsonFormattingTest.java#L26-L29' title='Snippet source file'>snippet source</a> | <a href='#snippet-customgsonbuildershowingnull' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
