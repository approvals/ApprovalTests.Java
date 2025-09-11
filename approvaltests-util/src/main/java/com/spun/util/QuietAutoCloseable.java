package com.spun.util;

public interface QuietAutoCloseable extends AutoCloseable
{
  void close();
}
