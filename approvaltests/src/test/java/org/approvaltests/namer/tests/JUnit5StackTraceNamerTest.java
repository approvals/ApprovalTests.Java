package org.approvaltests.namer.tests;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;

public class JUnit5StackTraceNamerTest
{
  String className = this.getClass().getSimpleName();
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework(className, "testGetApprovalName");
  }
  @ParameterizedTest
  @ValueSource(strings = {"A", "B"})
  public void parameterizedTest(String input)
  {
    try (NamedEnvironment en = NamerFactory.asMachineSpecificTest(input))
    {
      ApprovalNamer name = Approvals.createApprovalNamer();
      Assert.assertEquals(className + ".parameterizedTest." + input, name.getApprovalName());
    }
  }
}
