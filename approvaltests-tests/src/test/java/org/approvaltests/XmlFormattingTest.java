package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class XmlFormattingTest
{
  @Disabled("SPIKE for #466 - continue next time")
  @Test
  void xmlWithEmojiesAndAmpersands()
  {
    var expected = """
        <?xml version='1.0' encoding='UTF-8'?>
        <a>
          <b>Tom &amp; Jerry</b>
          <emoji>😸</emoji>
        </a>
        """;
    String minimizedXml = expected.replaceAll("\n", "").replace("  ", "");
    Approvals.verifyXml(minimizedXml, new Options().inline(expected));
  }
}
