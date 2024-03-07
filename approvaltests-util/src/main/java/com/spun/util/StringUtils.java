package com.spun.util;

import org.lambda.functions.Function1;
import org.lambda.query.Query;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A static class of convenience functions for database access
 **/
public class StringUtils
{
  public static final String NEW_LINE = System.getProperty("line.separator");
  public static String toURLEncode(String input)
  {
    try
    {
      return input == null ? null : java.net.URLEncoder.encode(input, "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static String loadNullableString(String i)
  {
    return StringUtils.isNonZero(i) ? i.trim() : null;
  }
  /**
   * Decode a URLEncoded <code>input</code> String.
   * <p>
   *
   * If <code>input</code> is <code>null</code>, <code>URLEncode()</code> will
   * return <code>null</code>.
   *
   * @see java.net.URLDecoder
   */
  public static String URLDecode(String input)
  {
    try
    {
      return input == null ? null : java.net.URLDecoder.decode(input, "UTF-8");
    }
    catch (Exception e)
    {
      return null;
    }
  }
  public static String[] split(String string, String splitOn)
  {
    return split(string, splitOn, false);
  }
  public static String[] split(String string, String splitOn, boolean trim)
  {
    String[] result;
    if (string.endsWith(splitOn))
    {
      String ending = "ś".equals(splitOn) ? "š" : "ś";
      string = string + ending;
      String[] splitted = string.split(splitOn, -1);
      result = ArrayUtils.getSubsection(splitted, 0, splitted.length - 1);
    } else {
      result = string.split(splitOn);
    }
    return trim ? Query.select(result, a -> a.trim()).asArray() : result;
  }
  public static String replace(String string, String find, String replace)
  {
    if ((string == null) || (find == null) || (replace == null))
    { throw new NullPointerException(String.format("[string,find,replace] = [%s,%s,%s]", string, find, replace)); }
    String[] parts = split(string, find, false);
    if (parts.length == 1)
    { return string; }
    String result = "";
    for (int i = 0; i < parts.length; i++)
    {
      result += parts[i] + replace;
    }
    return result.substring(0, result.length() - replace.length());
  }
  public static String stripWhiteSpace(String text)
  {
    return stripWhiteSpace(text, false);
  }
  public static String padNumber(long number, int digits)
  {
    String text = "" + number;
    while (text.length() < digits)
    {
      text = "0" + text;
    }
    return text;
  }
  public static String padNumber(int number, int digits)
  {
    return padNumber((long) number, digits);
  }
  public static String stripWhiteSpace(String text, boolean all)
  {
    StringBuffer newText = new StringBuffer();
    boolean whitespace = false;
    int num = text.length();
    char whiteSpaceChar = ' ';
    boolean atStart = true;
    for (int i = 0; i < num; i++)
    {
      char c = text.charAt(i);
      switch (c)
      {
        case '\r' :
        case '\n' :
          whiteSpaceChar = '\n';
          whitespace = true;
          break;
        case '\t' :
        case ' ' :
          whitespace = true;
          break;
        default :
          if (whitespace && atStart)
          {
            whitespace = false;
          }
          if (whitespace)
          {
            whitespace = false;
            newText.append(all ? '_' : whiteSpaceChar);
            whiteSpaceChar = ' ';
          }
          atStart = false;
          newText.append(c);
          break;
      }
    }
    return newText.toString();
  }
  /**
   * Turns "mr. frank m Peter" into "Mr. Frank M Peter"
   **/
  public static String toNameUpperCase(String name)
  {
    if (name == null)
    { return null; }
    StringBuffer returning = new StringBuffer(name.length());
    String upper = name.toUpperCase();
    int place = 0;
    while (place < name.length())
    {
      char letter = name.charAt(place);
      if (letter == ' ')
      {
        returning.append(' ');
        if ((place + 1) < name.length())
        {
          returning.append(upper.charAt(++place));
        }
      }
      else if ((place == 0))
      {
        returning.append(upper.charAt(0));
      }
      else
      {
        returning.append(letter);
      }
      place++;
    }
    return returning.toString();
  }
  public static String toConvertCamelCaseString(String varName, String insertBeforeCaps)
  {
    StringBuffer staticVarName = new StringBuffer();
    for (int i = 0; i < varName.length(); i++)
    {
      char letter = varName.charAt(i);
      if ((i > 0) && (letter == Character.toUpperCase(letter)) && Character.isLetter(letter))
      {
        staticVarName.append(insertBeforeCaps);
      }
      staticVarName.append(letter);
    }
    return staticVarName.toString();
  }
  /**
   * Turns "ATTRIUBE_NAME" into "AttributeName"
   **/
  public static String toMethodNameCase(String name)
  {
    StringBuffer returning = new StringBuffer(name.length());
    String upper = name.toUpperCase();
    String lower = name.toLowerCase();
    int place = 0;
    while (place < name.length())
    {
      char letter = lower.charAt(place);
      if (letter == ' ' || letter == '_')
      {
        if ((place + 1) < name.length())
        {
          returning.append(upper.charAt(++place));
        }
      }
      else if ((place == 0))
      {
        returning.append(upper.charAt(0));
      }
      else
      {
        returning.append(letter);
      }
      place++;
    }
    return returning.toString();
  }
  public static String stripNonNumeric(String number)
  {
    return stripNonNumeric(number, false, false);
  }
  public static String stripCharacters(String dirtyString, String toStrip)
  {
    StringBuffer cleanStringBuffer = new StringBuffer();
    for (int i = 0; i < dirtyString.length(); i++)
    {
      char currentChar = dirtyString.charAt(i);
      if (toStrip.indexOf(currentChar) == -1)
      {
        cleanStringBuffer.append(currentChar);
      }
    }
    return cleanStringBuffer.toString();
  }
  public static String escapeForXml(String string)
  {
    return string.replaceAll("&", "&amp;");
  }
  public static String stripNonNumeric(String number, boolean allowDecimal, boolean allowNegative)
  {
    boolean allowExponential = allowDecimal;
    boolean afterE = false;
    if (number == null)
    { return ""; }
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < number.length(); i++)
    {
      char c = number.charAt(i);
      switch (c)
      {
        case '0' :
        case '1' :
        case '2' :
        case '3' :
        case '4' :
        case '5' :
        case '6' :
        case '7' :
        case '8' :
        case '9' :
          result.append(c);
          afterE = false;
          break;
        case '-' :
          if (allowNegative || afterE)
          {
            result.append(c);
            allowNegative = false;
          }
          break;
        case '.' :
          if (allowDecimal)
          {
            result.append(c);
            allowDecimal = false;
          }
          afterE = false;
          break;
        case 'e' :
        case 'E' :
          if (allowExponential)
          {
            result.append(c);
            allowExponential = false;
          }
          afterE = true;
          break;
        default :
          break;
      }
    }
    return result.toString();
  }
  /**
   * A convenience function to check that a String has at least 1 character.
   *
   * @param string
   *            The string in question
   * @return true if Non Zero.
   **/
  public static boolean isNonZero(String string)
  {
    return ((string != null) && string.trim().length() > 0);
  }
  public static boolean isEmpty(String string)
  {
    return !isNonZero(string);
  }
  /**
   * A convenience function to turn a vector of String objects into an Array
   * of the String objects.
   *
   * @param vectorOf
   *            a Vector of String objects
   * @return the array of String.
   * @throws Error
   *             if an element of vectorOf is not a String object.
   **/
  public static String[] toArray(java.util.Collection<String> vectorOf)
  {
    if (vectorOf == null)
    { return new String[0]; }
    String[] array = new String[vectorOf.size()];
    java.util.Iterator<String> iterator = vectorOf.iterator();
    int i = 0;
    while (iterator.hasNext())
    {
      java.lang.Object rowObject = iterator.next();
      if (rowObject instanceof String)
      {
        array[i++] = (String) rowObject;
      }
      else
      {
        throw new Error(
            "toArray[" + i + "] is not an instance of String but a " + ObjectUtils.getClassName(rowObject));
      }
    }
    return array;
  }
  public static int resolveEnumeration(String value, String[] enumeration)
  {
    return resolveEnumeration(value, enumeration, false);
  }
  public static int resolveEnumeration(String value, String[] enumeration, boolean force)
  {
    for (int i = 0; i < enumeration.length; i++)
    {
      if (enumeration[i].equals(value))
      { return i; }
    }
    if (force)
    { throw new Error("Enumeration '" + value + "' not in " + Arrays.asList(enumeration).toString()); }
    return -1;
  }
  public static String truncate(String string, int maxLength)
  {
    if (string == null)
    { return null; }
    return (string.length() <= maxLength) ? string : string.substring(0, maxLength);
  }
  public static boolean hasNumeric(String teamId)
  {
    return isNonZero(stripNonNumeric(teamId, false, false));
  }
  public static String toHTMLEncode(String string)
  {
    if (string == null)
    { return null; }
    string = string.replaceAll("<", "&lt;");
    string = string.replaceAll("\n", "<br />");
    return string;
  }
  public static String toJavaScriptEncode(String string)
  {
    if (string == null)
    { return "null"; }
    string = string.replaceAll("\"", "\\\\\"");
    string = string.replaceAll("\r", "\\\\r");
    string = string.replaceAll("\n", "\\\\n");
    return "\"" + string + "\"";
  }
  public static boolean isIn(String target, String... fromList)
  {
    return Arrays.asList(fromList).contains(target);
  }
  public static boolean isIn(String target, String[] fromList, boolean allowNulls)
  {
    return (target == null && allowNulls) ? true : isIn(target, fromList);
  }
  public static void assertIn(String target, String[] fromList, boolean allowNulls)
  {
    boolean valid = isIn(target, fromList, allowNulls);
    if (!valid)
    { throw new IllegalArgumentException("The value '" + target + "' not in " + Arrays.asList(fromList)); }
  }
  public static void assertIn(String target, boolean allowNulls, String... options)
  {
    assertIn(target, options, allowNulls);
  }
  public static String convertEnumeration(final Object forValue, Class<?> clazz)
  {
    Function1<Field, Boolean> filter = a -> {
      try
      {
        return (ClassUtils.IsPublicStatic(a) && a.get(null).equals(forValue));
      }
      catch (Throwable e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    };
    List<Field> fields = Query.where(clazz.getFields(), filter);
    if (fields.isEmpty())
    {
      return "unknown Type " + forValue;
    }
    else
    {
      return fields.get(0).getName();
    }
  }
  /**
   * 'Tom S Hardy' becomes 'Tom S' - 'Hardy'
   **/
  public static String[] splitName(String fullName)
  {
    String[] names = {null, null};
    int split = fullName.lastIndexOf(' ');
    if (split != -1)
    {
      names[0] = fullName.substring(0, split);
      names[1] = fullName.substring(split);
    }
    else
    {
      names[1] = fullName;
    }
    return names;
  }
  public static Properties createProperties(String[] properties)
  {
    Properties props = new Properties();
    if (properties == null)
    { return props; }
    if (properties.length % 2 != 0)
    {
      throw new Error(
          "number of strings must be even. found [" + properties.length + "] = " + Arrays.asList(properties));
    }
    for (int i = 0; i < properties.length; i += 2)
    {
      if (properties[i + 1] != null)
      {
        props.setProperty(properties[i], properties[i + 1]);
      }
      else
      {
        props.setProperty(properties[i], "");
      }
    }
    return props;
  }
  public static <T> String toString(String name, T[] array)
  {
    StringBuffer buffer = new StringBuffer();
    name = (name == null ? "array" : name);
    if (array == null || array.length == 0)
    {
      buffer.append(name + ".length = 0");
    }
    else
    {
      int maxPadding = ("" + array.length).length();
      for (int i = 0; i < array.length; i++)
      {
        buffer.append(name + "[" + padNumber(i, maxPadding) + "] = " + array[i] + "\n");
      }
    }
    return buffer.toString();
  }
  public static <T> String toString(String name, Iterable<T> array)
  {
    StringBuffer buffer = new StringBuffer();
    name = (name == null ? "array" : name);
    if (array != null)
    {
      int count = 0;
      for (T t : array)
      {
        buffer.append(name + "[" + count + "] = " + arrayStringHelper(t) + "\n");
        count++;
      }
    }
    if (buffer.length() == 0)
    {
      buffer.append(name + ".length = 0");
    }
    return buffer.toString();
  }
  public static String arrayStringHelper(Object o)
  {
    if (o == null)
    { return "null"; }
    return o.getClass().isArray() ? Arrays.toString((Object[]) o) : o.toString();
  }
  public static String getFirstName(String fullName)
  {
    return splitName(fullName)[0];
  }
  public static String getLastName(String fullName)
  {
    return splitName(fullName)[1];
  }
  public static boolean isLengthWithin(String string, int length)
  {
    return string == null || string.length() <= length;
  }
  public static boolean equalsIgnoreCase(String one, String two)
  {
    return (one == two || (one != null && one.equalsIgnoreCase(two)));
  }
  public static StringBuffer trim(StringBuffer buffer, int i)
  {
    if (buffer == null || buffer.length() < i)
    { return buffer; }
    buffer.setLength(buffer.length() - i);
    return buffer;
  }
  public static String join(String first, String joinBy, String second)
  {
    return isEmpty(second) ? first : first + joinBy + second;
  }
  public static InputStream convertToInputStream(String string)
  {
    return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
  }
  public static String toString(Map<?, ?> map)
  {
    StringBuffer b = new StringBuffer();
    Object[] keySet = map.keySet().toArray();
    if (!(map instanceof SortedMap))
    {
      Arrays.sort(keySet);
    }
    for (Object key : keySet)
    {
      b.append(String.format("%s : %s \n", key, map.get(key)));
    }
    return b.toString();
  }
  public static <T> String join(T[] list, String delimiter, Function1<T, String> convertor)
  {
    return String.join(delimiter, Query.select(list, convertor));
  }
  public static <T> String join(Collection<T> list, String joinWith)
  {
    return join(list, joinWith, n -> n.toString());
  }
  public static <T> String join(Collection<T> list, String joinWith, Function1<T, String> converter)
  {
    return String.join(joinWith, Query.select(list, converter));
  }
  public static String replaceAll(String input, Pattern pattern, Function1<String, String> replacer)
  {
    Matcher matcher = pattern.matcher(input);
    boolean result = matcher.find();
    if (result)
    {
      StringBuilder sb = new StringBuilder();
      int start = 0;
      do
      {
        sb.append(input, start, matcher.start());
        sb.append(replacer.call(matcher.group()));
        start = matcher.end();
        result = matcher.find();
      }
      while (result);
      sb.append(input.substring(start));
      return sb.toString();
    }
    return input;
  }
  public static String pad(String contents, int targetLength)
  {
    while (contents.length() < targetLength)
    {
      contents += " ";
      if (contents.length() < targetLength)
      {
        contents = " " + contents;
      }
    }
    return contents;
  }
  public static String padLeft(String contents, int targetLength)
  {
    while (contents.length() < targetLength)
    {
      contents = " " + contents;
    }
    return contents;
  }
  public static String removeFromEnd(String contents, String text)
  {
    return removeFromEnd(contents, text.length());
  }
  public static String removeFromEnd(String contents, int length)
  {
    return contents.substring(0, contents.length() - length);
  }
}
