package com.spun.util.quartz;

import com.spun.util.DateUtils;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

public class QuartzUtils
{
  public static Trigger createTrigger(Trigger oldTrigger, String name, String defaultGroup)
  {
    TriggerBuilder<? extends Trigger> builder = oldTrigger.getTriggerBuilder();
    Trigger newTrigger = builder.withIdentity(name, defaultGroup) //
        .startAt(new Date()) // Start immediately
        .endAt(new Date(Long.MAX_VALUE)) // Effectively never ends
        .build();
    return newTrigger;
  }

  public static String toString(Trigger trigger)
  {
    return String.format("Trigger:\n  Key: %s\n  Job Key: %s\n  Start Time: %s\n  End Time: %s", trigger.getKey(),
        trigger.getJobKey(), DateUtils.toString(trigger.getStartTime()), DateUtils.toString(trigger.getEndTime()));
  }
}
