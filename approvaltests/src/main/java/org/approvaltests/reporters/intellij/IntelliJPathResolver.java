package org.approvaltests.reporters.intellij;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import com.spun.util.SystemUtils;
import java.util.stream.Stream;

public class IntelliJPathResolver
{
  private final String channelsPath;
  private final String runtimeSuffix;
  public IntelliJPathResolver(Edition edition)
  {
    String installRoot = getInstallRoot();
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
    String toolboxPath = installRoot + "/JetBrains/Toolbox";
    this.channelsPath = toolboxPath + "/apps/" + edition.getDirectory() + "/ch-0/";
  }

  private String getInstallRoot() {
    String installRoot = "";
    if (SystemUtils.isWindowsEnviroment())
    {
      installRoot = System.getenv("LOCALAPPDATA");
    }
    else if (SystemUtils.isMacEnviroment())
    {
      installRoot = System.getenv("HOME");
      installRoot += "/Library/Application Support";
    }
    else // Linux
    {
      installRoot = System.getenv("HOME");
      installRoot += "/.local/share";
    }
    return installRoot;
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
    return Paths.get(channelsPath + version.version + runtimeSuffix).toAbsolutePath();
  }
}
