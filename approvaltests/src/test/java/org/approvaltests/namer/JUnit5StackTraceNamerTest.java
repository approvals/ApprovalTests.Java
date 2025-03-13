package org.approvaltests.namer;

import com.spun.util.LambdaThreadLauncher;
import com.spun.util.ObjectUtils;
import com.spun.util.tests.TestUtils;
import com.spun.util.tests.TestUtils.SourceDirectoryRestorer;
import org.approvaltests.Approvals;
import org.approvaltests.integrations.junit5.JupiterApprovals;
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

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

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
  void approvalFromInsideLambda()
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
    ObjectUtils.throwAsError(() -> lambda.getThread().join(1000));
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
    StackTraceNamerUtils.assertSourceFilePath(this.getClass().getSimpleName(), new StackTraceNamer());
  }
  @TestFactory
  Collection<DynamicTest> testFactory()
  {
    return Arrays.asList(
        JupiterApprovals.dynamicTest("test 1",
            (o) -> StackTraceNamerUtils.assertNamerForFramework(this.getClass().getSimpleName(),
                "testFactory.test_1", o.forFile().getNamer())),
        JupiterApprovals.dynamicTest("test 3",
            (o) -> StackTraceNamerUtils.assertNamerForFramework(this.getClass().getSimpleName(),
                "testFactory.test_3", o.forFile().getNamer())),
        JupiterApprovals.dynamicTest("test 3",
            (o) -> StackTraceNamerUtils.assertNamerForFramework(this.getClass().getSimpleName(),
                "testFactory.test_3", o.forFile().getNamer())),
        JupiterApprovals.dynamicTest("test 2",
            (o) -> StackTraceNamerUtils.assertNamerForFramework(this.getClass().getSimpleName(),
                "testFactory.test_2", o.forFile().getNamer())));
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
        String helpMessage = "When using dynamic tests and Approvals, Instead use:  \n"
            + "  org.approvaltests.integrations.junit5.JupiterApprovals.dynamicTest(String, Executable)\n"
            + " More at: https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UseTestFactory.md";
        assertEquals(helpMessage, e.getMessage());
      }
    }));
  }
  // begin-snippet: java_dynamic_test
  @TestFactory
  Collection<DynamicTest> testFactory3()
  {
    return Stream.of(1, 2).map(number -> JupiterApprovals.dynamicTest("test " + number,
        o -> Approvals.verify("content for " + number, o))).collect(Collectors.toList());
  }
  // end-snippet
  @TestFactory
  Collection<DynamicTest> testMissingOptions()
  {
    return Stream.of(1, 2).map(number -> JupiterApprovals.dynamicTest("test " + number, o -> {
        switch (number) {
            case 1:
                Approvals.verifyAsJson("This should work: " + number, o);
                break;
            case 2:
                try {
                    Approvals.verify("calling this must throw an exception");
                    Assertions.fail();
                } catch (RuntimeException e) {
                    String helpMessage = "When using dynamic tests and Approvals, all calls to verify() must use the original Options or a derivative:  \n"
                            + "   wrong: o -> Approvals.verify(result);  \n" + "   right: o -> Approvals.verify(result, o);  \n"
                            + " More at: https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UseTestFactory.md";
                    assertEquals(helpMessage, e.getMessage());
                }
                break;
        }
    })).collect(Collectors.toList());
  }
}
