package org.approvaltests.namer;

/**
 * @deprecated Use {@code NamerUtils.getSourcePath(namer)} or {@code namer.getApprovedFile(ext).getParentFile()}.
 *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/Upgrade.V31.2.0.to.V31.3.0.md">Upgrade from 31.2.0 to 31.3.0</a>
 */
@Deprecated
public interface GetSourceFilePath
{
  String getSourceFilePath();
}
