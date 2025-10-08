package org.approvaltests.scrubbers;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.util.Random;

class RegExScrubberTest
{
  @Test
  void replaceRandomNumber()
  {
    // begin-snippet: scrub-regex-example
    String input = "Hello " + new Random().nextInt(100) + " World!";
    Approvals.verify(input, new Options(new RegExScrubber("(\\d+)", "[number]")));
    // end-snippet
  }

  @Test
  void blankDoesNothing()
  {
    String input = "Hello World!";
    Approvals.verify(input, new Options(new RegExScrubber("", "[replaced]")));
  }
}
