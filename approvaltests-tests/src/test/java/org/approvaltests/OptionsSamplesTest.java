package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.ReportNothing;
import org.approvaltests.scrubbers.GuidScrubber;
import org.junit.jupiter.api.Test;

public class OptionsSamplesTest
{
  @Test
  void showAll()
  {
    Options options =
        // begin-snippet: specify_all_the_options
        new Options().withReporter(new ReportNothing()).withScrubber(new GuidScrubber()).forFile()
            .withExtension(".json");
    // end-snippet
  }

  @Test
  void fileExtensions()
  {
    // begin-snippet: basic_approval_with_file_extension
    Approvals.verify("text to be verified", new Options().forFile().withExtension(".xyz"));
    // end-snippet
  }
}
