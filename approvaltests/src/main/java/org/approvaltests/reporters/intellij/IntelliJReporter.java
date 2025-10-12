package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.GenericDiffReporter;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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
    try
    {
      // Use reflection to support Java 8 (ProcessHandle is Java 9+)
      Class<?> processHandleClass = Class.forName("java.lang.ProcessHandle");
      Method allProcessesMethod = processHandleClass.getMethod("allProcesses");
      Method infoMethod = processHandleClass.getMethod("info");
      Class<?> processInfoClass = Class.forName("java.lang.ProcessHandle$Info");
      Method commandMethod = processInfoClass.getMethod("command");
      @SuppressWarnings("unchecked")
      Stream<Object> processHandles = (Stream<Object>) allProcessesMethod.invoke(null);
      try
      {
        Stream<Optional<String>> processes = processHandles.map(p -> {
          try
          {
            Object processInfo = infoMethod.invoke(p);
            @SuppressWarnings("unchecked")
            Optional<String> command = (Optional<String>) commandMethod.invoke(processInfo);
            return command;
          }
          catch (Exception e)
          {
            return Optional.<String> empty();
          }
        });
        return processes.filter(Optional::isPresent).map(Optional::get).toArray(String[]::new);
      }
      finally
      {
        processHandles.close();
      }
    }
    catch (Exception e)
    {
      return new String[0];
    }
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
