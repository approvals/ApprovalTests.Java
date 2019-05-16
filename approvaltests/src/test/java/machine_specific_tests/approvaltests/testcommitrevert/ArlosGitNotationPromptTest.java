package machine_specific_tests.approvaltests.testcommitrevert;

import org.approvaltests.Approvals;
import org.approvaltests.testcommitrevert.ArlosGitNotationPrompt;
import org.junit.Test;

//@UseReporter(ClipboardReporter.class)
public class ArlosGitNotationPromptTest
{
  @Test
  public void test()
  {
    Approvals.verify(new ArlosGitNotationPrompt().getPanel());
  }
}
