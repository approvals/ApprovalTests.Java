package com.spun.util.tests.multitest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MultiDemo extends TestSuite implements UseCaseTester<String>
{
  /** This test is suppose to Fail 3 times when run. 
   *  The idea is to continue to run despite failures.
   *  line 16 is there to allow the full suite to pass.
   */
  public static Test suite() throws Exception
  {
    //return UseCaseTesting.all(new MultiDemo(), new String[]{"a", "2", "3", "b", "6", "9a"});
    return UseCaseTesting.all(new MultiDemo(), new String[]{ "2", "3"});
  }
  public void testUseCase(String string)
  {
    TestCase.assertNotNull(Integer.parseInt(string));
  }
}
