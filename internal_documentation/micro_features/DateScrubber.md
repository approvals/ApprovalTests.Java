# DateScrubber.forSimpleDateFormat() Implementation

## Purpose
Allows users to create a DateScrubber from a `SimpleDateFormat` pattern, making it easier to scrub custom date formats 
without manually writing regex patterns.

## Behavior
A static factory method that accepts a `SimpleDateFormat` and uses it to look up if there is an existing DateScrubber. 
This means, it might fail.


## Examples
```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
DateScrubber scrubber = DateScrubber.forSimpleDateFormat(sdf);
```

## Unit Tests
A single unit test that uses inline approvals, creates a scrubber from a SimpleDateFormat and verifies it scrubs dates correctly.
