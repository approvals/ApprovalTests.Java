package com.spun.util.io;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InternetConnectivityTest
{
  @Test
  public void testInternetConnectivity() throws Exception
  {
    assertNull(InternetConnectivityException.testInternetConnectivity());
  }
}