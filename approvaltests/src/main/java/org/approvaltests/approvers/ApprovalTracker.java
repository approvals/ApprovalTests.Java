package org.approvaltests.approvers;

import org.approvaltests.ApprovalsDuplicateVerifyException;

import java.util.LinkedHashSet;
import java.util.Set;

public class ApprovalTracker
{
  private final Set<String> tracked = new LinkedHashSet<>();
  public void assertUnique(String approved)
  {
    if (tracked.contains(approved))
    { throw new ApprovalsDuplicateVerifyException(approved); }
    tracked.add(approved);
  }
}
