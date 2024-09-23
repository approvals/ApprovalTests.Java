package org.approvaltests.scrubbers;

import com.spun.util.ArrayUtils;
import org.approvaltests.core.Scrubber;

import java.util.Collection;

public class MultiScrubber implements Scrubber
{
  private final Scrubber[] scrubbers;
  public MultiScrubber(Scrubber one, Scrubber[] others)
  {
    this.scrubbers = ArrayUtils.combine(one, others);
  }
  public MultiScrubber(Collection<Scrubber> scrubbers)
  {
    this.scrubbers = scrubbers.toArray(new Scrubber[0]);
  }
  @Override
  public String scrub(String input)
  {
    for (Scrubber scrubber : scrubbers)
    {
      input = scrubber.scrub(input);
    }
    return input;
  }
}
