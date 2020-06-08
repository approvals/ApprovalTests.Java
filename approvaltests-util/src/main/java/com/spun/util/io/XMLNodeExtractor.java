package com.spun.util.io;

import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spun.util.StringUtils;


public interface XMLNodeExtractor
{
  
  public void extractProperty(Node node, HashMap<String, Object> properties);
  /************************************************************************/
  /*               INNER CLASS                                            */
  /************************************************************************/
  public static class Utils
  {
    public static boolean extractSingleton(Node node, HashMap<String, Object> properties)
    {
      String name = node.getNodeName();
      
      NodeList childNodes = node.getChildNodes();
      if ((childNodes.getLength() == 1) && (childNodes.item(0).getChildNodes().getLength() == 0))
      {
        properties.put(name, StringUtils.loadNullableString(childNodes.item(0).getNodeValue()));
        return true;
      }
      return false;
    }
  }
  /************************************************************************/
  /************************************************************************/
}