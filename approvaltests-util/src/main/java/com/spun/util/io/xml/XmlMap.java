package com.spun.util.io.xml;

import org.lambda.query.Query;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class XmlMap
{
  private String       settingFunction;
  private String       xmlName;
  private XmlExtractor extractor;
  private Method       settingMethod;
  private Class<?>     type;
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

  public Method getSettingMethod()
  {
    return settingMethod;
  }

  public String getNodeName()
  {
    return xmlName;
  }

  public XmlMap(String xmlName, String settingFunction, XmlExtractor extractor)
  {
    this.xmlName = xmlName;
    this.settingFunction = settingFunction;
    this.extractor = extractor;
  }

  public void initialize(Class<?> clazz)
  {
    List<Method> methods = Query.where(clazz.getMethods(), m -> isSetterMethod(m, type, settingFunction));
    settingMethod = getBestMethodFit(methods);
    if (extractor == null)
    {
      Class<?> takes = settingMethod.getParameterTypes()[0];
      extractor = getExtractorFor(takes);
    }
  }

  private XmlExtractor getExtractorFor(Class<?> takes)
  {
    XmlExtractor extractor = BasicExtractor.get(takes);
    // basic type get basic type extractor
    if (extractor != null)
    {
    }
    else if (XmlExtractable.class.isAssignableFrom(takes))
    {
      extractor = new XmlMapExtractor((Class<? extends XmlExtractable>) takes);
    }
    else
    {
      throw new Error("Unable to extract for class '" + takes.getName() + "'");
    }
    return extractor;
  }

  private Method getBestMethodFit(List<Method> methods)
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

  public XmlExtractor getExtractor()
  {
    return extractor;
  }

  public static boolean isSetterMethod(Method method, Class<?> clazz, String methodName)
  {
    return method.getParameterTypes().length == 1 && (clazz == null || method.getParameterTypes()[0] == clazz)
        && Modifier.isPublic(method.getModifiers()) && method.getName().equals(methodName);
  }
}
