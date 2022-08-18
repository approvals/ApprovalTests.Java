package org.approvaltests.namer;

import org.approvaltests.core.Options;

public class FileCounter
{
  int counter;
  public Options next()
  {
    return new Options().forFile().withAdditionalInformation("" + ++counter);
  }
}
