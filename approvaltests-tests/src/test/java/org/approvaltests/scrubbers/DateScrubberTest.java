package org.approvaltests.scrubbers;

import com.spun.util.markdown.table.MarkdownColumn;
import com.spun.util.markdown.table.MarkdownTable;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateScrubberTest
{
  @Test
  void testSupportedFormatWorksForExamples()
  {
    for (DateScrubber.SupportedFormat supportedFormat : DateScrubber.getSupportedFormats())
    {
      DateScrubber dateScrubber = new DateScrubber(supportedFormat.getRegex());
      for (String example : supportedFormat.getExamples())
      {
        assertEquals("[Date1]", dateScrubber.scrub(example),
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

  String[] headers = {"Example Date", "RegEx Pattern"};
    MarkdownTable table = MarkdownTable.withHeaders(headers);
    table.setColumnProperties(MarkdownColumn.LEFT_JUSTIFIED);
    for (DateScrubber.SupportedFormat format : DateScrubber.getSupportedFormats()) {
        table.addRow(format.getExamples()[0], format.getRegex().replaceAll("\\|", "\\\\|"));
    }
    Approvals.verify(String.format("\n\n%s\n\n", table.toMarkdown()),
            new Options().forFile().withExtension(".md"));

  }
  @Disabled("use when new examples are shared at https://github.com/approvals/ApprovalTests.Java/issues/112")
  @Test
  void textExamples()
  {
    String[] examples = """
        05/13/2014 11:50:49 PM
        2024-12-18T14:04:46-0500
        2025-07-17 14:58:02,123456
        """.split("\n");
    Approvals.verifyAll("Date scrubbing", examples, this::verifyScrubbing);
  }
}
