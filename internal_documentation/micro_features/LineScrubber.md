# Line Scrubber

## Purpose
Lets users remove whole lines that contain some text (for example a timestamp, a machine name, or a debug line) before comparing.
Ported from ApprovalTests.Python `create_line_scrubber()`.

## API
A static factory on `Scrubbers`, returning a `Scrubber` so it composes with `Scrubbers.scrubAll` and `Options.withScrubber`.

```java
Scrubber scrubber = Scrubbers.removeLinesContaining("Generated at");
Approvals.verify(text, new Options().withScrubber(scrubber));
```

## Behavior
1. Splits the input into lines.
2. Drops every line that contains the given text (plain substring match, case-sensitive, not a regex).
3. Joins the remaining lines with `\n`.

Every matching line is removed, including consecutive ones.
(The Python version removes items from the list while iterating it, so it skips a matching line that directly follows another one. Do not copy that bug.)

## Unit Tests
A single unit test using inline approvals: a multi-line string where two consecutive lines and one later line contain the text, verified with the scrubber.

## Docs
- Add a "Line scrubbing" section to `approvaltests/docs/Scrubbers.md`.
- Change the FeatureMap row "Remove lines that contain some text" from ❌ missing to `Scrubbers.removeLinesContaining`.
