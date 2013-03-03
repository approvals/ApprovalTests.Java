package com.spun.util.filters;

/**
  * Listens to the state of a EnabledConditions object
  **/
  

public class OrFilter
  implements Filter
{
  private Filter filter1 = null;
  private Filter filter2 = null;
  
  
  /***********************************************************************/
  public OrFilter(Filter filter1,Filter filter2)
  {
    this.filter1 = filter1;
    this.filter2 = filter2;
  }
	/***********************************************************************/
	/**
		* @return true if the object would be extracted by the filter
		* @throws IllegalArgumentException if the object is not supported by the filter 
		**/
	public boolean isExtracted(Object object)
		throws IllegalArgumentException
  {
    return filter1.isExtracted(object) || filter2.isExtracted(object);
  }
  /**************************************************************************/
  /**************************************************************************/
}