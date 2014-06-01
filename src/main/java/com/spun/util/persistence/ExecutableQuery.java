package com.spun.util.persistence;

public interface ExecutableQuery
{
  public abstract String getQuery() throws Exception;
  public abstract String executeQuery(String query) throws Exception;
}