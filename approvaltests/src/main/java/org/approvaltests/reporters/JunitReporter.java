package org.approvaltests.reporters;

public class JunitReporter extends FirstWorkingReporter
{
  public static final JunitReporter INSTANCE = new JunitReporter();
  public JunitReporter()
  {
    super(new Junit5Reporter(), new Junit4Reporter(), new Junit3Reporter());
  }
}