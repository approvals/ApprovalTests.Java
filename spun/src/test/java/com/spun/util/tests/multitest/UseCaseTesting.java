package com.spun.util.tests.multitest;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UseCaseTesting
{
  public static <T> Test all(UseCaseTester<T> multiTest, T[] cases)
  {
    TestSuite suite = new TestSuite("Test for UseCases");
    for (T c : cases)
    {
      testUseCase(suite,multiTest, c);
    }
    return suite;
  }
  public static <T> void testUseCase(TestSuite suite, final UseCaseTester<T> tester, final T caze)
  {
    String name = caze.toString().replace('(', '|'); // workaround for odd naming bug
    TestCase test = new TestCase("Test " + name)
    {
      public void runTest()
      {
        tester.testUseCase(caze);
      }
    };
    suite.addTest(test);
  }
}
