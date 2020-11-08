
[![Build Status](https://github.com/approvals/ApprovalTests.java/workflows/mvn%20verify%20linux/badge.svg?branch=master)](https://github.com/approvals/ApprovalTests.java/actions?query=build%3Amaster) 
[![Build Status](https://github.com/approvals/ApprovalTests.java/workflows/mvn%20verify%20windows/badge.svg?branch=master)](https://github.com/approvals/ApprovalTests.java/actions?query=build%3Amaster)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.approvaltests/approvaltests/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.approvaltests/approvaltests)
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fapprovals%2FApprovalTests.Java&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=PAGE+VIEWS&edge_flat=false)](https://hits.seeyoufarm.com)
toc

# ApprovalTests.Java

Capturing Human Intelligence - ApprovalTests is an open source assertion/verification library to aid unit testing.

It is compatible with JUnit 3, 4 & 5 and TestNG.

The jars can be used from JDK 1.7 on up until JDK 14.

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

## Documentation
- [ApprovalTests](/approvaltests/docs/README.md)  
- [ApprovalTests-util](/approvaltests-util/docs/README.md)

## Getting started

The best way to get started is download and open the [Starter Project](https://github.com/approvals/approvaltests.java.starterproject).  
It is a maven project and can be imported into any editor.


## How to get it
It's on Maven Central, search for 'approvaltests'. If you're using Maven,
add this to your pom file:

``` xml
<dependency>
    <groupId>com.approvaltests</groupId>
    <artifactId>approvaltests</artifactId>
    <version>9.3.0</version>
</dependency>
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

snippet: demo

Will Produce a File `SampleTest.TestList.received.txt`
snippet: /approvaltests-tests/src/test/java/org/approvaltests/demos/SampleArrayTest.testList.approved.txt

Simply rename this to SampleTest.testList.approved.txt and the test will now pass.

## Approved File Artifacts


The `*.approved.*` files must be checked into source your source control.
This can be an issue with git as it will change the line endings.
The suggested fix is to add
`*.approved.* binary` to your `.gitattributes`

## More Info


- [Website](https://approvaltests.com/)
- [Blog](https://blog.approvaltests.org/)
- [Getting Started Doc](https://github.com/approvals/ApprovalTests.Java/blob/master/build/resources/approval_tests/documentation/ApprovalTests%20-%20GettingStarted.md)



## LICENSE
[Apache 2.0 License](https://github.com/SignalR/SignalR/blob/master/LICENSE.md)


Questions?
---

twitter: [@LlewellynFalco](https://twitter.com/#!/llewellynfalco) or #ApprovalTests


## Developer notes  

The suggested way to contribute to ApprovalTests is to [pair with Llewellyn](mailto:llewellyn.falco@gmail.com)  
However, if you are set on forking please [read these notes](developer_notes.md)
