package org.approvaltests.reporters.intellij;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.spun.util.SystemUtils;

public class IntelliJPathResolver
{
  private final String channelsPath;
  public IntelliJPathResolver(Edition edition)
  {
    String toolboxPath = appData() + "/JetBrains/Toolbox";
    this.channelsPath = toolboxPath + "/apps/" + edition.getDirectory() + "/ch-0/";
  }
  private String appData()
  {
    String appData = "";
    if (SystemUtils.isWindowsEnviroment())
    {
      appData = System.getenv("LOCALAPPDATA");
    }
    else if (SystemUtils.isMacEnviroment())
    {
      appData = System.getenv("HOME");
      appData += "/Library/Application Support";
    }
    else // Linux
    {
      appData = System.getenv("HOME");
      appData += "/.local/share";
    }
    return appData;
  }
  public String findIt()
  {
    String notPresentPath = "C:\\Intelli-not-present.exe";
    try
    {
      return getIntelliJPath().map(Objects::toString).orElse(notPresentPath);
    }
    catch (IOException e)
    {
      return notPresentPath;
    }
  }
  private Optional<Path> getIntelliJPath() throws IOException
  {
    try (Stream<Path> walk = Files.walk(Paths.get(channelsPath), 1, FileVisitOption.FOLLOW_LINKS))
    {
      return walk //
          .map(Path::getFileName) //
          .map(Objects::toString) //
          .filter(Version::isVersionFile) //
          .map(Version::new) //
          .max(Comparator.naturalOrder()) //
          .map(this::getPath);
    }
  }
  private Path getPath(Version version)
  {
    return Paths.get(channelsPath + version.version + runtimeSuffix()).toAbsolutePath();
  }
  private String runtimeSuffix()
  {
    String runtimeSuffix;
    if (SystemUtils.isWindowsEnviroment())
    {
      runtimeSuffix = "/bin/idea64.exe";
    }
    else if (SystemUtils.isMacEnviroment())
    {
      runtimeSuffix = "/IntelliJ IDEA.app/Contents/MacOS/idea";
    }
    else // Linux
    {
      runtimeSuffix = "/bin/idea.sh";
    }
    return runtimeSuffix;
  }
}
