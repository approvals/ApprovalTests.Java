package com.spun.util.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Node;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.io.XMLNodeExtractor;
import com.spun.util.io.XMLUtils;

public class TestConfig
{
  public static final class TESTING_LEVEL
  {
    public static final int      EXCLUDE    = 0;
    public static final int      BUILD      = 1;
    public static final int      PRODUCTION = 2;
    public static final String[] TEXT       = {"Exclude", "Build", "Production"};
  }
  private static int     defaultLevel = 0;
  private static HashMap overrides    = new HashMap();
  /***********************************************************************/
  public static void load(String fileName, String[] directories)
  {
    try
    {
      overrides = XMLUtils.parseProperties(XMLUtils.locateFile(fileName, directories), new NodeExtractor());
      defaultLevel = StringUtils.resolveEnumeration((String) overrides.get("DEFAULT_TESTING_LEVEL"), TESTING_LEVEL.TEXT, true);
      //My_System.variable("test config", new TestConfig().toString());
    }
    catch (Exception f)
    {
      ObjectUtils.throwAsError(f);
    }
  }
  /***********************************************************************/
  public static int getTestingLevel(Class<?> clazz)
  {
    String name = clazz.getName();
    Object o = overrides.get(name); 
    
    return (o == null) ? defaultLevel : ((Integer) o).intValue();
  }
  /***********************************************************************/
  public String toString()
  {
    StringBuffer output = new StringBuffer();
    output.append("DEFAULT_LEVEL = " + TESTING_LEVEL.TEXT[defaultLevel]);
    Object[] keys = overrides.keySet().toArray();
    for (int i = 0; i < keys.length; i++)
    {
      Object value = overrides.get(keys[i]);
      if (value instanceof Integer)
      {
        output.append("\n" + keys[i] + " = " + TESTING_LEVEL.TEXT[((Integer) value).intValue()]);
      }
    }
    return output.toString();
  }
  /***********************************************************************/
  /*                           INNER CLASS                               */
  /***********************************************************************/
  public static class NodeExtractor implements XMLNodeExtractor
  {

    /***********************************************************************/
    
    public void extractProperty(Node node, HashMap<String, Object> properties)
    {

      String name = node.getNodeName();

      if (XMLNodeExtractor.Utils.extractSingleton(node, properties))
      {
        // do nothing
      }
      else if ("OVERRIDE".equals(name))	 
      {
        Integer level  = null;
        
        ArrayList<String> v = new ArrayList<String>();
        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
          
          Node subNode = node.getChildNodes().item(i);
          if (subNode.getNodeName().equals("CLASS"))
          {
            v.add(StringUtils.loadNullableString(subNode.getChildNodes().item(0).getNodeValue()));
          }
          else if (subNode.getNodeName().equals("LEVEL"))
          {
            String levelText = StringUtils.loadNullableString(subNode.getChildNodes().item(0).getNodeValue());
            level = StringUtils.resolveEnumeration(levelText, TESTING_LEVEL.TEXT, true);
          }
        }
        String[] values = StringUtils.toArray(v);
        for (int i = 0; i < values.length; i++)
        {
          properties.put(values[i], level);
        }
      }
      
    }
    
  }
  /***********************************************************************/
  /***********************************************************************/
}