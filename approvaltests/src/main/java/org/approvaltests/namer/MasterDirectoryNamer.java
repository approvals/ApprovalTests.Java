package org.approvaltests.namer;

import org.approvaltests.core.Options;

import java.io.File;

public class MasterDirectoryNamer implements ApprovalNamer {
    private final ApprovalNamer namer;
    private final String approvedFile;

    public MasterDirectoryNamer(File comparingFile, Options options) {
        this.namer = options.forFile().getNamer();
        this.approvedFile = comparingFile.getName();
    }

    @Override
    public File getApprovedFile(String extensionWithDot) {
        String dirName = namer.getSourceFilePath() + File.separator + namer.getApprovalName() + ".Files";
        return new File(dirName + File.separator + approvedFile);
    }

    @Override
    public File getReceivedFile(String extensionWithDot) {
        return namer.getReceivedFile(extensionWithDot);
    }

    @Override
    public String getApprovalName() {
        return namer.getApprovalName();
    }

    @Override
    public String getSourceFilePath() {
        return namer.getSourceFilePath();
    }
}
