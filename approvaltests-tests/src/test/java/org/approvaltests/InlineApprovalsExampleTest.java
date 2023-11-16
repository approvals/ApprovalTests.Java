package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.inline.InlineComparator;
import org.approvaltests.reporters.DiffMergeReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InlineApprovalsExampleTest
{
    public static class Step1 {
        @Ignore
        // begin-snippet: inline_approvals_before
        @Test
        public void testInlineApprovals()
        {
            var expected = """
		    """;
            Approvals.verify(getGreeting(), new Options().inline(expected));
        }
        // end-snippet
        private String getGreeting() {
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

  private String getGreeting() {
    return "Hello world!\nWelcome to inline approvals!";
  }
}
