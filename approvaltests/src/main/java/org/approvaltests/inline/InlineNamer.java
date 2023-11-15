package org.approvaltests.inline;

import org.approvaltests.namer.ApprovalNamer;

import java.io.File;

public class InlineNamer implements ApprovalNamer {
    @Override
    public File getApprovedFile(String extensionWithDot) {
        return null;
    }

    @Override
    public File getReceivedFile(String extensionWithDot) {
        return null;
    }

    @Override
    public ApprovalNamer addAdditionalInformation(String info) {
        return null;
    }

    @Override
    public String getApprovalName() {
        return null;
    }

    @Override
    public String getSourceFilePath() {
        return null;
    }
}
