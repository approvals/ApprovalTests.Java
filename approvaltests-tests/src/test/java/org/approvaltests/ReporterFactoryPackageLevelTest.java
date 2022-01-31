package org.approvaltests;

import org.approvaltests.PackageSettings.ApprovalTestsPackageLevelReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReporterFactoryPackageLevelTest
{
  @Test
  public void test()
  {
    assertEquals(ApprovalTestsPackageLevelReporter.class, ReporterFactoryHelper.getClassFor());
  }
}
