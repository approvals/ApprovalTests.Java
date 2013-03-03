package com.spun.util.persistence;


public interface Saver<T>
{
  public T save(T save) throws SavingException;
}
