package com.spun.util.persistence;


public  interface Loader<T>
{
  public T load() throws Exception;
}
