package com.spun.util.io;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.spun.util.io.InternetConnectivityException;

public class InternetConnectivityTest
{
  @Test
  public void testInternetConnectivity() throws Exception
  {
    assertNull(InternetConnectivityException.testInternetConnectivity());
  }
}