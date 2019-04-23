package org.approvaltests.legacycode.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.legacycode.TestCommitRevertRunner;
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
