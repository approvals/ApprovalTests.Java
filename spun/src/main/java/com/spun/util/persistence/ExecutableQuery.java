package com.spun.util.persistence;

public interface ExecutableQuery
{
  public abstract String getQuery();
  public abstract String executeQuery(String query);
}