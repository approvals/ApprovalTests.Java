package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

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
    verifyXml(minimizedXml, expected);
  }

  private static void verifyXml(String minimizedXml, String expected) {
    Options options = new Options().inline(expected);
    final String formattedXml = prettyPrintXml(minimizedXml);
    Approvals.verify(formattedXml, options.forFile().withExtension(".xml"));
  }

  private static String prettyPrintXml(String minimizedXml) {
    try
    {
      Source xmlInput = new StreamSource(new StringReader(minimizedXml));
      StringWriter stringWriter = new StringWriter();
      StreamResult xmlOutput = new StreamResult(stringWriter);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      transformerFactory.setAttribute("indent-number", 2);
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.transform(xmlInput, xmlOutput);
      try (Writer writer = xmlOutput.getWriter())
      {
        return writer.toString();
      }
    }
    catch (TransformerException | IOException e)
    {
      return minimizedXml;
    }
  }
}
