package com.spun.util.filters;

/**
  * Listens to the state of a EnabledConditions object
  **/
  

public interface Filter<T>
{
	
	/**
		* @return true if the object would be extracted by the filter
		* @throws IllegalArgumentException if the object is not supported by the filter 
		**/
	public boolean isExtracted(T object)
		throws IllegalArgumentException;
  /**************************************************************************/
  /**************************************************************************/
}