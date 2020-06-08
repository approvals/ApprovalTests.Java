package com.spun.util.filters;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  {@literal a -> a instanceof desiredclass or a -> clazz.isInstance(a)}
 * 
 */
@Deprecated
public class ClassFilter implements Filter<Object>
{
  
  public ClassFilter(Class<?> clazz)
  {
    throw new DeprecatedException(" a -> a instanceof %s", clazz.getName());
  }
  
  public boolean isExtracted(Object object)
  {
    return false;
  }
  
  
}
