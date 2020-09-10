package org.approvaltests.scrubbers;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class DateScrubberTests
{
    @Test
    void testGetDateScrubber() {
        String[] formats = {"Tue May 13 16:30:00",
                "Tue May 13 2014 23:30:00.789",
                "Tue May 13 16:30:00 -0800 2014",
                "13 May 2014 23:50:49,999",
                "May 13, 2014 11:30:00 PM PST",
                "23:30:00",
                "2014/05/13 16:30:59.786",
                "2020-09-10T08:07Z",
                "2020-9-10T08:07Z",
                "2020-09-9T08:07Z",
                "2020-09-10T8:07Z",
                "2020-09-10T01:23:45.678Z"
        };
        Approvals.verifyAll("Date scrubbing", formats, this::verifyScrubbing);
    }

    private String verifyScrubbing(String formattedExample) {
        DateScrubber scrubber = DateScrubber.getScrubberFor(formattedExample);
        String exampleText = String.format("{'date':\"%s\"}",formattedExample);
        return String.format("Scrubbing for %s:\n" +
                "%s\n" +
                "Example: %s\n\n", formattedExample, scrubber, scrubber.scrub(exampleText));
    }
}
