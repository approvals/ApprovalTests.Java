package org.approvaltests.scrubbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.Scrubber;
import org.junit.jupiter.api.Test;

class ScrubberTest
{
  @Test
  void noGuid()
  {
    String input = "just normal text";
    String output = Scrubbers.scrubGuid(input);
    assertEquals("just normal text", output);
  }
  @Test
  void scrubGuid()
  {
    String input = "normal text and 2fd78d4a-ad49-447d-96a8-deda585a9aa5 and normal text";
    String output = Scrubbers.scrubGuid(input);
    assertEquals("normal text and guid_1 and normal text", output);
    Approvals.verify(output);
    Approvals.verify(input, new Options(Scrubbers::scrubGuid));
    Approvals.verify(input, new Options().withScrubber(Scrubbers::scrubGuid));
  }
  @Test
  void scrubGuids()
  {
    // begin-snippet: guid-scrubbing-1
    String[] guids = {"2fd78d4a-ad49-447d-96a8-deda585a9aa5",
                      "2fd78d4a-1111-1111-1111-deda585a9aa5",
                      "2fd78d4a-3333-3333-3333-deda585a9aa5",
                      "2fd78d4a-ad49-447d-96a8-deda585a9aa5",
                      "2fd78d4a-ad49-447d-96a8-deda585a9aa5 and text"};
    // end-snippet
    // begin-snippet: guid-scrubbing-2
    Approvals.verifyAll("guids", guids, new Options(Scrubbers::scrubGuid));
    // end-snippet
  }
  @Test
  void scrubMultipleThings()
  {
    // begin-snippet: MultiScrubber
    final Scrubber portScrubber = new RegExScrubber(":\\d+/", ":[port]/");
    final Scrubber dateScrubber = DateScrubber.getScrubberFor("20210505T091112Z");
    final Scrubber signatureScrubber = new RegExScrubber("Signature=.+", "Signature=[signature]");
    Scrubber scrubber = Scrubbers.scrubAll(portScrubber, dateScrubber, signatureScrubber);
    Approvals.verify("http://127.0.0.1:55079/foo/bar?Date=20210505T091112Z&Signature=4a7dd6f09c1e",
        new Options(scrubber));
    // end-snippet
  }
}
