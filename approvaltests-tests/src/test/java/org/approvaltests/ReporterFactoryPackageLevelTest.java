package org.approvaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.PackageSettings.ApprovalTestsPackageLevelReporter;
import org.junit.jupiter.api.Test;

public class ReporterFactoryPackageLevelTest
{
  @Test
  public void test()
  {
    assertEquals(ApprovalTestsPackageLevelReporter.class, ReporterFactoryHelper.getClassFor());
  }
}
