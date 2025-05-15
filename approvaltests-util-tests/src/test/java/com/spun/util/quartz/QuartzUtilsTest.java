package com.spun.util.quartz;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;

class QuartzUtilsTest {
    @Test
    void testCreateTrigger() {
        // Arrange
        SimpleTriggerImpl oldTrigger = new SimpleTriggerImpl();
        oldTrigger.setName("oldName");
        oldTrigger.setGroup("oldGroup");
        oldTrigger.setJobKey(new JobKey("jobName", "jobGroup"));
        
        // Act
        Trigger newTrigger = QuartzUtils.createTrigger(oldTrigger, "testTriggerName", "testTriggerGroup");
        
        // Verify
        String output = String.format(
            "Trigger:\n" +
            "  Key: %s\n" +
            "  Job Key: %s\n" +
            "  Start Time: %s\n" +
            "  End Time: %s",
            newTrigger.getKey(),
            newTrigger.getJobKey(),
            newTrigger.getStartTime(),
            newTrigger.getEndTime()
        );
        
        Approvals.verify(output);
    }
}
