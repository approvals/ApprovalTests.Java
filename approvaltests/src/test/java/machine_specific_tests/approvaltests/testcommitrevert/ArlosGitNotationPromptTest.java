package machine_specific_tests.approvaltests.testcommitrevert;

import machine_specific_tests.MachineSpecificTest;
import org.approvaltests.Approvals;
import org.approvaltests.testcommitrevert.ArlosGitNotationPrompt;
import org.junit.Test;

//@UseReporter(ClipboardReporter.class)
public class ArlosGitNotationPromptTest extends MachineSpecificTest
{
  @Test
  public void test()
  {
    Approvals.verify(new ArlosGitNotationPrompt().getPanel());
  }
}
