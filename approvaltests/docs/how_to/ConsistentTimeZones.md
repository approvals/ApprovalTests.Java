<a id="top"></a>

# How to have consistent time zones

<!-- toc -->
## Contents

  * [The problem](#the-problem)
  * [The solution](#the-solution)<!-- endToc -->


## The problem
When you are running tests on multiple machines and CI systems distributed around the globe
the system's time zone can become an issue with having a consistent result.

## The solution
ApprovalTests has a convenience class, `WithTimeZone` to set a consistent time zone for a block of code
Here is an example:

<!-- snippet: with_time_zone -->
<a id='snippet-with_time_zone'></a>
```java
try (WithTimeZone tz = new WithTimeZone("UTC"))
{
    // All code within this block will see the computer as being in the UTC time zone
}
// The computer's time zone will revert to previous setting here
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/parser/VelocityUtilsTest.java#L35-L41' title='Snippet source file'>snippet source</a> | <a href='#snippet-with_time_zone' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
