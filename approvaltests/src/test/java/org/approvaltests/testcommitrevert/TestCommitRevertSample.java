package org.approvaltests.testcommitrevert;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

// startcode test_commit_revert_runner 
@RunWith(TestCommitRevertRunner.class)
public class TestCommitRevertSample
    // endcode
    // startcode test_commit_revert 
    extends TestCommitRevertTest
// endcode
{
  @Test
  public void testOnSuccessThisPromptsForCommitMessage()
  {
    assertEquals(1, 1);
  }
}
