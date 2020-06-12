package com.spun.util;

import com.spun.util.UniversalTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class UtilsTestSuite extends TestSuite
{
  public static Test suite() throws Exception
  {
    return UniversalTestSuite.createFor("com.spun.util.date");
  }
}
