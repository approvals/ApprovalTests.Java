package com.spun.util.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class InternetConnectivityTest
{
  @Test
  public void testInternetConnectivity() throws Exception
  {
    assertNull(InternetConnectivityException.testInternetConnectivity());
  }
}
