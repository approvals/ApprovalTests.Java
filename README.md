[![FormatJava](https://github.com/approvals/ApprovalTests.Java/actions/workflows/formatJava.yml/badge.svg)](https://github.com/approvals/ApprovalTests.Java/actions/workflows/formatJava.yml)
[![Run tests](https://github.com/approvals/ApprovalTests.Java/actions/workflows/test.yml/badge.svg)](https://github.com/approvals/ApprovalTests.Java/actions/workflows/test.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.approvaltests/approvaltests/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.approvaltests/approvaltests)
[![Discord](https://img.shields.io/discord/1349240939406819409?logo=discord)](https://discord.gg/XDrgy6x6Se)
<!-- toc -->
## Contents

  * [What can it be used for?](#what-can-it-be-used-for)
  * [Documentation](#documentation)
    * [Missing Documentation?](#missing-documentation)
  * [Getting started](#getting-started)
    * [New Projects](#new-projects)
    * [Docs](#docs)
    * [Learning](#learning)
  * [How to get it](#how-to-get-it)
    * [Maven](#maven)
    * [Gradle](#gradle)
  * [Podcasts](#podcasts)
  * [Examples](#examples)
  * [Approved File Artifacts](#approved-file-artifacts)
  * [More Info](#more-info)
    * [No Checked Exceptions Philosophy](#no-checked-exceptions-philosophy)
  * [LICENSE](#license)
  * [Developer notes](#developer-notes)<!-- endToc -->

# ApprovalTests.Java

Capturing Human Intelligence - ApprovalTests is an open source assertion/verification library to aid unit testing.

It is compatible with JUnit 3, 4 & 5 and TestNG.

<!-- snippet: approvaltests-tests/src/test/java/org/approvaltests/ci/JavaVersionTest.testSupportedJavaVersions.approved.txt -->
<a id='snippet-approvaltests-tests/src/test/java/org/approvaltests/ci/JavaVersionTest.testSupportedJavaVersions.approved.txt'></a>
```txt
Works on JDK 1.8+ (tested on 1.8, 17, 21, 24).
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/ci/JavaVersionTest.testSupportedJavaVersions.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-approvaltests-tests/src/test/java/org/approvaltests/ci/JavaVersionTest.testSupportedJavaVersions.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

## What can it be used for?

Approval Tests can be used for verifying objects that require more than a simple assert. They also come prepackaged with utilities for some common java scenarios including


- HashMaps & Collections
- Long Strings
- Log Files
- JPanels
- Xml
- Html
- Json
- Getting *Legacy Code* under tests

  Want to see more? Check out [Better Testing with Approvals](https://www.youtube.com/watch?v=QEdpE0chA-s)
  
## Documentation
- [ApprovalTests](/approvaltests/docs/README.md)  
- [ApprovalTests-util](/approvaltests-util/docs/README.md)

### Missing Documentation?
If there is documentation you wish existed, please add a `page request` to [this issue](https://github.com/approvals/ApprovalTests.Java/issues/342).

## Getting started
### New Projects
The best way to get started is download and open one of the starter projects:
* Java
  * [Maven](https://github.com/approvals/approvaltests.java.starterproject)
  * [Gradle](https://github.com/approvals/approvaltests.java.starterproject.gradle)
* [Kotlin](https://github.com/approvals/Approvaltests.Kotlin.StarterProject)
* [Groovy](https://github.com/approvals/Approvaltests.Groovy.StarterProject)
* [Scala](https://github.com/approvals/Approvaltests.Scala.StarterProject)

These are standard projects and can be imported into any editor or IDE.  
They also all have CI with Github actions.

### Docs
You might also want to check out the [Getting Started Overview](approvaltests/docs/tutorials/GettingStarted.md)

### Learning
A great way to learn Approval Tests if you are new to it is to try out the [Koans](https://github.com/approvals/ApprovalTests.java.Koans).

## How to get it
It's on Maven Central, search for 'approvaltests'.

### Maven 
If you're using Maven, add this to your pom file:

``` xml
<dependency>
    <groupId>com.approvaltests</groupId>
    <artifactId>approvaltests</artifactId>
    <version>24.22.0</version>
    <scope>test</scope>
</dependency>
```

### Gradle

```gradle
dependencies {
    testImplementation("com.approvaltests:approvaltests:24.22.0")
}
```

or [download the jars from maven central repository](https://repo1.maven.org/maven2/com/approvaltests/approvaltests/)

[Video Tutorials](https://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all)
---

You can watch a bunch of short videos on getting started and [using ApprovalTests in Java](https://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all) at youtube.

*Note*: There are a lot of videos about ApprovalTests in .Net They are equally useful for understanding the concepts despite being in a different programming language.

## Podcasts

If you prefer auditory learning, you might enjoy the following podcast (Note: Some of these talk about the .net side)

- [Cucumber Podcast](https://cucumber.io/blog/2017/01/26/approval-testing)
- [Hanselminutes](https://www.hanselminutes.com/360/approval-tests-with-llewellyn-falco)
- [Herding Code](https://www.developerfusion.com/media/122649/herding-code-117-llewellyn-falcon-on-approval-tests/)



## Examples

ApprovalTests eats it own dogfood, so the best examples are in the source code itself.

None the less,  Here's a quick look

<!-- snippet: demo -->
<a id='snippet-demo'></a>
```java
public class SampleArrayTest
{
  @Test
  public void testList()
  {
    String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
    Arrays.sort(names);
    Approvals.verifyAll("", names);
  }
}
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/demos/SampleArrayTest.java#L8-L19' title='Snippet source file'>snippet source</a> | <a href='#snippet-demo' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Will Produce a File `SampleArrayTest.testList.received.txt`
<!-- snippet: /approvaltests-tests/src/test/java/org/approvaltests/demos/SampleArrayTest.testList.approved.txt -->
<a id='snippet-/approvaltests-tests/src/test/java/org/approvaltests/demos/SampleArrayTest.testList.approved.txt'></a>
```txt
[0] = Dan
[1] = James
[2] = Jason
[3] = Katrina
[4] = Llewellyn
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/demos/SampleArrayTest.testList.approved.txt#L1-L5' title='Snippet source file'>snippet source</a> | <a href='#snippet-/approvaltests-tests/src/test/java/org/approvaltests/demos/SampleArrayTest.testList.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

Simply rename this to SampleTest.testList.approved.txt and the test will now pass.

## Approved File Artifacts


The `*.approved.*` files must be checked into source your source control.
This can be an issue with git as it will change the line endings.
The suggested fix is to add
`*.approved.* binary` to your `.gitattributes`

## More Info

- [ApprovalTests.com](https://approvaltests.com/)

### No Checked Exceptions Philosophy
Approval Tests follows the [no checked exceptions"](approvaltests/docs/explanations/NoCheckedExceptions.md) philosophy. That is, our API only throws runtime exceptions.


## LICENSE
[Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html)


Questions?
---

twitter: [@LlewellynFalco](https://twitter.com/#!/llewellynfalco) or #ApprovalTests


## Developer notes  

The suggested way to contribute to ApprovalTests is to [pair with Llewellyn](mailto:llewellyn.falco@gmail.com)  
However, if you are set on forking please [read these notes](developer_notes.md)
