package org.approvaltests;

import nu.xom.*;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.io.*;

class XmlFormattingTest
{
  // @Disabled("SPIKE for #466 - continue next time")
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
    verifyXml(minimizedXml, expected);
  }
  private static void verifyXml(String minimizedXml, String expected)
  {
    Options options = new Options().inline(expected);
    final String formattedXml = prettyPrintXml(minimizedXml, 2);
    Approvals.verify(formattedXml, options.forFile().withExtension(".xml"));
  }
  private static String prettyPrintXml(String minimizedXml, int indent)
  {
    try
    {
      Builder builder = new Builder();
      Document doc = builder.build(new StringReader(minimizedXml));
      // create a string output stream
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      Serializer serializer = new Serializer(byteArrayOutputStream, "UTF-8");
      serializer.setIndent(indent); // Increase indentation to make XML more readable
      serializer.setLineSeparator("\n"); // Ensure new lines are added
      serializer.setMaxLength(0); // Prevent line wrapping
      serializer.write(doc);
      return byteArrayOutputStream.toString("UTF-8");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return minimizedXml + "\n\nXML Parsing Error:\n" + e.getMessage();
    }
  }
}
