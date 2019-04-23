package org.approvaltests.legacycode.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.legacycode.TestCommitRevert;
import org.junit.Test;

//startcode test_commit_revert 
public class TestCommitRevertSample extends TestCommitRevert
// endcode
{
  @Test
  public void testOnSuccessThisPromptsForCommitMessage()
  {
    assertEquals(1, 1);
  }
}
