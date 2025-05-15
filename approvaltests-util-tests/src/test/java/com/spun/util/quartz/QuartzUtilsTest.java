package com.spun.util.quartz;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.DateScrubber;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;

class QuartzUtilsTest
{
  @Test
  void testCreateTriggerFromTrigger()
  {
    var expected = """
        Trigger:
          Key: newGroup.newName
          Job Key: jobGroup.jobName
          Start Time: [Date1]
          End Time: <EndOfTime>
        """;
    SimpleTriggerImpl oldTrigger = new SimpleTriggerImpl();
    oldTrigger.setName("oldName");
    oldTrigger.setGroup("oldGroup");
    oldTrigger.setJobKey(new JobKey("jobName", "jobGroup"));
    Trigger newTrigger = QuartzUtils.createTrigger(oldTrigger, "newName", "newGroup");
    DateScrubber scrubber = DateScrubber.getScrubberFor("2025-05-15 16:57:04.599");
    Approvals.verify(QuartzUtils.toString(newTrigger), new Options().inline(expected).withScrubber(scrubber));
  }
}