package org.approvaltests.inline;

import org.approvaltests.core.ApprovalWriter;

import java.io.File;

public class InlineWriter implements ApprovalWriter {
    @Override
    public File writeReceivedFile(File received) {
        return  null;
    }

    @Override
    public String getFileExtensionWithDot() {
        return ".java";
    }
}
