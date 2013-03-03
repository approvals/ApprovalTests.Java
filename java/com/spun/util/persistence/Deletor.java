package com.spun.util.persistence;

public interface Deletor <T>
{
  public void delete(T delete) throws SavingException;
}
