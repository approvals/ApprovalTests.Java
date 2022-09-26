package org.approvaltests;

public class VerifiableStoryBoard extends StoryBoard implements AutoCloseable
{
  @Override
  public void close()
  {
    Approvals.verify(this);
  }
}
