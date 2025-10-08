package org.approvaltests;

import com.spun.util.QuietAutoCloseable;
import com.spun.util.introspection.Caller;
import org.approvaltests.approvers.FileApprover;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

import java.io.File;

public class ApprovalSettings
{
  public void allowMultipleVerifyCallsForThisMethod()
  {
    StackTraceElement caller = Caller.get(1);
    String className = Queryable.as(caller.getClassName().split("\\.")).last();
    String methodName = caller.getMethodName();
    String name = className + "." + methodName;
    FileApprover.tracker.addAllowedDuplicates(f -> f.contains(name));
  }

  public void allowMultipleVerifyCallsForThisClass()
  {
    StackTraceElement caller = Caller.get(1);
    String className = caller.getClassName().replace('.', File.separatorChar);
    FileApprover.tracker.addAllowedDuplicates(f -> f.contains(className));
  }

  public static QuietAutoCloseable registerErrorGenerator(Function2<String, String, Error> errorGenerator)
  {
    return FileApprover.registerErrorGenerator(errorGenerator);
  }
}
