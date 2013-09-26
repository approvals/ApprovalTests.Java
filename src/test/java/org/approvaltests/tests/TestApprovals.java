package org.approvaltests.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.spun.util.tests.UniversalTestSuite;

public class TestApprovals extends TestSuite
{
  public static Test suite() throws Exception
  {
    return UniversalTestSuite.createFor("org.approvaltests");
  }
}