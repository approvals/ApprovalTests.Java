<a id="top"></a>

# How to Use TestFactory

<!-- toc -->
## Contents

  * [Explanation](#explanation)
  * [Java](#java)
  * [Kotlin](#kotlin)<!-- endToc -->

## Explanation
Approvals uses the stack trace to figure out the name of the `.approved.` file.
Dynamic tests require some intervention to capture this at the right time.

Because of this, you will always have to use the Options when calling `verify()` as well.

## Java
Here is an example of how to do this in Java:

<!-- snippet: java_dynamic_test -->
<a id='snippet-java_dynamic_test'></a>
```java
@TestFactory
Collection<DynamicTest> testFactory3()
{
  return Stream.of(1, 2).map(number -> JupiterApprovals.dynamicTest("test " + number,
      o -> Approvals.verify("content for " + number, o))).collect(Collectors.toList());
}
```
<sup><a href='/approvaltests/src/test/java/org/approvaltests/namer/JUnit5StackTraceNamerTest.java#L125-L132' title='Snippet source file'>snippet source</a> | <a href='#snippet-java_dynamic_test' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## Kotlin
Here is an example of how to do this in Kotlin:

<!-- snippet: kotlin_dynamic_test -->
<a id='snippet-kotlin_dynamic_test'></a>
```kt
@TestFactory
fun `test factory`(): List<DynamicTest> {
    return listOf(1, 2).map { it ->
        JupiterApprovals.dynamicTest("square $it") {
                o -> Approvals.verify("${it}^2 = ${it * it}", o)
        }
    }
}
```
<sup><a href='#snippet-kotlin_dynamic_test' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

---

[Back to User Guide](/doc/README.md#top)
