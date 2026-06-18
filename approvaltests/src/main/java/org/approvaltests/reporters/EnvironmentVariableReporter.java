package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public class EnvironmentVariableReporter implements ApprovalFailureReporter
{
  public static final String                      ENVIRONMENT_VARIABLE_NAME = "APPROVAL_TESTS_USE_REPORTER";
  public static Function1<String, String>         GET_ENVIRONMENT_VARIABLE  = System::getenv;
  public static final EnvironmentVariableReporter INSTANCE                  = new EnvironmentVariableReporter();
  private ApprovalFailureReporter                 reporter                  = null;
  public EnvironmentVariableReporter()
  {
    String environmentValue = GET_ENVIRONMENT_VARIABLE.call(ENVIRONMENT_VARIABLE_NAME);
    if (environmentValue == null || environmentValue.isBlank())
    { return; }
    Queryable<String> split = Queryable.of(environmentValue.split(","));
    Queryable<ApprovalFailureReporter> reporters = split.distinct()
        .select(EnvironmentVariableReporter::loadReporter);
    reporter = reporters.size() == 1 ? reporters.first() : new MultiReporter(reporters);
  }

  private static ApprovalFailureReporter loadReporter(String className)
  {
    try
    {
      Class<?> clazz = Class.forName(className.trim());
      return ClassUtils.create(clazz.asSubclass(ApprovalFailureReporter.class));
    }
    catch (ClassNotFoundException e)
    {
      throw new RuntimeException("Unknown reporter: " + className, e);
    }
  }

  public ApprovalFailureReporter getReporter()
  {
    return reporter;
  }

  @Override
  public boolean report(String received, String approved)
  {
    if (reporter == null)
    { return false; }
    return reporter.report(received, approved);
  }
}
