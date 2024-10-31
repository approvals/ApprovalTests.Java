package org.approvaltests;

import com.spun.util.io.FileUtils;

import java.io.File;

public class ApprovedFileLog {
    static {
        FileUtils.writeFile(get(), "");
    }
    public static File get() {
        File file = new File(".approval_tests_temp/.approved_files.log");
        FileUtils.createIfNeeded(file.getAbsolutePath());
        return file;
    }

    public static void log(File file) {
        File log = get();
        FileUtils.appendToFile(log, file.getAbsolutePath() + "\n");
    }
}
