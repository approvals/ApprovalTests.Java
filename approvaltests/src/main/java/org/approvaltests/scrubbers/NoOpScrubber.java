package org.approvaltests.scrubbers;

import org.approvaltests.core.Scrubber;

public class NoOpScrubber implements Scrubber
{
  public static final Scrubber INSTANCE = new NoOpScrubber();
  @Override
  public String scrub(String input)
  {
    return input;
  }
}
