package org.approvaltests.reporters.intellij;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntelliJPathResolverTest {

    @Test
    @Ignore
    void testFindsPath() {
        IntelliJPathResolver intelliJPathResolver = new IntelliJPathResolver(Edition.Community);
        String result = intelliJPathResolver.findIt();
        assertEquals("C:\\Users\\Administrator\\AppData\\Local\\JetBrains\\Toolbox\\apps\\IDEA-C\\ch-0\\211.6693.111\\bin\\idea64.exe", result);
    }
}