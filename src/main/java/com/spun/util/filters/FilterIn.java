package com.spun.util.filters;

import com.spun.util.ObjectUtils;

/**
  * Listens to the state of a EnabledConditions object
  **/
  

public class FilterIn
  implements Filter
{
  private Object[] objects;
  
  
  /***********************************************************************/
  public FilterIn(Object[] objects)
  {
    this.objects = objects;
  }
	/***********************************************************************/
	/**
		* @return true if the object would be extracted by the filter
		* @throws IllegalArgumentException if the object is not supported by the filter 
		**/
	public boolean isExtracted(Object object)
		throws IllegalArgumentException
  {
	  return ObjectUtils.isIn(object, objects);
  }
  /**************************************************************************/
  /**************************************************************************/
}