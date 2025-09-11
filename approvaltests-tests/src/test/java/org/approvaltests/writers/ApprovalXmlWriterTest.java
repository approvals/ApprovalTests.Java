package org.approvaltests.writers;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.RegExScrubber;
import org.approvaltests.utils.ConsoleOutput;
import org.junit.jupiter.api.Test;

public class ApprovalXmlWriterTest
{
  @Test
  public void plainXml()
  {
    Approvals.verifyXml("<xml><hello/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithAttributes()
  {
    Approvals.verifyXml("<xml b=\"123\" a=\"456\"><hello x=\"y\"/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithDeepAttributes()
  {
    Approvals
        .verifyXml("<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>");
  }
  @Test
  public void xmlWithDeepAttributesWithScrubber()
  {
    Approvals.verifyXml(
        "<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>",
        new Options(new RegExScrubber("hi", "hello")));
  }
  @Test
  public void invalidXml()
  {
    var expected = """
        <xml><hello/><start>hi</xml>
        """;
    try (var __ = new ConsoleOutput())
    {
      Approvals.verifyXml("<xml><hello/><start>hi</xml>", new Options().inline(expected));
    }
  }
  @Test
  public void validXml()
  {
    var expected = """
        <?xml version="1.0" encoding="UTF-8"?><xml>
          <hello/>
          <start>hi</start>
        </xml>
        """;
    Approvals.verifyXml("<xml><hello/><start>hi</start></xml>", new Options().inline(expected));
  }
}
