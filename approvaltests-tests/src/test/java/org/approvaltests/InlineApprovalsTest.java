package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

public class InlineApprovalsTest
{
  @Test
  public void test()
  {
    var expected = """
        Hello World***
        """;
    Approvals.verify("Hello World***", Options.inline(expected));
  }
}
