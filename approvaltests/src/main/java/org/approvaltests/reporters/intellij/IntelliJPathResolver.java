package org.approvaltests.reporters.intellij;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class IntelliJPathResolver
{
  public static final String                             PATH_NOT_FOUND = "C:\\Intelli-not-present.exe";
  public static Function2<String, Integer, Stream<Path>> PATH_WALKER    = resetChannelPath();
  public static Function2<String, Integer, Stream<Path>> resetChannelPath()
  {
    PATH_WALKER = FileUtils::walkPath;
    return PATH_WALKER;
  }
  private final String channelsPath;
  public IntelliJPathResolver(Edition edition)
  {
    this(edition, appData());
  }

  public IntelliJPathResolver(Edition edition, String appDataLocation)
  {
    String toolboxPath = appDataLocation + "/JetBrains/Toolbox";
    this.channelsPath = toolboxPath + "/apps/" + edition.getDirectory() + "/ch-0/";
  }

  private static String appData()
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
    return getIntelliJPath().map(Objects::toString).orElse(PATH_NOT_FOUND);
  }

  private Optional<Path> getIntelliJPath()
  {
    try (Stream<Path> walk = PATH_WALKER.call(channelsPath, 1))
    {
      Queryable<Path> paths = Queryable.as(walk);
      Queryable<String> namesStrings = paths.select(path -> "" + path.getFileName());
      Queryable<String> versionsNames = namesStrings.where(Version::isVersionFile);
      Queryable<Version> versions = versionsNames.select(Version::new);
      Version max = versions.max(v -> v);
      return Optional.ofNullable(max).map(this::getPath);
    }
    catch (Error e)
    {
      return Optional.empty();
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
