package com.spun.util.persistence;

import com.spun.util.io.FileUtils;
import com.spun.util.velocity.VelocityParser;

import java.util.Calendar;
import java.util.List;

public class KitchenScheduler {

    // Called by production code
    public static String print(Calendar day) {
        return print(new LoadShiftsFromDatabase(day), day);
    }

    // Called by tests and the above function
    public static String print(Loader<List<Shift>> shifts, Calendar day) {
        String template = FileUtils.readFromClassPath(KitchenScheduler.class, "KitchenSchedule.template.md");
        String text = VelocityParser.parseString(template, c -> {
            c.put("day", day);
            c.put("shifts", shifts.load());
        });
        return text;
    }

}
