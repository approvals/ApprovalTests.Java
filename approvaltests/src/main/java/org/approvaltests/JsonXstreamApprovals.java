package org.approvaltests;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import org.approvaltests.core.Options;

/**
 * Requires (XStream)[https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream]
 * Requires (Jettison)[https://mvnrepository.com/artifact/org.codehaus.jettison/jettison]
 * Requires (Camel-XStream)[https://mvnrepository.com/artifact/org.apache.camel/camel-xstream]
 */
public class JsonXstreamApprovals
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
