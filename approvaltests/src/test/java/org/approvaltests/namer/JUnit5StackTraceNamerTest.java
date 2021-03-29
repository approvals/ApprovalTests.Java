package org.approvaltests.namer;

import java.io.File;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.spun.util.LambdaThreadLauncher;
import com.spun.util.ObjectUtils;
import com.spun.util.tests.TestUtils;

public class JUnit5StackTraceNamerTest
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "testGetApprovalName");
  }
  @ParameterizedTest
  @ValueSource(strings = {"A", "B"})
  public void parameterizedTest(String input)
  {
    StackTraceNamerUtils.assertParameterizedTest(getClass().getSimpleName(), "parameterizedTest", input);
  }
  @Nested
  class NestedTests
  {
    @Test
    void nestedTest()
    {
      String className = JUnit5StackTraceNamerTest.class.getSimpleName() + "." + getClass().getSimpleName();
      StackTraceNamerUtils.assertApprovalName(className, "nestedTest");
    }
  }
  @Test
  @DisplayName("hello")
  public void testWithDisplayName()
  {
    StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "testWithDisplayName");
  }
  @RepeatedTest(2)
  public void repeatedTest(RepetitionInfo repetitionInfo)
  {
    StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "repeatedTest");
  }
  @Test
  void approvalFromInsideLambda() throws Exception
  {
    Throwable[] caught = new Throwable[1];
    LambdaThreadLauncher lambda = new LambdaThreadLauncher((() -> {
      try
      {
        StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "approvalFromInsideLambda");
      }
      catch (Throwable e)
      {
        caught[0] = e;
      }
    }));
    lambda.getThread().join(1000);
    if (caught[0] != null)
    { throw ObjectUtils.throwAsError(caught[0]); }
  }
  @Test
  void testOverridingDirectoryFinder() throws Exception
  {
    try (AutoCloseable autoCloseable = TestUtils
        .registerSourceDirectoryFinder((c, s) -> new File("does not exist")))
    {
      StackTraceNamer name = new StackTraceNamer();
      File file = new File(name.getSourceFilePath() + this.getClass().getSimpleName() + ".java");
      Assertions.assertFalse(file.exists());
    }
    StackTraceNamerUtils.assertSourceFilePath(this.getClass().getSimpleName());
  }
}
