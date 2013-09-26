package com.spun.util;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spun.util.io.XMLNodeExtractor;

public class ConfigXMLNodeExtractor implements XMLNodeExtractor 
{
  /***********************************************************************/
  public void extractProperty(Node node, HashMap<String, Object> properties)
  {
    //String name = node.getNodeName();
    if (XMLNodeExtractor.Utils.extractSingleton(node, properties))
    {
      // do nothing
    }
    else if (node.getChildNodes().getLength() > 0)
    {
      NodeList childNodes = node.getChildNodes();
      ArrayList<String> v = new ArrayList<String>();
      for (int i = 0; i < childNodes.getLength(); i++)
      {
        if (childNodes.item(i).getNodeName().equals("VALUE"))
        {
          v.add(StringUtils.loadNullableString(childNodes.item(i).getChildNodes().item(0).getNodeValue()));
        }
      }
      properties.put(node.getNodeName(), StringUtils.toArray(v));
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
