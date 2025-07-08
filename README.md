
[![FormatJava](https://github.com/approvals/ApprovalTests.Java/actions/workflows/formatJava.yml/badge.svg)](https://github.com/approvals/ApprovalTests.Java/actions/workflows/formatJava.yml)
[![Run tests](https://github.com/approvals/ApprovalTests.Java/actions/workflows/test.yml/badge.svg)](https://github.com/approvals/ApprovalTests.Java/actions/workflows/test.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.approvaltests/approvaltests/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.approvaltests/approvaltests)
[![Discord](https://img.shields.io/discord/1349240939406819409?logo=discord)](https://discord.gg/XDrgy6x6Se)
<!-- toc -->
## Contents

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
Supported Java versions: 8, 17, 21, 24
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/ci/JavaVersionTest.testSupportedJavaVersions.approved.txt#L1-L1' title='Snippet source file'>snippet source</a> | <a href='#snippet-approvaltests-tests/src/test/java/org/approvaltests/ci/JavaVersionTest.testSupportedJavaVersions.approved.txt' title='Start of snippet'>anchor</a></sup>
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
