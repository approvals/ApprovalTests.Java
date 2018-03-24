package com.spun.util.filters;

import java.util.Date;

import com.spun.util.MethodExecutionPath;
import com.spun.util.ObjectUtils;

/************************************************************************/
/**
 * @deprecated use Query.where()
 */
public class MethodFilter implements com.spun.util.filters.Filter
{
  private MethodExecutionPath path = null;
  private Object value = null;
  private CompareBy operation;
  public enum CompareBy{GREATER_THAN_OR_EQUAL,LESS_THAN_OR_EQUAL,EQUAL}


  /************************************************************************/
  public MethodFilter(Class<?> clazz, Object value, String... methodNames)
  {
    this(new MethodExecutionPath(clazz, methodNames), CompareBy.EQUAL, value);
  }
  /************************************************************************/
  public MethodFilter(Class<?> clazz, Object value, CompareBy operation, String... methodNames)
  {
    this(new MethodExecutionPath(clazz, methodNames), operation, value);
  }
  /************************************************************************/
  public MethodFilter(Class<?> clazz, Object value, MethodExecutionPath path)
  {
    this(path, CompareBy.EQUAL, value);
  }
  /************************************************************************/
  public MethodFilter(MethodExecutionPath path, CompareBy operation, Object value)
  {
    this.value = value;
    this.path = path;
    this.operation = operation;
    ObjectUtils.assertInstanceOrNull(path.getReturnType(), value);
  }
  public MethodFilter(Class<?> clazz, Object value, String method, Object parameter)
  {
    this(MethodExecutionPath.method(clazz, method, parameter), CompareBy.EQUAL, value);
  }
  /************************************************************************/

  public boolean isExtracted(Object object) throws IllegalArgumentException
  {
    ObjectUtils.assertInstance(path.getClassType(), object);
    Object extracted = path.extractValue(object);
    switch (operation) 
    {
      case EQUAL:
        return ObjectUtils.isEqual(extracted, value);
      case GREATER_THAN_OR_EQUAL:
        if (value instanceof Date)
        {
          ObjectUtils.assertInstance(Date.class, extracted);
          return ((Date)value).getTime() >= ((Date)extracted).getTime()  ;
        }
        ObjectUtils.assertInstance(Number.class, value);
        ObjectUtils.assertInstance(Number.class, extracted);
        return ((Number)value).doubleValue() >= ((Number)extracted).doubleValue() ;
      default:
        return false;
    }
    
  }
  public static MethodFilterBuilder on(Class<?> clazz)
  {
    return new MethodFilterBuilder(clazz);
  }


  /************************************************************************/
  /************************************************************************/
}