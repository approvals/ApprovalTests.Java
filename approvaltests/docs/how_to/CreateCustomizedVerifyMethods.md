# How to extend your own verify() methods

<!-- toc -->
## Contents

  * [Create a Custom Verify Method](#create-a-custom-verify-method)
  * [Create a `Verifiable` Object](#create-a-verifiable-object)<!-- endToc -->

## Create a Custom Verify Method

One method is to create a custom `verify()` method tailored to your specific needs.

For example, here's how to create a custom method for handling JSON:

<!-- snippet: verify_as_json -->
<a id='snippet-verify_as_json'></a>
```java
public static void verifyAsJson(Object o, Options options)
{
  Approvals.verify(JsonUtils.asJson(o), options.forFile().withExtension(".json"));
}
```
<sup><a href='/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L52-L57' title='Snippet source file'>snippet source</a> | <a href='#snippet-verify_as_json' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Create a `Verifiable` Object

Another option is to create an object that knows how to verify itself. 
To do this, you'll need to implement the required interfaces:
* [Verifiable](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/core/Verifiable.java#L3-L5)
* [VerifyParameters](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/core/VerifyParameters.java#L3)

And then just call `verify(yourVerifiableObject)`

If verify is called with an instance of `Verifiable` it will do a callback, allowing you to do whatever is needed,
for example:

<!-- snippet: verifiable_object_example -->
<a id='snippet-verifiable_object_example'></a>
```java
@Test
void testVerifiable()
{
  verify(new MarkdownParagraph("Paragraph Title", "This is where the paragraph text is."));
}
public static class MarkdownParagraph implements Verifiable
{
  private String title;
  private String paragraph;
  public MarkdownParagraph(String title, String paragraph)
  {
    this.title = title;
    this.paragraph = paragraph;
  }
  @Override
  public VerifyParameters getVerifyParameters(Options options)
  {
    return new VerifyParameters(options.forFile().withExtension(".md"));
  }
  @Override
  public String toString()
  {
    return String.format("# %s\n%s", title, paragraph);
  }
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/core/VerifiableTest.java#L9-L35' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifiable_object_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
  
