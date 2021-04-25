package org.approvaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.core.Scrubber;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.UseReporterTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import com.spun.util.ArrayUtils;

public class OptionsTest
{
  @Test
  void cannotInstantiateWithoutScrubber()
  {
    Scrubber nullScrubber = null;
    assertThrows(NullPointerException.class, () -> new Options(nullScrubber));
    assertThrows(NullPointerException.class, () -> new Options().withScrubber(nullScrubber));
  }
  @Test
  void cannotInstantiateWithoutReporter()
  {
    ApprovalFailureReporter nullReporter = null;
    assertThrows(NullPointerException.class, () -> new Options(nullReporter));
    assertThrows(NullPointerException.class, () -> new Options().withReporter(nullReporter));
  }
  @Test
  void verifyWithReporterInOptions()
  {
    ApprovalFailureReporterSpy reporter = new ApprovalFailureReporterSpy();
    Options options = new Options(reporter);
    try
    {
      Approvals.verify("hallo", options);
    }
    catch (Error error)
    {
      assertTrue(reporter.hasBeenCalled());
    }
  }
  @Test
  void verifyWithReporterFromOptionsBuilder()
  {
    ApprovalFailureReporterSpy reporter = new ApprovalFailureReporterSpy();
    Options options = new Options().withReporter(reporter);
    try
    {
      Approvals.verify("hallo", options);
    }
    catch (Error error)
    {
      assertTrue(reporter.hasBeenCalled());
    }
  }
  @Test
  @UseReporter(UseReporterTest.TestReporter.class)
  void verifyWithoutReporter()
  {
    FirstWorkingReporter reporter = (FirstWorkingReporter) new Options().getReporter();
    assertEquals(UseReporterTest.TestReporter.class, reporter.getReporters()[1].getClass());
  }
  @Test
  void testTheVerifyApi()
  {
    Method[] declaredMethods = Approvals.class.getDeclaredMethods();
    List<Method> methodList = Query.where(declaredMethods, m -> m.getName().startsWith("verify"));
    methodList = Query.orderBy(methodList, m -> m.toString());
    Approvals.verifyAll("verifyMethods", methodList, m -> m.toString(), new Options());
  }
  @Test
  void testEachMethodHasOneWithOptions()
  {
    verifyEachVerifyMethodHasOneWithOptions(Approvals.class);
    verifyEachVerifyMethodHasOneWithOptions(CombinationApprovals.class);
  }
  private void verifyEachVerifyMethodHasOneWithOptions(Class<?> approvalsClass)
  {
    Queryable<Method> declaredMethods = Queryable.as(approvalsClass.getDeclaredMethods());
    Queryable<Method> methodList = declaredMethods.where(m -> m.getName().startsWith("verify")
        && Modifier.isPublic(m.getModifiers()) && !m.isAnnotationPresent(Deprecated.class));
    List<Method> methodsWithOptions = methodList.where(m -> isOptionsPresent(m));
    List<Method> methodsWithoutOptions = methodList.where(m -> !(isOptionsPresent(m)));
    for (Method withOptions : methodsWithOptions)
    {
      String name = withOptions.getName();
      Class<?>[] parameterTypes = withOptions.getParameterTypes();
      Class<?>[] parameters = getWithoutOptions(parameterTypes);
      boolean foundWithoutOptions = Query.any(methodsWithoutOptions,
          m -> name.equals(m.getName()) && Arrays.deepEquals(m.getParameterTypes(), parameters));
      assertTrue(foundWithoutOptions, "No match found for:" + withOptions);
    }
    for (Method withoutOptions : methodsWithoutOptions)
    {
      String name = withoutOptions.getName();
      Class[] parameters = withoutOptions.getParameterTypes();
      boolean foundWithOptions = Query.any(methodsWithOptions, m -> name.equals(m.getName())
          && Arrays.deepEquals(getWithoutOptions(m.getParameterTypes()), parameters));
      assertTrue(foundWithOptions, "No match found for:\n" + withoutOptions);
    }
    assertEquals(methodsWithOptions.size(), methodsWithoutOptions.size());
    assertNotEquals(0, methodsWithoutOptions.size());
  }
  private Class<?>[] getWithoutOptions(Class<?>[] parameterTypes)
  {
    Class<?>[] parameters = Query.where(parameterTypes, t -> !t.equals(Options.class)).toArray(new Class[0]);
    return parameters;
  }
  private boolean isOptionsPresent(Method m)
  {
    return ArrayUtils.getLast(m.getParameterTypes()).equals(Options.class)
        || ArrayUtils.getFirst(m.getParameterTypes()).equals(Options.class);
  }
  @Test
  void verifyFileExtension()
  {
    Approvals.verify("<html><body><h1>hello approvals</h1></body></html>",
        new Options().forFile().withExtension(".html"));
  }
  @Test
  void verifyFileName()
  {
    String sampleText = "<html><body><h1>hello approvals</h1></body></html>";
    Approvals.verify(sampleText,
        new Options().forFile().withBaseName("customApproval").forFile().withExtension(".html"));
    Approvals.verify(sampleText, new Options().forFile().withName("customApproval", ".html"));
    Approvals.verify(sampleText, new Options().forFile().withBaseName("customApproval"));
  }
  @Disabled("todo")
  @Test
  void verifyFilePath()
  {
    Approvals.verify("<html><body><h1>hello approvals</h1></body></html>",
        new Options().forFile().withExtension(".html"));
  }
  @Disabled("todo")
  @Test
  void verifyFileRelativePath()
  {
    Approvals.verify("<html><body><h1>hello approvals</h1></body></html>",
        new Options().forFile().withExtension(".html"));
  }
  static class ApprovalFailureReporterSpy implements ApprovalFailureReporter
  {
    private boolean hasBeenCalled;
    public ApprovalFailureReporterSpy()
    {
      this.hasBeenCalled = false;
    }
    @Override
    public void report(String received, String approved)
    {
      hasBeenCalled = true;
    }
    public boolean hasBeenCalled()
    {
      return hasBeenCalled;
    }
  }
}
