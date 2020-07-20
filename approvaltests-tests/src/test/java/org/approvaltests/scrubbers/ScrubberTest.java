package org.approvaltests.scrubbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
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
    String[] guids = {"2fd78d4a-ad49-447d-96a8-deda585a9aa5",
                      "2fd78d4a-1111-1111-1111-deda585a9aa5",
                      "2fd78d4a-3333-3333-3333-deda585a9aa5",
                      "2fd78d4a-ad49-447d-96a8-deda585a9aa5",
                      "2fd78d4a-ad49-447d-96a8-deda585a9aa5 and text"};
    Approvals.verifyAll("guids", guids, new Options(Scrubbers::scrubGuid));
  }
}
