package org.approvaltests.scrubbers;

public class Scrubbers
{
  public static String scrubGuid(String input)
  {
    return new GuidScrubber().scrub(input);
  }
}
