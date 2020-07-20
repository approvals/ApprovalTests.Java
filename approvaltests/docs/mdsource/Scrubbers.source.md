<a id="top"></a>

# Scrubbers

toc

!(https://raw.githubusercontent.com/approvals/ApprovalTests.cpp/master/doc/images/ScrubberOverview.png)

If you are having trouble getting tests running reproducibly, you might need to use a “scrubber” to convert the non-deterministic text to something stable.

## Interface

Fundamentally, a scrubber is function that takes a string and returns a string. 
You can create ones by passing in a function or a lambda. 
We also have some pre-made ones for your convenience.


## Guid Scrubbing

Let's say you have the following Guids in your output:
snippet: guid-scrubbing-1
You can make this output deterministic by using a scrubber in the options.
For example:
snippet: guid-scrubbing-2
**Note:** Options is available on all Approvals.verify methods.
This will result in the following `.approved.txt` file
snippet: /src/test/java/org/approvaltests/scrubbers/ScrubberTest.scrubGuids.approved.txt
**Note:** If a Guid is used in multiple places, it will be scrubbed using the same replacement.
That is why you see `guid_1` three times.

## RexEx Scrubbing

Using a regex search term

For example, here is an example where random numbers are scrubbed:

---

[Back to User Guide](README.md#top)
