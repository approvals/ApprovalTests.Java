package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class WhiteSpaceStripperTest
{
  @Test
  public void test()
  {
    String[] useCases = {"  hello \n    \n   \n", "  hello \r\n    \n a \n", "  hello  "};
    Approvals.verifyAll("whitespace", useCases,
        u -> String.format("---\n%s\n--- ->---\n%s\n---\n", u, WhiteSpaceStripper.stripBlankLines(u)));
  }
}
