package org.approvaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.spun.util.ArrayUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.UseReporterTest;
import org.approvaltests.reporters.macosx.BeyondCompareMacReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

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
    Method[] declaredMethods = Approvals.class.getDeclaredMethods();
    List<Method> methodList = Query.where(declaredMethods, m -> m.getName().startsWith("verify") && m.getModifiers() == Modifier.PUBLIC);
    List<Method> methodsWithOptions = Query.where(methodList, m -> ArrayUtils.getLast(m.getParameterTypes()).equals(Options.class));
    List<Method> methodsWithoutOptions = Query.where(methodList, m -> !ArrayUtils.getLast(m.getParameterTypes()).equals(Options.class));
    for (Method m : methodsWithOptions) {
      String name = m.getName();
      Class<?>[] parameterTypes = m.getParameterTypes();
      new ArrayList<Class>()
      Query.first(methodsWithoutOptions, m -> m.getName().equals(m.getName() && m.getParameterTypes().length == m.getParameterTypes().length - 1));
    }
    assertEquals(methodsWithOptions.size(), methodsWithoutOptions.size());
    //Approvals.verifyAll("verifyMethods", methodList, m -> m.toString(), new Options(new BeyondCompareMacReporter()));
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
