package com.spun.util.database;

import com.spun.util.ObjectUtils;
import com.spun.util.io.xml.XmlExtractable;
import com.spun.util.io.xml.XmlExtractor;
import com.spun.util.io.xml.XmlExtractorUtil;
import com.spun.util.io.xml.XmlMap;
import com.spun.util.io.xml.XmlMapTranslator;
import com.spun.util.io.xml.XmlTranslator;
import org.w3c.dom.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XmlDatabaseMapExtractor implements XmlExtractor
{
  private Class<?>      clazz;
  private XmlTranslator translator;
  private Method        creator = null;
  public XmlDatabaseMapExtractor(Class<?> clazz)
  {
    this.clazz = clazz;
  }
  public Object extractObjectForNode(Node node)
  {
    try
    {
      Object object = getCreator().invoke(null, (Object[]) null);
      XmlExtractorUtil.extractAndTranslateForNode(node, object, getTranslator());
      return object;
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  private XmlTranslator getTranslator() throws IllegalArgumentException, SecurityException, IllegalAccessException,
      InvocationTargetException, NoSuchMethodException, InstantiationException
  {
    if (translator == null)
    {
      XmlMap[] xmlMaps = ((XmlExtractable) getCreator().invoke(null, (Object[]) null)).getXmlMap();
      this.translator = XmlMapTranslator.get(clazz, xmlMaps);
    }
    return translator;
  }
  private Method getCreator() throws SecurityException, NoSuchMethodException
  {
    if (creator == null)
    {
      this.creator = clazz.getMethod("create", (Class[]) null);
    }
    return creator;
  }
}
