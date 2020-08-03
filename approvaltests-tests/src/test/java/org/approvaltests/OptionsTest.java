package org.approvaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
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
    Queryable<Method> declaredMethods = Queryable.as(Approvals.class.getDeclaredMethods());
    Queryable<Method> methodList = declaredMethods
        .where(m -> m.getName().startsWith("verify") && Modifier.isPublic(m.getModifiers()) && m.isAccessible());
    List<Method> methodsWithOptions = methodList
        .where(m -> ArrayUtils.getLast(m.getParameterTypes()).equals(Options.class));
    List<Method> methodsWithoutOptions = methodList
        .where(m -> !ArrayUtils.getLast(m.getParameterTypes()).equals(Options.class));
    for (Method withOptions : methodsWithOptions)
    {
      String name = withOptions.getName();
      Class<?>[] parameterTypes = withOptions.getParameterTypes();
      Class[] parameters = ArrayUtils.getSubsection(parameterTypes, 0, parameterTypes.length - 1);
      assertTrue(
          Query.any(methodsWithoutOptions,
              m -> name.equals(m.getName()) && Arrays.deepEquals(m.getParameterTypes(), parameters)),
          "No match found for:" + withOptions);
    }
    for (Method withoutOptions : methodsWithoutOptions)
    {
      String name = withoutOptions.getName();
      Class[] parameters = ArrayUtils.addToArray(withoutOptions.getParameterTypes(), Options.class);
      assertTrue(
          Query.any(methodsWithOptions,
              m -> name.equals(m.getName()) && Arrays.deepEquals(m.getParameterTypes(), parameters)),
          "No match found for:" + withoutOptions);
    }
    assertEquals(methodsWithOptions.size(), methodsWithoutOptions.size());
  }
  @Test
  void verifyFileExtension()
  {
    Approvals.verify("<html><body><h1>hello approvals</h1></body></html>", new Options().forFile().withExtension(".html"));
  }
  @Test
  void verifyFileName()
  {
    String sampleText = "<html><body><h1>hello approvals</h1></body></html>";
    Approvals.verify(sampleText, new Options().forFile().withBaseName("customApproval").forFile().withExtension(".html"));
    //Approvals.verify(sampleText, new Options().forFile().withName("customApproval", ".html"));
    //Approvals.verify(sampleText, new Options().forFile().withBaseName("customApproval"));
  }
  @Disabled("todo")
  @Test
  void verifyFilePath()
  {
    Approvals.verify("<html><body><h1>hello approvals</h1></body></html>", new Options().forFile().withExtension(".html"));
  }
  @Disabled("todo")
  @Test
  void verifyFileRelativePath()
  {
    Approvals.verify("<html><body><h1>hello approvals</h1></body></html>", new Options().forFile().withExtension(".html"));
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
