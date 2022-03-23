package org.approvaltests.reporters;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

class ClipboardReporterTest
{
  @EnabledOnOs({MAC, LINUX})
  @Test
  void commandLineMoves()
  {
    String[] files = {"with spaces.txt", "withoutSpaces.txt"};
    Boolean[] systems = {true, false};
    CombinationApprovals.verifyAllCombinations(ClipboardReporter::getAcceptApprovalText, files, files, systems);
  }
  @Disabled("TODO: find a windows user for this test")
  @EnabledOnOs(WINDOWS)
  @Test
  void commandLineMovesWindows()
  {
    String[] files = {"with spaces.txt", "withoutSpaces.txt"};
    Boolean[] systems = {true, false};
    CombinationApprovals.verifyAllCombinations(ClipboardReporter::getAcceptApprovalText, files, files, systems);
  }
}
