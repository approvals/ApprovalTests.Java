package org.approvaltests.reporters.intellij;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import org.lambda.functions.Function2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class IntelliJPathResolver
{
  public static Function2<String, Integer, Stream<Path>> PATH_WALKER = resetChannelPath();
  public static Function2<String, Integer, Stream<Path>> resetChannelPath()
  {
    PATH_WALKER = FileUtils::walkPath;
    return PATH_WALKER;
  }
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
    catch (Error e)
    {
      return notPresentPath;
    }
  }
  private Optional<Path> getIntelliJPath()
  {
    try (Stream<Path> walk = PATH_WALKER.call(channelsPath, 1))
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
      try (Stream<Path> walk = PATH_WALKER.call(channelsPath, 3))
      {
        final String s = findEapOrRegular(walk).orElse("/IntelliJ IDEA.app");
        runtimeSuffix = "/" + s + "/Contents/MacOS/idea";
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
