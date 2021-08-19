<a id="top"></a>

# How to have consistent time zones

<!-- toc -->
<!-- endToc -->


## The problem
When you are running tests on multiple machines and CI systems distributed around the globe
the system's time zone can become an issue with having a consistent result.

## The solution
ApprovalTests has a convenience class, `WithTimeZone` to set a consistent time zone for a block of code
Here is an example:

snippet: with_time_zone
