package org.approvaltests.machine_specific_tests.testcommitrevert;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.machine_specific_tests.MachineSpecificTest;
import org.approvaltests.testcommitrevert.ArlosGitNotationPrompt;
import org.junit.jupiter.api.Test;

public class ArlosGitNotationPromptTest extends MachineSpecificTest
{
  @Test
  public void test()
  {
    AwtApprovals.verify(new ArlosGitNotationPrompt().getPanel());
  }
}
