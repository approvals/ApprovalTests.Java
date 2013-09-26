package com.spun.util.persistence.tests;

import com.spun.util.persistence.Deletor;

public class MockDeletor<T> implements Deletor<T>
{

  private T deleted;

  public void delete(T delete)
  {
    this.deleted = delete;
    
  }

  public T getDeleted()
  {
    return deleted;
  }
}
