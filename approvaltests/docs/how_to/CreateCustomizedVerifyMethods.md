# How to extend your own verify() methods

<!-- toc -->
## Contents

* [Create a Custom Verify Method](#create-a-custom-verify-method)
* [Create a `Verifiable` Object](#create-a-verifiable-object)<!-- endToc -->

## Create a Custom Verify Method

One method is to create a custom `verify()` method for your particular situation.

For example, as we use it to handle json:

<!-- snippet: verify_as_json -->
<a id='snippet-verify_as_json'></a>
<sup><a href='/approvaltests/approvals.py#L191-L206' title='Snippet source file'>snippet source</a> | <a href='#snippet-verify_as_json' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Create a `Verifiable` Object

Alternatively, you can create an object that knows how to verify itself.
See the required interfaces:
* [Verifiable](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/core/Verifiable.java#L3-L5)
* [VerifyParameters](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/core/VerifyParameters.java#L3)

And then just call `verify(yourVerifiableObject)`

If verify is called with an instance of `Verifiable` it will do a callback, allowing you to do whatever is needed,
for example:

<!-- snippet: verifiable_object_example -->
<a id='snippet-verifiable_object_example'></a>
<sup><a href='/tests/test_verify.py#L282-L299' title='Snippet source file'>snippet source</a> | <a href='#snippet-verifiable_object_example' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
  