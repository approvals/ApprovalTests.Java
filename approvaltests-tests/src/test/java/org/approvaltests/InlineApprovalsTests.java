package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.DiffMergeReporter;
import org.junit.jupiter.api.Test;

public class InlineApprovalsTests
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
