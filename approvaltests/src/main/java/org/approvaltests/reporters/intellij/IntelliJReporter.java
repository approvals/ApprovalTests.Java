package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.GenericDiffReporter;

import java.lang.ProcessHandle;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class IntelliJReporter extends GenericDiffReporter
{
  public static final IntelliJReporter INSTANCE = new IntelliJReporter();
  public IntelliJReporter()
  {
    super(new DiffInfo(getPath(), "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS));
  }
  private static String getPath()
  {
    try
    {
      return findJetBrainsIdes();
    }
    catch (Throwable e)
    {
      // requires Java 9+
      return "";
    }
  }
  public static String findJetBrainsIdes()
  {
    String[] runningPrograms = getRunningPrograms();
    return findJetBrainsIdes(runningPrograms);
  }
  private static String[] getRunningPrograms()
  {
    return ProcessHandle.allProcesses().map(p -> p.info().command()).filter(Optional::isPresent).map(c -> c.get())
        .toArray(String[]::new);
  }
  public static String findJetBrainsIdes(String[] commands)
  {
    Set<String> seenPaths = new HashSet<>();
    String[] keywords = {"idea",
                         "pycharm",
                         "webstorm",
                         "phpstorm",
                         "goland",
                         "rider",
                         "clion",
                         "rubymine",
                         "appcode",
                         "datagrip"};
    for (String command : commands)
    {
      String lowerCommand = command.toLowerCase();
      for (String keyword : keywords)
      {
        if (lowerCommand.contains(keyword) && isMainExecutable(command, keyword))
        {
          if (!seenPaths.contains(command))
          {
            seenPaths.add(command);
            return command;
          }
          break;
        }
      }
    }
    return "";
  }
  public static boolean isMainExecutable(String path, String keyword)
  {
    String lowerPath = path.toLowerCase();
    return lowerPath.endsWith("macos/" + keyword) || lowerPath.contains("bin\\" + keyword);
  }
}
