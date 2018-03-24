package com.spun.util.io.xml;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.lambda.query.Query;

import com.spun.util.ObjectUtils;
import com.spun.util.filters.Filter;

/***********************************************************************/
public class XmlMap
{
  private String       settingFunction;
  private String       xmlName;
  private XmlExtractor extractor;
  private Method       settingMethod;
  private Class<?>     type;
  /***********************************************************************/
  public XmlMap(String xmlName, String settingFunction, Class<?> type)
  {
    this.xmlName = xmlName;
    this.settingFunction = settingFunction;
    this.type = type;
  }
  public XmlMap(String xmlName, String settingFunction)
  {
    this.xmlName = xmlName;
    this.settingFunction = settingFunction;
  }
  /***********************************************************************/
  public Method getSettingMethod()
  {
    return settingMethod;
  }
  /***********************************************************************/
  public String getNodeName()
  {
    return xmlName;
  }
  /***********************************************************************/
  public XmlMap(String xmlName, String settingFunction, XmlExtractor extractor)
  {
    this.xmlName = xmlName;
    this.settingFunction = settingFunction;
    this.extractor = extractor;
  }
  /***********************************************************************/
  public void initialize(Class<?> clazz) throws InstantiationException, IllegalAccessException
  {
    List<Method> methods = Query.where(clazz.getMethods(),
        m -> new SingleSetterMethodFilter(settingFunction, type).isExtracted(m));
    settingMethod = getBestMethodFit(methods);
    if (extractor == null)
    {
      Class<?> takes = settingMethod.getParameterTypes()[0];
      extractor = getExtractorFor(takes);
    }
  }
  /***********************************************************************/
  private XmlExtractor getExtractorFor(Class<?> takes) throws InstantiationException, IllegalAccessException
  {
    XmlExtractor extractor = BasicExtractor.get(takes);
    // basic type get basic type extractor
    if (extractor != null)
    {
    }
    else if (XmlExtractable.class.isAssignableFrom(takes))
    {
      extractor = new XmlMapExtractor(takes);
    }
    else
    {
      throw new Error("Unable to extract for class '" + takes.getName() + "'");
    }
    return extractor;
  }
  /***********************************************************************/
  private Method getBestMethodFit(List methods)
  {
    if (methods.size() == 0)
    {
      throw new Error("no method found for '" + settingFunction + "'");
    }
    else if (methods.size() == 1)
    {
      return (Method) methods.get(0);
    }
    else
    {
      throw new Error("Need to Find best method for '" + settingFunction + "' from " + methods);
    }
  }
  /***********************************************************************/
  public XmlExtractor getExtractor()
  {
    return extractor;
  }
  /***********************************************************************/
  public class SingleSetterMethodFilter implements Filter
  {
    private String   methodName = null;
    private Class<?> argument   = null;
    public SingleSetterMethodFilter(String methodName, Class<?> argument)
    {
      this.methodName = methodName;
      this.argument = argument;
    }
    /***********************************************************************/
    public boolean isExtracted(Object object) throws IllegalArgumentException
    {
      ObjectUtils.assertInstance(Method.class, object);
      Method method = (Method) object;
      return method.getParameterTypes().length == 1
          && (argument == null || method.getParameterTypes()[0] == argument)
          && Modifier.isPublic(method.getModifiers()) && method.getName().equals(methodName);
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
