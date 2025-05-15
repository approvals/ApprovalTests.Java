package com.spun.util.quartz;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

public class QuartzUtils
{
  public static Trigger createTrigger(Trigger oldTrigger, String name, String defaultGroup)
  {
    // Get a TriggerBuilder that is configured to produce a Trigger
    // identical to the oldTrigger one in terms of job, schedule type etc.
    TriggerBuilder<? extends Trigger> builder = oldTrigger.getTriggerBuilder();
    // Now, set the new identity (name and group) and start/end times
    Trigger newTrigger = builder.withIdentity(name, defaultGroup).startAt(new Date()) // Start immediately
        .endAt(new Date(Long.MAX_VALUE)) // Effectively never ends
        .build();
    return newTrigger;
  }
}
