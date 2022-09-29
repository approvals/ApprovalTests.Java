package org.approvaltests;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import org.approvaltests.core.Options;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.StringWriter;

public class JsonXtreamApprovals
{
  public static void verifyAsJson(Object o)
  {
    verifyAsJson(o, new Options());
  }
  public static void verifyAsJson(Object o, Options options)
  {
    Approvals.verify(asJson(o), options.forFile().withExtension(".json"));
  }
  public static String asJson(Object o)
  {
    XStream xstream = new XStream(new JsonHierarchicalStreamDriver()); 
    return xstream.toXML(o);
  }
}
