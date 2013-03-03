package com.spun.util.filters;

import java.util.ArrayList;

import com.spun.util.CallMethod;
import com.spun.util.filters.MethodFilter.CompareBy;

public class MethodFilterBuilder
{

  private CompareBy compareBy;
  private Object value;
  private CallMethod path;

  public MethodFilterBuilder(Class clazz)
  {
    this.path = new CallMethod(clazz);
  }

  public MethodFilterBuilder isEqual(Object o)
  {
    compareBy = CompareBy.EQUAL;
    value = o;
    return this;
  }

  public MethodFilterBuilder method(String methodName, Object... parameters)
  {
    path.method(methodName, parameters);
    return this;
  }

  public <T> ArrayList<T> filterExtracted(T[] all)
  {
    return FilterUtils.retainExtracted(all, getFilter());
  }

  public Filter getFilter()
  {
    return new MethodFilter(path.go(),compareBy,value);
  }
}
