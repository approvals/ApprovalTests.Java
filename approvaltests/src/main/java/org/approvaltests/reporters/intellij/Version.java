package org.approvaltests.reporters.intellij;

import static java.lang.Integer.parseInt;

class Version implements Comparable<Version>
{
  final String version;
  Version(String version)
  {
    this.version = version;
  }
  static boolean isVersionFile(String version)
  {
    return version.matches("[0-9]+(\\.[0-9]+)*");
  }
  @Override
  public int compareTo(Version other)
  {
    String[] thisParts = version.split("\\.");
    String[] thatParts = other.version.split("\\.");
    int length = Math.max(thisParts.length, thatParts.length);
    for (int i = 0; i < length; i++)
    {
      int thisPart = (i < thisParts.length) ? parseInt(thisParts[i]) : 0;
      int thatPart = (i < thatParts.length) ? parseInt(thatParts[i]) : 0;
      if (thisPart < thatPart)
      { return -1; }
      if (thisPart > thatPart)
      { return 1; }
    }
    return 0;
  }
}
