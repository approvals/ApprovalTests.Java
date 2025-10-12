package org.approvaltests;

import org.approvaltests.core.Options;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class InlineApprovalsExampleTest
{
  public static class Step1
  {
    @Disabled
    // begin-snippet: inline_approvals_before
    @Test
    public void testInlineApprovals()
    {
      var expected = """
          """;
      Approvals.verify(getGreeting(), new Options().inline(expected));
    }

    // end-snippet
    private String getGreeting()
    {
      return "Hello world!\nWelcome to inline approvals!";
    }
  }
  // begin-snippet: inline_approvals_after
  @Test
  public void testInlineApprovals()
  {
    var expected = """
        Hello world!
        Welcome to inline approvals!
        """;
    Approvals.verify(getGreeting(), new Options().inline(expected));
  }

  // end-snippet
  private String getGreeting()
  {
    return "Hello world!\nWelcome to inline approvals!";
  }
}
