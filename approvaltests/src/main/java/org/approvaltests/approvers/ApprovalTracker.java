package org.approvaltests.approvers;

import org.approvaltests.ApprovalsDuplicateVerifyException;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.util.LinkedHashSet;
import java.util.Set;

public class ApprovalTracker
{
  private final Set<String>                           tracked           = new LinkedHashSet<>();
  private final Queryable<Function1<String, Boolean>> allowedDuplicates = new Queryable<>();
  public void assertUnique(String approved)
  {
    if (tracked.contains(approved) && !allowedDuplicates.any(f -> f.call(approved)))
    { throw new ApprovalsDuplicateVerifyException(approved); }
    tracked.add(approved);
  }

  public void addAllowedDuplicates(Function1<String, Boolean> duplicateChecker)
  {
    allowedDuplicates.add(duplicateChecker);
  }
}
