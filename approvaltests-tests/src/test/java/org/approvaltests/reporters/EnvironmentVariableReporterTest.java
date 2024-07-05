package org.approvaltests.reporters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvironmentVariableReporterTest {
    @Test
    public void testEnvironmentVariable() {
        var reporter = EnvironmentVariableReporter.INSTANCE.getReporter();

        Assertions.assertInstanceOf(MultiReporter.class, reporter);
        var multiReporter = (MultiReporter) reporter;
        var reporters = multiReporter.getReporters();

        Assertions.assertEquals(2, reporters.length);
        Assertions.assertInstanceOf(DiffReporter.class, reporters[0]);
        Assertions.assertInstanceOf(QuietReporter.class, reporters[1]);
    }
}
