package com.spun.util.quartz;

import java.util.Date;
import org.quartz.Trigger;

public class QuartzUtils
{
  public static Trigger createTrigger(Trigger trigger, String name, String defaultGroup)
  {
    trigger.setName(name);
    trigger.setGroup(defaultGroup);
    trigger.setStartTime(new Date());
    trigger.setEndTime(new Date(Long.MAX_VALUE));
    return trigger;
  }
}
