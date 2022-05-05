package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.scrubbers.GuidScrubber;
import org.junit.jupiter.api.Test;

public class OptionsSamplesTest {
    @Test
    void showAll() {
        Options options =
        // begin-snippet: specify_all_the_options 
                new Options()
                        .withReporter(new QuietReporter())
                        .withScrubber(new GuidScrubber())
                        .forFile().withExtension(".json");
        // end-snippet
    }
}

