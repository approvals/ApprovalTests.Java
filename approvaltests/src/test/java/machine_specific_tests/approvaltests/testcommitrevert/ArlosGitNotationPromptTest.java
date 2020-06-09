package machine_specific_tests.approvaltests.testcommitrevert;

import machine_specific_tests.MachineSpecificTest;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.testcommitrevert.ArlosGitNotationPrompt;
import org.junit.Test;

//@UseReporter(ClipboardReporter.class)
public class ArlosGitNotationPromptTest extends MachineSpecificTest
{
  @Test
  public void test()
  {
    AwtApprovals.verify(new ArlosGitNotationPrompt().getPanel());
  }
}
