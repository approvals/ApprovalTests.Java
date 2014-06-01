package com.spun.util.filters;

public class ClassFilter implements Filter
{
  private Class clazz;
  /***********************************************************************/
  public ClassFilter(Class clazz)
  {
    this.clazz = clazz;
  }
  /***********************************************************************/
  public boolean isExtracted(Object object) throws IllegalArgumentException
  {
    return clazz.isInstance(object);
  }
  /***********************************************************************/
  /***********************************************************************/
}
