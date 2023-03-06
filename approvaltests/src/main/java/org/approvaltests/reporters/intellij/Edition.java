package org.approvaltests.reporters.intellij;

public enum Edition {
                     Ultimate("IDEA-U"), Community("IDEA-C"), Silicon("IDEA-ARM");
  private final String directory;
  Edition(String directory)
  {
    this.directory = directory;
  }
  public String getDirectory()
  {
    return directory;
  }
}
