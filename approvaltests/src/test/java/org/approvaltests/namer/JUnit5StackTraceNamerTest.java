package org.approvaltests.namer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lambda.actions.Action0;

import com.spun.util.LambdaThreadLauncher;
import com.spun.util.ObjectUtils;
import com.spun.util.tests.TestUtils;
import com.spun.util.tests.TestUtils.SourceDirectoryRestorer;

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
      StackTraceNamerUtils.assertApprovalName(className, "nestedTest", new StackTraceNamer());
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
  void testOverridingDirectoryFinder()
  {
    try (SourceDirectoryRestorer sdr = TestUtils
        .registerSourceDirectoryFinder((c, s) -> new File("does not exist")))
    {
      StackTraceNamer name = new StackTraceNamer();
      File file = new File(name.getSourceFilePath() + this.getClass().getSimpleName() + ".java");
      Assertions.assertFalse(file.exists());
    }
    StackTraceNamerUtils.assertSourceFilePath(this.getClass().getSimpleName(),
        new StackTraceNamer());
  }
  @TestFactory
  Collection<DynamicTest> testFactory()
  {
    return Arrays.asList(
        verifyDynamicTest("test 1",
            () -> StackTraceNamerUtils.assertNamerForFramework(this.getClass().getSimpleName(),
                "testFactory.test_1")),
        verifyDynamicTest("test 3",
            () -> StackTraceNamerUtils.assertNamerForFramework(this.getClass().getSimpleName(),
                "testFactory.test_3")),
        verifyDynamicTest("test 2", () -> StackTraceNamerUtils
            .assertNamerForFramework(this.getClass().getSimpleName(), "testFactory.test_2")));
  }
  @TestFactory
  Collection<DynamicTest> testFactory2()
  {
    return Collections.singletonList(dynamicTest("test 4", () -> {
      try
      {
        Approvals.verify("calling this must throw an exception");
        Assertions.fail();
      }
      catch (RuntimeException e)
      {
        assertEquals("Use dynamicApprovals(String, Executable) instead", e.getMessage());
      }
    }));
  }
  //  @Experimental
  private DynamicTest verifyDynamicTest(String displayName, Action0 action0)
  {
    return dynamicTest(displayName, () -> {
      try (NamedEnvironment en = NamerFactory.withParameters(convertToLegalFileName(displayName)))
      {
        action0.call();
      }
    });
  }
  public static String convertToLegalFileName(String uri)
  {
    return uri.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
  }
}
