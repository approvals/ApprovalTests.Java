package org.approvaltests;

import com.spun.util.ArrayUtils;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.Junit4Reporter;
import org.approvaltests.reporters.Junit5Reporter;
import org.approvaltests.reporters.MultiReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.UseReporterTest;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    for (Class c : getApprovalClasses())
    {
      verifyEachVerifyMethodHasOneWithOptions(c, "verify");
    }
  }
  public static List<Class<?>> getApprovalClasses()
  {
    return Arrays.asList(Approvals.class, CombinationApprovals.class, AwtApprovals.class, JsonApprovals.class,
        VelocityApprovals.class, JsonJacksonApprovals.class, JsonXstreamApprovals.class);
    // TODO: missing verification that JsonApprovals, JsonJacksonApprovals and JsonXStreamApprovals offer the same verify methods
  }
  public static void verifyEachVerifyMethodHasOneWithOptions(Class<?> approvalsClass, String methodPrefix)
  {
    Queryable<Method> declaredMethods = Queryable.as(approvalsClass.getDeclaredMethods());
    Queryable<Method> methodList = declaredMethods.where(m -> m.getName().startsWith(methodPrefix)
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
  private static Class<?>[] getWithoutOptions(Class<?>[] parameterTypes)
  {
    Class<?>[] parameters = Query.where(parameterTypes, t -> !t.equals(Options.class)).toArray(new Class[0]);
    return parameters;
  }
  public static boolean isOptionsPresent(Method m)
  {
    Class<?>[] parameterTypes = m.getParameterTypes();
    return 0 < parameterTypes.length && (ArrayUtils.getLast(parameterTypes).equals(Options.class)
        || ArrayUtils.getFirst(parameterTypes).equals(Options.class));
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
    String customFileName = "customApproval";
    FileApprover.tracker.addAllowedDuplicates(f -> f.contains(customFileName));
    String sampleText = "<html><body><h1>hello approvals</h1></body></html>";
    Approvals.verify(sampleText,
        new Options().forFile().withBaseName(customFileName).forFile().withExtension(".html"));
    Approvals.verify(sampleText, new Options().forFile().withName(customFileName, ".html"));
    Approvals.verify(sampleText, new Options().forFile().withBaseName(customFileName));
  }
  static class ApprovalFailureReporterSpy implements ApprovalFailureReporter
  {
    private boolean hasBeenCalled;
    public ApprovalFailureReporterSpy()
    {
      this.hasBeenCalled = false;
    }
    @Override
    public boolean report(String received, String approved)
    {
      hasBeenCalled = true;
      return true;
    }
    public boolean hasBeenCalled()
    {
      return hasBeenCalled;
    }
  }
  @Test
  void testDefaultWriterForOptions()
  {
    final Options options = new Options();
    ApprovalWriter writer = options.createWriter("any text");
    Approvals.verify(writer);
  }
  @Test
  void testCustomWriter()
  {
    final Options options = new Options();
    ApprovalWriter anotherWriter = options.withWriter(MyWriter::getFactory).createWriter("any text");
    assertTrue(anotherWriter instanceof MyWriter);
  }
  public static class MyWriter implements ApprovalWriter
  {
    public static ApprovalWriter getFactory(Object content, Options options)
    {
      return new MyWriter();
    }
    @Override
    public File writeReceivedFile(File received)
    {
      return null;
    }
    @Override
    public String getFileExtensionWithDot()
    {
      return null;
    }
  }
  @Test
  void verifyCustomComparator()
  {
    Approvals.verify("The approval file is empty", new Options().withComparator((a, b) -> VerifyResult.SUCCESS));
  }
  @Test
  void testAddReporter()
  {
    Options options = new Options().withReporter(new Junit5Reporter()).withReporter(new Junit4Reporter());
    ApprovalFailureReporter reporter = options.getReporter();
    assertTrue(reporter instanceof Junit4Reporter);
    Options options2 = new Options().withReporter(new MultiReporter(new Junit5Reporter(), new Junit4Reporter()));
    ApprovalFailureReporter reporter2 = options2.getReporter();
    assertEquals(reporter2.toString(), "Junit5Reporter, Junit4Reporter");
    Options options3 = new Options().withReporter(new Junit5Reporter()).addReporter(new Junit4Reporter());
    ApprovalFailureReporter reporter3 = options3.getReporter();
    assertEquals(reporter3.toString(), "Junit5Reporter, Junit4Reporter");
  }
}
