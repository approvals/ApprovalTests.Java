package com.spun.util.persistence;

import com.spun.util.DateUtils;
import com.spun.util.persistence.test.MockLoader;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.DiffMergeReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeMacOsReporter;
import org.approvaltests.scrubbers.NormalizeSpacesScrubber;
import org.approvaltests.utils.WithTimeZone;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

class LoaderTest
{
  @Test
  @UseReporter(DiffMergeReporter.class)
  // begin-snippet: testing_rendered_data
  void testWithMockedData()
  {
    try (WithTimeZone tz = new WithTimeZone("PST"))
    {
      Calendar day = DateUtils.asCalendar(DateUtils.parse("2020/01/02"));
      // Mocked data
      MockLoader<List<Shift>> shifts = new MockLoader<>(
          Arrays.asList(new Shift("Scott", "Chef", DateUtils.setTime(day, 8, 0)),
              new Shift("Llewellyn", "Dishwasher", DateUtils.setTime(day, 9, 30))));
      verifyMarkdown(KitchenScheduler.print(shifts, day));
    }
  }

  // end-snippet
  private void verifyMarkdown(String print)
  {
    Approvals.verify(print,
        new Options().forFile().withExtension(".md").withScrubber(new NormalizeSpacesScrubber()));
  }

  // begin-snippet: testing_executable_command
  @Test
  void testWithDatabaseAccess()
  {
    try (WithTimeZone withTimeZone = new WithTimeZone())
    {
      Calendar day = DateUtils.asCalendar(DateUtils.parse("2020/01/02"));
      Approvals.verify(new LoadShiftsFromDatabase(day));
    }
  }
  // end-snippet
}
