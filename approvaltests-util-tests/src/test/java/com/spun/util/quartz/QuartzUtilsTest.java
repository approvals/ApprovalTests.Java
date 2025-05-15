package com.spun.util.quartz;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuartzUtilsTest
{
  @Test
  void testCreateTrigger()
  {
    // Arrange
    SimpleTriggerImpl oldTrigger = new SimpleTriggerImpl();
    oldTrigger.setName("oldName");
    oldTrigger.setGroup("oldGroup");
    // A JobKey is required for getTriggerBuilder() and for the trigger to be valid for scheduling with a scheduler
    oldTrigger.setJobKey(new JobKey("jobName", "jobGroup"));
    String newName = "testTriggerName";
    String newGroup = "testTriggerGroup";
    long beforeTime = System.currentTimeMillis();
    // Act
    Trigger newTrigger = QuartzUtils.createTrigger(oldTrigger, newName, newGroup);
    long afterTime = System.currentTimeMillis();
    // Assert
    assertNotNull(newTrigger, "New trigger should not be null");
    assertEquals(newName, newTrigger.getKey().getName(), "Trigger name should be updated");
    assertEquals(newGroup, newTrigger.getKey().getGroup(), "Trigger group should be updated");
    assertNotNull(newTrigger.getStartTime(), "Start time should be set");
    // Check if start time is within a small delta (e.g., 100ms) of the call time to account for execution time
    assertTrue(
        newTrigger.getStartTime().getTime() >= beforeTime
            && newTrigger.getStartTime().getTime() <= afterTime + 100,
        "Start time should be approximately now. Expected between " + beforeTime + " and " + (afterTime + 100)
            + " but was " + newTrigger.getStartTime().getTime());
    assertNotNull(newTrigger.getEndTime(), "End time should be set");
    assertEquals(new Date(Long.MAX_VALUE).getTime(), newTrigger.getEndTime().getTime(),
        "End time should be Long.MAX_VALUE");
    // Verify that the JobKey is carried over
    assertEquals(oldTrigger.getJobKey(), newTrigger.getJobKey(), "JobKey should be carried over from old trigger");
    // Use ApprovalTests to verify the trigger's key aspects
    Approvals.verify(String.format("Trigger Key: %s\nJob Key: %s\nStart Time: %s\nEnd Time: %s",
        newTrigger.getKey(), newTrigger.getJobKey(), newTrigger.getStartTime(), newTrigger.getEndTime()));
  }
}
