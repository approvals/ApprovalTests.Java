package com.spun.util.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import com.spun.util.NumberUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.PhoneNumber;
import com.spun.util.velocity.ParserDateUtils;

public class ParserCommons
{
  public static TemplateStringUtils stringUtils = TemplateStringUtils.INSTANCE;
  public static TemplateNumberUtils numberUtils = TemplateNumberUtils.INSTANCE;
  public static TemplateDate today = TemplateDate.INSTANCE;
  public static ParserCommons INSTANCE = new ParserCommons();

  /***********************************************************************/
  public static TemplateNumberUtils getNumberUtils()
  {
    return numberUtils;
  }

  /***********************************************************************/
  public static Class getClass(String clazz) throws ClassNotFoundException
  {
    return Class.forName(clazz);
  }

  public static Object createNew(String clazz) throws InstantiationException,
      IllegalAccessException, ClassNotFoundException
  {
    return getClass(clazz).newInstance();
  }

  /***********************************************************************/
  public static Object getNull()
  {
    return null;
  }

  /***********************************************************************/
  public static ParserDateUtils getDateUtils()
  {
    return ParserDateUtils.INSTANCE;
  }

  /***********************************************************************/
  public static TemplateStringUtils getStringUtils()
  {
    return stringUtils;
  }

  /***********************************************************************/
  public static String asJavascript(Object obj)
  {
    return TemplateStringUtils.toJavaScriptEncode(obj);
  }

  /***********************************************************************/
  public static TemplateDate getToday()
  {
    return today;
  }

  /***********************************************************************/
  public static TemplateDate asDate(Date date)
  {
    return new TemplateDate(date);
  }

  /***********************************************************************/
  public static TemplateDouble asDouble(double number)
  {
    return new TemplateDouble(number);
  }

  /***********************************************************************/
  public static PhoneNumber asPhoneNumber(String number)
  {
    return new PhoneNumber(number);
  }

  /***********************************************************************/
  public static String asExcel(String text)
  {
    return TemplateStringUtils.formatExcelString(text);
  }

  /***********************************************************************/
  public static boolean isNull(Object object)
  {
    return object == null;
  }

  /***********************************************************************/
  public static PercentageAmount asPercentage(double d)
  {
    return new PercentageAmount(d);
  }

  /***********************************************************************/
  public static TemplateDouble asDouble(Number number)
  {
    return new TemplateDouble(number.doubleValue());
  }

  /***********************************************************************/
  public static List asArray(Object[] array, int offset, int stepping)
  {
    return new VelocityList(array, offset, stepping).getAll();
  }

  /***********************************************************************/
  public static List asArray(Object[] array, int offset, int stepping1,
      int stepping2)
  {
    return new VelocityList(array, offset, new int[] { stepping1, stepping2 })
        .getAll();
  }

  /***********************************************************************/
  public static List asArray(Object[] array)
  {
    return new VelocityList(array).getAll();
  }

  /***********************************************************************/
  public static List asArray(Object nullObject)
  {
    if (nullObject != null)
    {
      throw new Error("Improper usage");
    }
    return new VelocityList(Collections.EMPTY_LIST).getAll();
  }

  /***********************************************************************/
  public static List asArray(List list)
  {
    return new VelocityList(list).getAll();
  }

  /***********************************************************************/
  public static TemplateDouble asDouble(int number)
  {
    return new TemplateDouble(number);
  }

  /***********************************************************************/
  public static CurrencyAmount asCurrency(double number)
  {
    return new CurrencyAmount(number);
  }

  /***********************************************************************/
  public static CurrencyAmount asCurrency(Number number)
  {
    return number == null ? new CurrencyAmount(0.00) : new CurrencyAmount(
        number.doubleValue());
  }

  /***********************************************************************/
  public static CurrencyAmount asCurrency(int number)
  {
    return new CurrencyAmount(number);
  }

  /***********************************************************************/
  public static CurrencyAmount asCurrencyAmount(double number)
  {
    return new CurrencyAmount(number);
  }

  /***********************************************************************/
  public static CurrencyAmount asCurrencyAmount(Number number)
  {
    return new CurrencyAmount(number.doubleValue());
  }

  /***********************************************************************/
  public static CurrencyAmount asCurrencyAmount(int number)
  {
    return new CurrencyAmount(number);
  }

  /***********************************************************************/
  public static TemplateDouble sum(Object[] array, String methodName)
  {
    return new TemplateDouble(NumberUtils.sum(array, methodName, null));
  }

  /***********************************************************************/
  public static TemplateDouble sum(Object[] array, String methodName,
      Object param1)
  {
    return new TemplateDouble(NumberUtils.sum(array, methodName,
        new Object[] { param1 }));
  }

  /***********************************************************************/
  public static TemplateDouble sum(Object[] array, String methodName,
      Object param1, Object param2)
  {
    return new TemplateDouble(NumberUtils.sum(array, methodName, new Object[] {
        param1, param2 }));
  }

  /***********************************************************************/
  public static int getArrayLength(Object[] array)
  {
    return array == null ? 0 : array.length;
  }

  /***********************************************************************/
  public static int getLength(Object[] array)
  {
    return array == null ? 0 : array.length;
  }

  /***********************************************************************/
  public static int getLength(Collection collection)
  {
    return collection == null ? 0 : collection.size();
  }

  /***********************************************************************/
  public static boolean isEmpty(Object[] array)
  {
    return getArrayLength(array) == 0;
  }

  /***********************************************************************/
  public static boolean isNotEmpty(Object[] array)
  {
    return !isEmpty(array);
  }

  /***********************************************************************/
  public static boolean isEmpty(List list)
  {
    return (list == null || list.size() == 0);
  }

  /***********************************************************************/
  public static Object ternary(boolean test, Object ifTrue, Object ifFalse)
  {
    return test ? ifTrue : ifFalse;
  }

  /***********************************************************************/
  public static Object get(Object[] array, int index)
  {
    return getArrayLength(array) > index ? array[index] : null;
  }

  /***********************************************************************/
  public static Object get(List list, int index)
  {
    return list == null ? null : list.get(index);
  }

  /***********************************************************************/
  public static void throwError(String errorString)
  {
    throw new Error(errorString);
  }

  /***********************************************************************/
  public static void throwError(Throwable t)
  {
    throw ObjectUtils.throwAsError(t);
  }
  /***********************************************************************/
  /***********************************************************************/
}