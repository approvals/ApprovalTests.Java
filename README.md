ApprovalTests.Java
==================
Capturing Human Intelligence - ApprovalTests is an open source assertion/verification library to aid unit testing.

It is compatiable with JUnit 3 & JUnit 4.

What can it be used for?
---

Approval Tests can be used for verifing objects that require more than a simple assert. They also come prepackaged with utilities for some common .Net scenarios including


- HashMaps & Collections
- Long Strings
- Log Files
- JPanels
- Xml
- Html
- Json

How to get it
---
Unfortunately, I haven't added this to maven repo yet. Simply download the zip from the releases tab and add the jar to your project.


[Video Tutorials](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all)
---

You can watch a bunch of short videos on getting started and [using ApprovalTests in Java](http://www.youtube.com/playlist?list=PLFBA98F47156EFAA9&feature=view_all) at youtube

Podcasts
---
If you prefer auditory learning, you might enjoy the following podcast (Note: Some of these talk about the .net side)

- [Hanselminutes] (http://www.hanselminutes.com/360/approval-tests-with-llewellyn-falco)
- [Herding Code](http://www.developerfusion.com/media/122649/herding-code-117-llewellyn-falcon-on-approval-tests/)
- [The Watir Podcast](http://watirpodcast.com/podcast-53/)


Download 
---
You can find the latest ApprovalTests release under https://github.com/approvals/ApprovalTests.Java/releases.


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

The `*.approved.*` files must be checked into source your source control. This can be an issue with git as it will change the line endings. 
The suggested fix is to add
`*.approved.* binary` to your `.gitattributes`

More Info
---

- [Website](http://approvaltests.sourceforge.net/)
- [Blog](http://blog.approvaltests.com/)
- [Getting Started Doc](https://github.com/approvals/ApprovalTests.Java/raw/master/build/resources/approval_tests/documentation/ApprovalTest%20-%20Getting%20Started.pdf)

	
## LICENSE
[Apache 2.0 License](https://github.com/SignalR/SignalR/blob/master/LICENSE.md)


Questions?
---

twitter: [@LlewellynFalco](https://twitter.com/#!/llewellynfalco) or #ApprovalTests
