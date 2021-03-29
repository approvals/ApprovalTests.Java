package org.approvaltests.reporters;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

class ClipboardReporterTest
{
  @Test
  void commandLineMoves()
  {
    String[] files = {"with spaces.txt", "withoutSpaces.txt"};
    Boolean[] systems = {true, false};
    CombinationApprovals.verifyAllCombinations(ClipboardReporter::getAcceptApprovalText, files, files, systems);
  }
}
