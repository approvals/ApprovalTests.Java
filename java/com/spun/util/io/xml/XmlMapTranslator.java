package com.spun.util.io.xml;

/***********************************************************************/

public class XmlMapTranslator
{
  /***********************************************************************/
  
  public static XmlTranslator get(Class<?> clazz, XmlMap[] maps) throws InstantiationException, IllegalAccessException
  {
    for (int i = 0; i < maps.length; i++)
    {
      maps[i].initialize(clazz);
    }
    return new XmlTranslator(maps);
    
  }
  /***********************************************************************/
  /***********************************************************************/

}
