package org.approvaltests.reporters;

public class QuietReporter implements EnvironmentAwareReporter
{
  public static final QuietReporter INSTANCE = new QuietReporter();
  @Override
  public void report(String received, String approved)
  {
    System.out.println(ClipboardReporter.getAcceptApprovalText(received, approved));
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return true;
  }
}
