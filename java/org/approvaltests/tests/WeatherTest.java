package org.approvaltests.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;

@UseReporter(TortoiseTextDiffReporter.class)
public class WeatherTest extends TestCase
{
  public void testWeather() throws Exception
  {
    Approvals.verify(new WeatherLoader("KCASANDI56"));
  }
}
