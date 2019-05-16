<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [ApprovalTests.Java](#approvaltestsjava)
  - [What can it be used for?](#what-can-it-be-used-for)
  - [How to get it](#how-to-get-it)
  - [Video Tutorials](#video-tutorials)
  - [Podcasts](#podcasts)
  - [Examples](#examples)
  - [Approved File Artifacts](#approved-file-artifacts)
  - [More Info](#more-info)
  - [Documentation](#documentation)
  - [LICENSE](#license)
  - [Questions?](#questions)
  - [Developer notes](#developer-notes)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

ApprovalTests.Java
==================
Capturing Human Intelligence - ApprovalTests is an open source assertion/verification library to aid unit testing.

It is compatible with JUnit 3 & 4 and TestNG

What can it be used for?
---

Approval Tests can be used for verifying objects that require more than a simple assert. They also come prepackaged with utilities for some common .Net scenarios including


- HashMaps & Collections
- Long Strings
- Log Files
- JPanels
- Xml
- Html
- Json

How to get it
---
It's on Maven Central, search for 'approvaltests'. If you're using Maven,
add this to your pom file:

``` xml
<dependency>
    <groupId>com.approvaltests</groupId>
    <artifactId>approvaltests</artifactId>
    <version>2.4.0</version>
</dependency>
```

or [download the jars from maven central repository](http://repo1.maven.org/maven2/com/approvaltests/approvaltests/)

[Video Tutorials](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all)
---

You can watch a bunch of short videos on getting started and [using ApprovalTests in Java](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all) at youtube

Podcasts
---
If you prefer auditory learning, you might enjoy the following podcast (Note: Some of these talk about the .net side)

- [Cucumber Podcast](https://cucumber.io/blog/2017/01/26/approval-testing)
- [Hanselminutes](http://www.hanselminutes.com/360/approval-tests-with-llewellyn-falco)
- [Herding Code](http://www.developerfusion.com/media/122649/herding-code-117-llewellyn-falcon-on-approval-tests/)
- [The Watir Podcast](http://watirpodcast.com/podcast-53/)



Examples
---
ApprovalTests eats it own dogfood, so the best examples are in the source code itself.

None the less,  Here's a quick look at some
[Sample Code](https://github.com/approvals/ApprovalTests.Java/blob/master/java/org/approvaltests/tests/demos/SampleArrayTest.java)

	public class SampleArrayTest extends TestCase
	{
		public void testList() throws Exception
		{
			String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
			Arrays.sort(names);
			Approvals.verifyAll("", names);
		}
	}

Will Produce a File

    SampleTest.TestList.received.txt
    [0] = Dan
    [1] = James
    [2] = Jason
    [3] = Katrina
    [4] = Llewellyn

Simply rename this to SampleTest.testList.approved.txt and the test will now pass.

Approved File Artifacts
---

The `*.approved.*` files must be checked into source your source control.
This can be an issue with git as it will change the line endings.
The suggested fix is to add
`*.approved.* binary` to your `.gitattributes`

More Info
---

- [Website](http://approvaltests.sourceforge.net/)
- [Blog](http://blog.approvaltests.com/)
- [Getting Started Doc](https://github.com/approvals/ApprovalTests.Java/blob/master/build/resources/approval_tests/documentation/ApprovalTests%20-%20GettingStarted.md)


## Documentation
- [ApprovalTests-util](/approvaltests-util/docs/README.md)  
- [ApprovalTests](/approvaltests/docs/README.md)  

## LICENSE
[Apache 2.0 License](https://github.com/SignalR/SignalR/blob/master/LICENSE.md)


Questions?
---

twitter: [@LlewellynFalco](https://twitter.com/#!/llewellynfalco) or #ApprovalTests

Developer notes
----------------

To build with Maven:

	mvn install

If you see test failures and want to carry on anyway (Some tests are machine or locale dependent unfortunately):

	mvn install -DskipTests

If you have trouble with the "mrunit" package which is listed on Maven central but doesn't seem to download, install it locally with this command:

	mvn install:install-file -Dfile=missing_jars/mrunit-0.9.0-incubating-hadoop1.jar -DgroupId=org.apache.mrunit -DartifactId=mrunit -Dversion=0.9.0-incubating -Dpackaging=jar


If you want to create the signed jars suitable for release, you will need a gpg key.
There are detailed instructions about setting this up on [sonatype's site](https://central.sonatype.org/pages/working-with-pgp-signatures.html)
Then to actually perform the release, use the shell script:

    ./publish_maven.sh

Enter your gpg key passphrase when it prompts you.

After the release, update the version number in all the pom files, eg:

    mvn versions:set -DnewVersion=2.0.1-SNAPSHOT
