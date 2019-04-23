package org.approvaltests.testcommitrevert;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestCommitRevertRunner.class)
public class TestCommitRevertSample
//startcode test_commit_revert 
// extends TestCommitRevert
// endcode
{
  @Test
  public void testOnSuccessThisPromptsForCommitMessage()
  {
    assertEquals(1, 1);
  }
}
