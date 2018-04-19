package org.approvaltests.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.tests.PackageSettings.ApprovalTestsPackageLevelReporter;
import org.junit.Test;

public class ReporterFactoryPackageLevelTest
{
  @Test
  public void test()
  {
    assertEquals(ApprovalTestsPackageLevelReporter.class, ReporterFactoryHelper.getClassFor());
  }
}
