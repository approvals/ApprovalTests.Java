package org.approvaltests.scrubbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DateScrubberTests
{
  @Test
  void testSupportedFormatWorksForExamples()
  {
    for (DateScrubber.SupportedFormat supportedFormat : DateScrubber.getSupportedFormats())
    {
      DateScrubber dateScrubber = new DateScrubber(supportedFormat.getRegex());
      for (String example : supportedFormat.getExamples())
      {
        assertEquals(dateScrubber.scrub(example), "[Date1]",
            () -> "didn't work for regex " + supportedFormat.getRegex());
      }
    }
  }
  @Test
  void testGetDateScrubber()
  {
    List<String> formats = Stream.of(DateScrubber.getSupportedFormats()).flatMap(f -> Stream.of(f.getExamples()))
        .collect(Collectors.toList());
    Approvals.verifyAll("Date scrubbing", formats, this::verifyScrubbing);
  }
  private String verifyScrubbing(String formattedExample)
  {
    DateScrubber scrubber = DateScrubber.getScrubberFor(formattedExample);
    String exampleText = String.format("{'date':\"%s\"}", formattedExample);
    return String.format("Scrubbing for %s:\n" + "%s\n" + "Example: %s\n\n", formattedExample, scrubber,
        scrubber.scrub(exampleText));
  }
  @Test
  void exampleForDocumentation()
  {
    // begin-snippet: scrub-date-example
    Approvals.verify("created at 03:14:15", new Options().withScrubber(DateScrubber.getScrubberFor("00:00:00")));
    // end-snippet
  }
  @Test
  void supportedFormats()
  {
    VelocityApprovals.verify(c -> {
      c.put("formats", DateScrubber.getSupportedFormats());
    }, ".md");
  }
  @Disabled
  @Test
  void nextWeek()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //    new DateScrubber(sdf);
  }
}
