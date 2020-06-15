package org.approvaltests;

import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.junit.jupiter.api.Test;

@UseReporter(TortoiseTextDiffReporter.class)
public class WeatherTest
{
  @Test
  public void testWeather()
  {
    // TODO:
    Approvals.verify(new WeatherLoader("KCASANDI56"));
  }
}
