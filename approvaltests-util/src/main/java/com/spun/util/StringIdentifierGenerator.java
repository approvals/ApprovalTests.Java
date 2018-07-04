package com.spun.util;

import java.util.UUID;

/**
 * @deprecated use {@code UUID.randomUUID().toString() }
 */
@Deprecated
public class StringIdentifierGenerator
{
  public static synchronized String nextIdentifier()
  {
    return UUID.randomUUID().toString();
  }
}
