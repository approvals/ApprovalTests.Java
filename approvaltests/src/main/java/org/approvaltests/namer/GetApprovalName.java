package org.approvaltests.namer;

/**
 * @deprecated Use {@code NamerUtils.getApprovalName(namer)} or {@code namer.getApprovedFile(ext)}.
 *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/Upgrade.V31.2.0.to.V31.3.0.md">Upgrade from 31.2.0 to 31.3.0</a>
 */
@Deprecated
public interface GetApprovalName
{
  String getApprovalName();
}
