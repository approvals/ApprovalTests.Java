package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;

class NamerFactoryForOptionsTest {

    @EnabledOnOs(OS.MAC)
    @Test
    void testOsSpecificTest() {
        Options options = Approvals.NAMES.asOsSpecificTest();
        ApprovalNamer namer = options.forFile().getNamer();
        String name = namer.getApprovedFile(".txt").getName();
        assertTrue(name.contains(".Mac_OS_X."), "name was " + name);
    }
}
