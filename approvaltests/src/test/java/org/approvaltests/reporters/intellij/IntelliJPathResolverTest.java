package org.approvaltests.reporters.intellij;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.Scrubber;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.reporters.EnvironmentAwareReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.MacDiffReporter;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@UseReporter(IntelliJReporter.class)
public class IntelliJPathResolverTest
{
  @Test
  void name() {
    IntelliJUltimateReporter ultimateReporter = IntelliJUltimateReporter.INSTANCE;
    System.out.println("File exists:  " + ultimateReporter.checkFileExists());
    System.out.println("Command line: " + Arrays.asList(ultimateReporter.getCommandLine("r.text", "a.txt")));


    assertEquals(true, ultimateReporter.isWorkingInThisEnvironment("a.txt"));
    FirstWorkingReporter firstWorkingReporter = new FirstWorkingReporter(new EnvironmentAwareReporter[]{IntelliJMacSiliconReporter.INSTANCE, IntelliJCommunityReporter.INSTANCE, MacDiffReporter.INSTANCE});

    Approvals.verify("applesauce", new Options().withReporter(firstWorkingReporter));
  }

  @Test
  void testDetectionOfIntellijOnDevMachines()
  {
    // begin-snippet: runOnlyOnSpecificMachines
    try (NamedEnvironment namedEnvironment = NamerFactory.asMachineNameSpecificTest())
    {
      if (!namedEnvironment.isCurrentEnvironmentValidFor(".lars-mbp-14"))
      { return; }
      // the rest of your test...
      // end-snippet
      final GenericDiffReporter environmentAwareReporter = (GenericDiffReporter) new IntelliJReporter()
          .getWorkingReportersForEnvironment().get(0);
      final String[] commandLine = environmentAwareReporter.getCommandLine("r.txt", "a.txt");
      // the moment it breaks and we are annoyed, start scrubbing.
      final Scrubber scrubber = new RegExScrubber("/21\\d.\\d+.\\d+/", "/211.xxx.xxx/");
      Approvals.verify(commandLine[0], new Options(scrubber));
    }
  }
  @Disabled("wip")
  @Test
  void testIntellijPaths()
  {
    String[] paths = new String[]{
                                  // begin-snippet: SupportedIntelliJPaths
                                  "/Users/fakeUser/Library/Application Support/JetBrains/Toolbox/apps/IDEA-C/ch-0/223.8617.56/IntelliJ IDEA CE.app/Contents/MacOS/idea",
                                  "/Users/fakeUser/Library/Application Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.8617.56/IntelliJ IDEA 2022.2 EAP.app"
        // end-snippet
    };
    Approvals.verifyAll("IntelliJ", paths, this::findIntellijReporter);
  }
  private String findIntellijReporter(String path)
  {
    try
    {
      IntelliJPathResolver.PATH_WALKER = (s,__) -> {
        if (path.startsWith(s))
        { return getPaths(Paths.get(path)).stream(); }
        return Stream.of();
      };
      Queryable<Edition> editions = Queryable.as(Edition.values());
      Edition foundReporter = editions
          .first(x -> !new IntelliJPathResolver(x).findIt().equals("C:\\Intelli-not-present.exe"));
      assertNotNull(foundReporter, "No reporter found for " + path);
      // foundReporter is null because fakeUser cannot be resolved from my disk
      String absolutePath = new IntelliJPathResolver(foundReporter).findIt();
      return String.format("%s [%s] \n\t\t found = %s\n\t\t given = %s", foundReporter, absolutePath.equals(path),
          absolutePath, path);
    }
    finally
    {
      IntelliJPathResolver.resetChannelPath();
    }
  }
  private static List<Path> getPaths(Path path)
  {
    List<Path> paths = new ArrayList<>();
    do
    {
      paths.add(path);
      path = path.getParent();
    }
    while (path != null);
    return paths;
  }
}
