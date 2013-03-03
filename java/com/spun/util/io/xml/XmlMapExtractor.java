package com.spun.util.io.xml;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Node;
import com.spun.util.ClassUtils;

/***********************************************************************/

public class XmlMapExtractor implements XmlExtractor
{
  private Class clazz;
  private XmlTranslator translator;
  /***********************************************************************/
  public XmlMapExtractor(XmlMap[] xmlMaps, Class clazz) throws InstantiationException, IllegalAccessException
  {
    this.clazz = clazz;
    this.translator = XmlMapTranslator.get(clazz, xmlMaps);

    
  }
  /***********************************************************************/
  public XmlMapExtractor(Class clazz) throws InstantiationException, IllegalAccessException 
  {
    
    this(((XmlExtractable)clazz.newInstance()).getXmlMap(), clazz);
  }
  /***********************************************************************/
  
  public Object extractObjectForNode(Node node) 
    throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException
  {
    Object object = clazz.newInstance();
    XmlExtractorUtil.extractAndTranslateForNode(node, object, translator);
    return object;
    
  }
  public String toString()
  {
    return ClassUtils.getClassName(clazz) + ".extractor";
  }
  /***********************************************************************/
  /***********************************************************************/
}
