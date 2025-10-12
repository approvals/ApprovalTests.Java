package org.approvaltests;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Serializer;
import org.approvaltests.core.Options;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class XmlXomApprovals
{
  public static void verifyXml(String xml)
  {
    verifyXml(xml, new Options());
  }

  public static void verifyXml(String xml, Options options)
  {
    Approvals.verifyXml(xml, x -> prettyPrintXml(x, 2), options);
  }

  public static String prettyPrintXml(String minimizedXml, int indent)
  {
    try
    {
      Builder builder = new Builder();
      Document doc = builder.build(new StringReader(minimizedXml));
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      Serializer serializer = new Serializer(byteArrayOutputStream, "UTF-8");
      serializer.setIndent(indent);
      serializer.setLineSeparator("\n");
      serializer.setMaxLength(0);
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
