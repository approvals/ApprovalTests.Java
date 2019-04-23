package org.approvaltests.legacycode.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.legacycode.TestCommitRevert;
import org.junit.Test;

public class TestCommitRevertSample extends TestCommitRevert
{
  @Test
  public void testOnSuccessThisPromptsForCommitMessage()
  {
    assertEquals(1, 1);
  }
}
