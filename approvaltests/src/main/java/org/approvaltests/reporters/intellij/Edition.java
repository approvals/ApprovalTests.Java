package org.approvaltests.reporters.intellij;

public enum Edition {
    Community("IDEA-C"), Ultimate("IDEA-U");
    private final String directory;

    Edition(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
