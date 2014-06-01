package com.spun.util.io.xml;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Node;

/***********************************************************************/

public interface XmlExtractor
{

  /***********************************************************************/
  
  public Object extractObjectForNode(Node node) 
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException;
  /***********************************************************************/
  /***********************************************************************/
}
