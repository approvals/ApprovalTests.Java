package org.approvaltests.reporters;

import junit.framework.TestCase;

public class JunitReporter extends GenericJUnitReporter
{
  public static final JunitReporter INSTANCE = new JunitReporter();

  @Override
  void assertEquals(String aText, String rText)
  {
    TestCase.assertEquals(aText, rText);
  }

  @Override
  String getClassNameToTestJunitVersion()
  {
    return "junit.framework.TestCase";
  }
}