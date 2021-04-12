package org.approvaltests.reporters.intellij;

public enum Edition {
                     Community("IDEA-C"), Ultimate("IDEA-U"), Silicon("IDEA-ARM");
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
