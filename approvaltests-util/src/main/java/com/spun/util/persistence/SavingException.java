package com.spun.util.persistence;

public class SavingException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  public SavingException()
  {
  }

  public SavingException(Throwable cause)
  {
    super(cause);
  }
}
