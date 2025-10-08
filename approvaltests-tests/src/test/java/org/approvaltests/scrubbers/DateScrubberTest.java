package org.approvaltests.scrubbers;

import com.spun.util.DateUtils;
import com.spun.util.markdown.table.MarkdownColumn;
import com.spun.util.markdown.table.MarkdownTable;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.utils.ConsoleOutput;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.AutoApproveReporter;

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
    for (DateScrubber.SupportedFormat format : DateScrubber.getSupportedFormats())
    {
      table.addRow(format.getExamples()[0], format.getRegex());
    }
    Approvals.verify(String.format("\n\n%s\n\n", table.toMarkdown()),
        new Options().forFile().withExtension(".md"));
  }

  @Disabled("use when new examples are shared at https://github.com/approvals/ApprovalTests.Java/issues/112")
  @Test
  void textExamples()
  {
    String[] examples = """
        2025-05-15 16:57:04.599
        """.split("\n");
    Approvals.verifyAll("Date scrubbing", examples, this::verifyScrubbing);
  }

  @Test
  void testBlank()
  {
    DateScrubber.getScrubberFor("");
  }

  @Test
  void testTimestamp()
  {
    String input = DateUtils.asTimestamp(new Date()).toString();
    String scrubbed = DateScrubber.getScrubberForTimestamp().scrub(input);
    assertEquals("[Date1]", scrubbed);
  }

  @Test
  void testSqlDate()
  {
    java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
    String input = sqlDate.toString();
    String scrubbed = DateScrubber.getScrubberForSqlDate().scrub(input);
    assertEquals("[Date1]", scrubbed);
  }

  @Test
  void testUtilDate()
  {
    java.util.Date utilDate = new java.util.Date();
    String input = utilDate.toString();
    String scrubbed = DateScrubber.getScrubberForDate().scrub(input);
    assertEquals("[Date1]", scrubbed);
  }

  @Test
  void testAddScrubberWithValidRegexAndExample()
  {
    try
    {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{4}-[A-Za-z]{3}-\\d{2}", false);
      DateScrubber scrubber = DateScrubber.getScrubberFor("2024-Jan-15");
      assertEquals("[Date1]", scrubber.scrub("2024-Jan-15"));
    }
    finally
    {
      DateScrubber.clearCustomScrubbers();
    }
  }

  @Test
  void testAddScrubberWithInvalidRegex()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      DateScrubber.addScrubber("2023-Dec-25", "[invalid regex");
    });
  }

  @Test
  void testAddScrubberWithRegexThatDoesntMatchExample()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{2}-\\d{2}-\\d{4}");
    });
  }

  @Test
  @UseReporter(AutoApproveReporter.class)
  void testAddScrubberDisplaysMessage()
  {
    var expected = """
        You are using a custom date scrubber.
        If you think the format you want to scrub would be useful for others,
        please add it to
        https://github.com/approvals/ApprovalTests.Java/issues/112.

        To suppress this message, use:
            DateScrubber.addScrubber("<date format>", "<regex>", false)
        """;
    try (ConsoleOutput console = new ConsoleOutput())
    {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{4}-[A-Za-z]{3}-\\d{2}");
      console.verifyOutput(new Options().inline(expected));
    }
    finally
    {
      DateScrubber.clearCustomScrubbers();
    }
  }

  @Test
  void testAddScrubberSuppressMessage()
  {
    try (ConsoleOutput console = new ConsoleOutput())
    {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{4}-[A-Za-z]{3}-\\d{2}", false);
      String output = console.getOutput();
      assertEquals("", output);
    }
    finally
    {
      DateScrubber.clearCustomScrubbers();
    }
  }

  @Test
  void testCustomScrubberIntegrationWithGetScrubberFor()
  {
    try
    {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{4}-[A-Za-z]{3}-\\d{2}", false);
      DateScrubber scrubber = DateScrubber.getScrubberFor("2024-Jan-15");
      assertEquals("[Date1]", scrubber.scrub("2024-Jan-15"));
      assertEquals("[Date1]", scrubber.scrub("2025-Mar-30"));
      assertEquals("Meeting on [Date1] at noon", scrubber.scrub("Meeting on 2024-Jan-15 at noon"));
      assertEquals("Due date: [Date1]", scrubber.scrub("Due date: 2025-Mar-30"));
    }
    finally
    {
      DateScrubber.clearCustomScrubbers();
    }
  }

  @Test
  void testClearCustomScrubbers()
  {
    try
    {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{4}-[A-Za-z]{3}-\\d{2}", false);
      DateScrubber scrubber = DateScrubber.getScrubberFor("2024-Jan-15");
      assertEquals("[Date1]", scrubber.scrub("2024-Jan-15"));
      DateScrubber.clearCustomScrubbers();
      assertThrows(Exception.class, () -> {
        DateScrubber.getScrubberFor("2024-Jan-15");
      });
    }
    finally
    {
      DateScrubber.clearCustomScrubbers();
    }
  }

  @Test
  void testMultipleCustomScrubbers()
  {
    try
    {
      DateScrubber.addScrubber("2023-Dec-25", "\\d{4}-[A-Za-z]{3}-\\d{2}", false);
      DateScrubber.addScrubber("25/Dec/2023", "\\d{2}/[A-Za-z]{3}/\\d{4}", false);
      DateScrubber scrubber1 = DateScrubber.getScrubberFor("2024-Jan-15");
      assertEquals("[Date1]", scrubber1.scrub("2024-Jan-15"));
      DateScrubber scrubber2 = DateScrubber.getScrubberFor("15/Jan/2024");
      assertEquals("[Date1]", scrubber2.scrub("15/Jan/2024"));
    }
    finally
    {
      DateScrubber.clearCustomScrubbers();
    }
  }
}