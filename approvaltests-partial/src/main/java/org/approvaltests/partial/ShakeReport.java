package org.approvaltests.partial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ShakeReport
{
  public int                  totalFiles;
  public int                  unresolvedCalls;
  public List<String>         missingEntryPoints = new ArrayList<>();
  public List<String>         deletedFiles       = new ArrayList<>();
  public List<String>         removedTypes       = new ArrayList<>();
  public List<String>         deletedDirectories = new ArrayList<>();
  public Map<String, Integer>      removedMethods = new TreeMap<>();
  public Map<String, List<String>> keptBecause    = new TreeMap<>();
  public void removedMethod(String file)
  {
    removedMethods.merge(file, 1, Integer::sum);
  }

  @Override
  public String toString()
  {
    StringBuilder b = new StringBuilder();
    b.append("Files: ").append(totalFiles).append(" total, ").append(deletedFiles.size()).append(" deleted, ")
        .append(totalFiles - deletedFiles.size()).append(" kept\n");
    b.append("Unresolved calls (kept by name): ").append(unresolvedCalls).append("\n");
    section(b, "Missing entry points", missingEntryPoints);
    List<String> removed = new ArrayList<>();
    removedMethods.forEach((file, count) -> removed.add(file + " (" + count + ")"));
    section(b, "Removed methods", removed);
    section(b, "Removed nested types", removedTypes);
    section(b, "Deleted files", deletedFiles);
    section(b, "Deleted directories", deletedDirectories);
    return b.toString();
  }

  private static void section(StringBuilder b, String title, List<String> lines)
  {
    List<String> sorted = new ArrayList<>(lines);
    Collections.sort(sorted);
    b.append("\n").append(title).append(" (").append(sorted.size()).append("):\n");
    for (String line : sorted)
    {
      b.append("  ").append(line).append("\n");
    }
  }
}
