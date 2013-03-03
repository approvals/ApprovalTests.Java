package com.spun.util.io.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;
import org.w3c.dom.Node;

/***********************************************************************/
public class XmlTranslator
{
  private HashMap<String,XmlExtractor> extractors = new HashMap<String,XmlExtractor>();
  private HashMap<String,Method> setters    = new HashMap<String,Method>();
  /***********************************************************************/
  public XmlTranslator(XmlMap[] maps)
  {
    for (int i = 0; i < maps.length; i++)
    {
      String nodeName = maps[i].getNodeName();
      extractors.put(nodeName, maps[i].getExtractor());
      setters.put(nodeName, maps[i].getSettingMethod());
    }
  }
  /***********************************************************************/
  public Object extractAndTranslateForNode(Node node, Object addToObject) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException, InstantiationException
  {
    String name = node.getNodeName();
    Method method = (Method) setters.get(name);
    XmlExtractor extractor = (XmlExtractor) extractors.get(name);
    if (extractor == null) {
      throw new Error("No Extractor Found for Node '" + getNamePath(node) + "'"); 
      }
    Object o = extractor.extractObjectForNode(node);
    this.setObject(o, addToObject, method);
    return o;
  }
  /***********************************************************************/
  private static String getNamePath(Node node)
  {
    StringBuffer buffer = new StringBuffer(node.getNodeName());
    while (node.getParentNode() != null)
    {
      node = node.getParentNode();
      buffer.insert(0, node.getNodeName() + ".");
    }
    return buffer.toString();
  }
  /***********************************************************************/
  private void setObject(Object o, Object addToObject, Method settingMethod) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException
  {
    try
    {
      settingMethod.invoke(addToObject, new Object[]{o});
    }
    catch (IllegalArgumentException e)
    {
      throw e;
    }
  }
  public String toString()
  {
    StringBuffer out = new StringBuffer();
    for (Entry<String, XmlExtractor> entry : extractors.entrySet() )
    {
      String key = entry.getKey();
      XmlExtractor value = entry.getValue(); 
      out.append(String.format("%s => %s\n",key, value));
    }
    return out.toString();
  }
  /***********************************************************************/
  /***********************************************************************/
}
