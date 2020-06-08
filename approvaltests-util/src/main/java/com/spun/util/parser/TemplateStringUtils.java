package com.spun.util.parser;

import java.io.File;

import com.spun.util.StringUtils;

public class TemplateStringUtils
{
  public static TemplateStringUtils INSTANCE = new TemplateStringUtils();

  private TemplateStringUtils()
  {
  }
  
  public String truncate(String onString, int maxLength)
  {
    return truncate(onString, maxLength, new String[]{});
  }
  
  public String plural(int number, String singular, String plural)
  {
    return String.format("%s %s", number, (number == 1 ? singular : plural));
  }
  
  public String truncate(String onString, int maxLength, String minus1, String minus2)
  {
    return truncate(onString, maxLength, new String[]{"", "", minus1, minus2});
  }
  
  public String truncate(String onString, int maxLength, String minus1)
  {
    return truncate(onString, maxLength, new String[]{"", "", minus1});
  }
  
  private String truncate(String onString, int maxLength, String minus[])
  {
    if (onString == null || onString.equals("null")) { return ""; }
    for (int i = 2; i < minus.length; i++)
    {
      maxLength -= minus[i] != null ? minus[i].length() : 0;
    }
    maxLength = (maxLength < 0) ? 0 : maxLength;
    return StringUtils.truncate(onString, maxLength);
  }

  public static String clearNull(String value)
  {
    return clearNull(value, "");
  }

  public static String clearNull(Object value, String replacingValue)
  {
    return (("null".equals(value) || value == null) ? replacingValue : value.toString());
  }

  /**
   * Compare if arg [0] equals any other argument starting at [1].
   **/
  public static boolean isIn(String compare, String inParameter1, String inParameter2)
  {
    return StringUtils.isIn(compare, new String[]{inParameter1, inParameter2});
  }
  
  public static boolean isIn(String compare, String inParameter1)
  {
    return StringUtils.isIn(compare, new String[]{inParameter1});
  }
  
  public static String pad(String original, int length, String paddingCharacter)
  {
    original = original == null ? "" : original;
    int paddingNeeded = length - original.length();
    StringBuffer buffer = new StringBuffer(original);
    for (int i = 0; i < paddingNeeded; i++)
    {
      buffer.append(paddingCharacter);
    }
    return buffer.toString();
  }
  
  public String getFileName(String fileName)
  {
    return fileName.substring(fileName.lastIndexOf(File.separatorChar) + 1);
  }
  
  public static String formatExcelString(String in)
  {
    if ("0".equals(in) || "null".equals(in) || in == null) // So that null's & int(0) show up empty
    {
      return "\"\"";
    }
    else if (in.indexOf('\"') == -1)
    {
      return '\"' + in + '\"';
    }
    else
    {
      StringBuffer buffer = new StringBuffer();
      buffer.append('\"');
      for (int i = 0; i < in.length(); i++)
      {
        char c = in.charAt(i);
        buffer.append((c == '\"') ? "\"\"" : "" + c);
      }
      buffer.append('\"');
      return buffer.toString();
    }
  }

  public static int getLength(String s)
  {
    return s == null ? 0 : s.length();
  }

  public String toHTMLEncode(String string)
  {
    return StringUtils.toHTMLEncode(clearNull(string));
  }

  public String toURLEncode(String string)
  {
    return StringUtils.toURLEncode(clearNull(string));
  }

  public static String toJavaScriptEncode(String string)
  {
    return StringUtils.toJavaScriptEncode(clearNull(string, null));
  }

  public static String toJavaScriptEncode(Number n)
  {
    return n == null ? "null" : n.toString();
  }

  public static String toJavaScriptEncode(Object n)
  {
    if (n == null)
    {
      return "null";
    }
    else if (n instanceof Number)
    {
      return toJavaScriptEncode((Number) n);
    }
    else
    {
      return toJavaScriptEncode(n.toString());
    }
  }
  public static String asXml(String s)
  {
    return StringUtils.escapeForXml(s);
  }


}