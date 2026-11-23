package org.approvaltests.scrubbers;

import org.approvaltests.core.Scrubber;

import java.util.ArrayList;
import java.util.List;

public class Scrubbers
{
  public static String scrubGuid(String input)
  {
    return new GuidScrubber().scrub(input);
  }

  public static Scrubber scrubAll(Scrubber one, Scrubber... others)
  {
    return new MultiScrubber(one, others);
  }

  public static Scrubber removeLinesContaining(String text)
  {
    return input -> {
      List<String> kept = new ArrayList<>();
      for (String line : input.split("\\r?\\n", -1))
      {
        if (!line.contains(text))
        {
          kept.add(line);
        }
      }
      return String.join("\n", kept);
    };
  }
  /**
   * Provides methods that can be inlined as example code.
   */
  public static class Templates
  {
    /**
     * To use this template, inline the method.
     */
    public static DateScrubber DateScrubber()
    {
      return DateScrubber.getScrubberFor("your date here: 2020-09-9T08:07Z");
    }

    public static GuidScrubber GuidScrubber()
    {
      return new GuidScrubber();
    }

    public static RegExScrubber RegExScrubber()
    {
      return new RegExScrubber("your pattern here: [a-zA-Z]+/d{4}", "<your replacement>");
    }
  }
}
