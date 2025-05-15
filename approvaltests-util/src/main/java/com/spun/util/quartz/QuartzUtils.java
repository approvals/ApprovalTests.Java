package com.spun.util.quartz;

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
}
