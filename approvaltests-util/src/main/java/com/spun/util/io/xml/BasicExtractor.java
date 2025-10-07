package com.spun.util.io.xml;

import com.spun.util.io.XMLUtils;
import org.w3c.dom.Node;

import java.sql.Timestamp;

public class BasicExtractor
{
  public static XmlExtractor get(Class<?> clazz)
  {
    XmlExtractor extractor = null;
    if (String.class.isAssignableFrom(clazz))
    {
      extractor = StringExtractor.INSTANCE;
    }
    else if (double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz))
    {
      extractor = DoubleExtractor.INSTANCE;
    }
    else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz))
    {
      extractor = IntegerExtractor.INSTANCE;
    }
    else if (boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz))
    {
      extractor = BooleanExtractor.INSTANCE;
    }
    else if (Timestamp.class.isAssignableFrom(clazz))
    {
      extractor = TimestampExtractor.INSTANCE;
    }
    return extractor;
  }
  private static abstract class ExtractorBase implements XmlExtractor
  {
    public String toString()
    {
      String name = this.getClass().getName();
      int divider = name.lastIndexOf("$");
      return name.substring(divider + 1);
    }
  }
  public static class StringExtractor extends ExtractorBase
  {
    public static StringExtractor INSTANCE = new StringExtractor();
    private StringExtractor()
    {
    }

    public Object extractObjectForNode(Node node)
    {
      return XMLUtils.extractStringValue(node);
    }
  }
  public static class DoubleExtractor extends ExtractorBase
  {
    public static DoubleExtractor INSTANCE = new DoubleExtractor();
    private DoubleExtractor()
    {
    }

    public Object extractObjectForNode(Node node)
    {
      return Double.valueOf(XMLUtils.extractStringValue(node));
    }
  }
  public static class IntegerExtractor extends ExtractorBase
  {
    public static IntegerExtractor INSTANCE = new IntegerExtractor();
    private IntegerExtractor()
    {
    }

    public Object extractObjectForNode(Node node)
    {
      return Integer.valueOf(XMLUtils.extractStringValue(node));
    }
  }
  public static class BooleanExtractor extends ExtractorBase
  {
    public static BooleanExtractor INSTANCE = new BooleanExtractor();
    private BooleanExtractor()
    {
    }

    public Object extractObjectForNode(Node node)
    {
      return Boolean.valueOf(XMLUtils.extractStringValue(node));
    }
  }
  public static class TimestampExtractor extends ExtractorBase
  {
    public static TimestampExtractor INSTANCE = new TimestampExtractor();
    private TimestampExtractor()
    {
    }

    public Object extractObjectForNode(Node node)
    {
      return Timestamp.valueOf(XMLUtils.extractStringValue(node));
    }
  }
}
