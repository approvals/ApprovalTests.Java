package org.approvaltests.scrubbers;

import org.approvaltests.core.Scrubber;

public class NormalizeSpacesScrubber implements Scrubber
{
  @Override
  public String scrub(String input)
  {
    char narrowSpace = '\u202F';
    return input.replace(narrowSpace, ' ');
  }
}
