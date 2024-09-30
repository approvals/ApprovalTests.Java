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
  @Disabled("SPIKE - continue next time")
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
    String input = expected.replaceAll("\n", "").replace("  ", "");
    Approvals.verify(prettyPrint(input, 2), new Options().inline(expected));
  }
  private static String prettyPrint(String expected, int tabSize)
  {
    try
    {
      Source xmlInput = new StreamSource(new StringReader(expected));
      StringWriter stringWriter = new StringWriter();
      StreamResult xmlOutput = new StreamResult(stringWriter);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      transformerFactory.setAttribute("indent-number", tabSize);
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
      return expected;
    }
  }
}
