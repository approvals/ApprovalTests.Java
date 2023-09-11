package org.approvaltests;

import com.spun.util.introspection.Caller;
import org.approvaltests.approvers.FileApprover;
import org.lambda.query.Queryable;

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
}
