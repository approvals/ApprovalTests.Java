<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [ApprovalTests.Java](#approvaltestsjava)
  - [What can it be used for?](#what-can-it-be-used-for)
  - [Documentation](#documentation)
  - [Getting started](#getting-started)
  - [How to get it](#how-to-get-it)
  - [Video Tutorials](#video-tutorials)
  - [Podcasts](#podcasts)
  - [Examples](#examples)
  - [Approved File Artifacts](#approved-file-artifacts)
  - [More Info](#more-info)
  - [LICENSE](#license)
  - [Questions?](#questions)
  - [Developer notes](#developer-notes)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# ApprovalTests.Java

Capturing Human Intelligence - ApprovalTests is an open source assertion/verification library to aid unit testing.

It is compatible with JUnit 3 & 4 and TestNG

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
It is a maven project and can be imported in to any editor.


## How to get it
It's on Maven Central, search for 'approvaltests'. If you're using Maven,
add this to your pom file:

``` xml
<dependency>
    <groupId>com.approvaltests</groupId>
    <artifactId>approvaltests</artifactId>
    <version>3.0.0</version>
</dependency>
```

or [download the jars from maven central repository](http://repo1.maven.org/maven2/com/approvaltests/approvaltests/)

[Video Tutorials](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all)
---

You can watch a bunch of short videos on getting started and [using ApprovalTests in Java](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all) at youtube.  

*Note*: There are a lot of videos about ApprovalTests in .Net They are equally useful for understanding the concepts despite being in a different programming language.

## Podcasts

If you prefer auditory learning, you might enjoy the following podcast (Note: Some of these talk about the .net side)

- [Cucumber Podcast](https://cucumber.io/blog/2017/01/26/approval-testing)
- [Hanselminutes](http://www.hanselminutes.com/360/approval-tests-with-llewellyn-falco)
- [Herding Code](http://www.developerfusion.com/media/122649/herding-code-117-llewellyn-falcon-on-approval-tests/)
- [The Watir Podcast](http://watirpodcast.com/podcast-53/)



## Examples

ApprovalTests eats it own dogfood, so the best examples are in the source code itself.

None the less,  Here's a quick look at some
[Sample Code](https://github.com/approvals/ApprovalTests.Java/blob/master/java/org/approvaltests/tests/demos/SampleArrayTest.java)

``` Java
	public class SampleArrayTest extends TestCase
	{
		public void testList() throws Exception
		{
			String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
			Arrays.sort(names);
			Approvals.verifyAll("", names);
		}
	}
```

Will Produce a File
```
    SampleTest.TestList.received.txt
    [0] = Dan
    [1] = James
    [2] = Jason
    [3] = Katrina
    [4] = Llewellyn
```

Simply rename this to SampleTest.testList.approved.txt and the test will now pass.

## Approved File Artifacts


The `*.approved.*` files must be checked into source your source control.
This can be an issue with git as it will change the line endings.
The suggested fix is to add
`*.approved.* binary` to your `.gitattributes`

## More Info


- [Website](http://approvaltests.com/)
- [Blog](http://blog.approvaltests.com/)
- [Getting Started Doc](https://github.com/approvals/ApprovalTests.Java/blob/master/build/resources/approval_tests/documentation/ApprovalTests%20-%20GettingStarted.md)



## LICENSE
[Apache 2.0 License](https://github.com/SignalR/SignalR/blob/master/LICENSE.md)


Questions?
---

twitter: [@LlewellynFalco](https://twitter.com/#!/llewellynfalco) or #ApprovalTests


## Developer notes

The suggested way to contribute to ApprovalTests is to [pair with Llewellyn](mailto:llewellyn.falco@gmail.com)  
However, if you are set on forking please [read these notes](developer_notes.md)
