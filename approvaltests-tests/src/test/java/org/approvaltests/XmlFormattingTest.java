package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

class XmlFormattingTest
{
  @Test
  void xmlWithEmojiesAndAmpersands()
  {
    var expected = """
        <?xml version="1.0" encoding="UTF-8"?>
        <a>
          <b>😸 &amp; 🐶</b>
        </a>
        """;
    String minimizedXml = expected.replaceAll("\n", " ").replace("  ", "");
    XmlXomApprovals.verifyXml(minimizedXml, new Options().inline(expected));
  }
}
