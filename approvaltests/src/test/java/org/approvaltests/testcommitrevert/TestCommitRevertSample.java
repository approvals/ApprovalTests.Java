package org.approvaltests.testcommitrevert;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

// begin-snippet: test_commit_revert_runner 
@RunWith(TestCommitRevertRunner.class)
public class TestCommitRevertSample
    // end-snippet
    // begin-snippet: test_commit_revert 
    extends
      TestCommitRevertTest
// end-snippet
{
  @Test
  public void testOnSuccessThisPromptsForCommitMessage()
  {
    GitCommitOrRevert.printOnly();
    assertEquals(1, 1);
  }
}
