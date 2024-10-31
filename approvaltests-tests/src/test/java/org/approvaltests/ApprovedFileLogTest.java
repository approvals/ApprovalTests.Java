package org.approvaltests;

import com.spun.util.io.FileUtils;
import org.approvaltests.namer.ApprovalNamer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ApprovedFileLogTest {
    @Test
    void testLogging() {
        File file = ApprovedFileLog.get();
        ApprovalNamer approvalNamer = Approvals.createApprovalNamer();
        File approvedFile = approvalNamer.getApprovedFile(".txt");
        String prelog = FileUtils.readFile(file);
        Assert.assertFalse(prelog.contains(approvedFile.getAbsolutePath()));
        Approvals.verify("anything");
        String postlog = FileUtils.readFile(file);
        Assert.assertTrue(postlog.contains(approvedFile.getAbsolutePath()));
    }
}
