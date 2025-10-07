package org.approvaltests;

import org.approvaltests.approvers.ApprovalApprover;
import org.approvaltests.core.Options;
import org.lambda.actions.Action2;

public class SafetyCheckBeforeVerify
{
  public static final ThreadLocal<Action2<ApprovalApprover, Options>> guardRailCheck = ThreadLocal
      .withInitial(Action2::doNothing);
  public static void run(ApprovalApprover approver, Options options)
  {
    Action2<ApprovalApprover, Options> check = guardRailCheck.get();
    guardRailCheck.remove();
    check.call(approver, options);
  }

  public static void add(Action2<ApprovalApprover, Options> newCheck)
  {
    Action2<ApprovalApprover, Options> oldCheck = guardRailCheck.get();
    guardRailCheck.set(newCheck);
  }

  public static void clear()
  {
    guardRailCheck.remove();
  }
}
