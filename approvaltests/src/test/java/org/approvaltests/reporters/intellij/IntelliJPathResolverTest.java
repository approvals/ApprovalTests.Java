package org.approvaltests.reporters.intellij;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class IntelliJPathResolverTest {
    @Test
    public void findUltimate() {
        final IntelliJPathResolver intelliJPathResolver = new IntelliJPathResolver(Edition.Ultimate);
        final String path = intelliJPathResolver.findIt();
        assertThat(path, anyOf(
            equalTo("C:\\Intelli-not-present.exe"),
            allOf(
                containsString("IDEA-U"),
                containsString("ch-0"),
                containsString("idea")
            )
        ));
    }

    @Test
    public void findCommunity() {
        final IntelliJPathResolver intelliJPathResolver = new IntelliJPathResolver(Edition.Community);
        final String path = intelliJPathResolver.findIt();
        assertThat(path, anyOf(
            equalTo("C:\\Intelli-not-present.exe"),
            allOf(
                containsString("IDEA-C"),
                containsString("ch-0"),
                containsString("idea")
            )
        ));
    }
}