package com.spun.util.io.xml;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import org.w3c.dom.Node;

public class XmlMapExtractor implements XmlExtractor
{
  private Class<?>      clazz;
  private XmlTranslator translator;
  public XmlMapExtractor(XmlMap[] xmlMaps, Class<?> clazz)
  {
    this.clazz = clazz;
    this.translator = XmlMapTranslator.get(clazz, xmlMaps);
  }

  public XmlMapExtractor(Class<? extends XmlExtractable> clazz)
  {
    this(ClassUtils.create(clazz).getXmlMap(), clazz);
  }

  public Object extractObjectForNode(Node node)
  {
    try
    {
      Object object = ClassUtils.create(clazz);
      XmlExtractorUtil.extractAndTranslateForNode(node, object, translator);
      return object;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }

  public String toString()
  {
    return ClassUtils.getClassName(clazz) + ".extractor";
  }
}
