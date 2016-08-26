package com.spun.util.io.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlExtractorUtil
{
  /***********************************************************************/
  public static Object[] extract(Node node, String xmlName, Class<?> clazz) throws Exception
  {
    ArrayList<Object> list = new ArrayList<Object>();
    extractAndTranslateForNode(traverseToTag("XML", node), list, XmlMapTranslator.get(ArrayList.class,
        new XmlMap[]{new XmlMap(xmlName, "add", new XmlMapExtractor(clazz))}));
    return list.toArray();
  }
  /***********************************************************************/
  public static Node traverseToTag(String tag, Node node)
  {
    Node n = traverseToInnerTag(tag, node);
    if (n == null) { throw new NullPointerException(
        String.format("The Tag '%s' could not be found from '%s'", tag, node)); }
    return n;
  }
  /***********************************************************************/
  private static Node traverseToInnerTag(String tag, Node node)
  {
    String name = node.getNodeName();
    if (name.equals(tag))
    {
      return node;
    }
    else
    {
      NodeList childNodes = node.getChildNodes();
      for (int i = 0; i < childNodes.getLength(); i++)
      {
        Node child = traverseToInnerTag(tag, childNodes.item(i));
        if (child != null) { return child; }
      }
    }
    return null;
  }
  /***********************************************************************/
  public static Object extractAndTranslateForNode(Node node, Object addToObject, XmlTranslator translator)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException
  {
    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++)
    {
      if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE)
      {
        translator.extractAndTranslateForNode(childNodes.item(i), addToObject);
      }
    }
    return addToObject;
  }
  /***********************************************************************/
  /***********************************************************************/
}