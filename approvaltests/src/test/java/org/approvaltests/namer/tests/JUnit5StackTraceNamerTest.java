package org.approvaltests.namer.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    StackTraceNamerUtils.assertParameterizedTest(className, "parameterizedTest", input);
  }
}
