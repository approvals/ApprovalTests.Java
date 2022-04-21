package org.approvaltests.reporters.intellij;

import com.spun.util.ObjectUtils;
import com.spun.util.SystemUtils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
    if (SystemUtils.isWindowsEnvironment())
    {
      appData = System.getenv("LOCALAPPDATA");
    }
    else if (SystemUtils.isMacEnvironment())
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
    if (SystemUtils.isWindowsEnvironment())
    {
      runtimeSuffix = "/bin/idea64.exe";
    }
    else if (SystemUtils.isMacEnvironment())
    {
      try (Stream<Path> walk = Files.walk(Paths.get(channelsPath), 3, FileVisitOption.FOLLOW_LINKS))
      {
        final String s = findEapOrRegular(walk).orElse("/IntelliJ IDEA.app");
        runtimeSuffix = "/" + s + "/Contents/MacOS/idea";
      }
      catch (IOException e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
    else // Linux
    {
      runtimeSuffix = "/bin/idea.sh";
    }
    return runtimeSuffix;
  }
  private Optional<String> findEapOrRegular(Stream<Path> walk)
  {
    return walk.map(Path::getFileName).map(Objects::toString).filter(s -> s.endsWith(".app")).findAny();
  }
}
