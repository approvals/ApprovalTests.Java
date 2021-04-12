package org.approvaltests.writers;

import org.approvaltests.core.Options;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.io.Writer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ApprovalXmlWriter extends ApprovalTextWriter
{
  public ApprovalXmlWriter(String text, Options options)
  {
    super(prettyPrint(text, 2), options.forFile().withExtension(".xml"));
  }
  public static String prettyPrint(String input, int indent)
  {
    try
    {
      Source xmlInput = new StreamSource(new StringReader(input));
      StringWriter stringWriter = new StringWriter();
      StreamResult xmlOutput = new StreamResult(stringWriter);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      transformerFactory.setAttribute("indent-number", indent);
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
      return input;
    }
  }
}
