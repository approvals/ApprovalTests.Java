package org.approvaltests.reporters.intellij;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.Scrubber;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import java.nio.file.Paths;

@UseReporter(IntelliJReporter.class)
public class IntelliJPathResolverTest
{
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
  @Test
  void testIntellijPaths()
  {
    String[] paths = new String[]{"/Users/lars/Library/Application Support/JetBrains/Toolbox/apps/IDEA-C/ch-0/223.8617.56/IntelliJ IDEA CE.app/Contents/MacOS/idea"};
    Approvals.verifyAll("IntelliJ", paths, this::findIntellijReporter);
  }
  private String findIntellijReporter(String path)
  {
    try
    {
      IntelliJPathResolver.PATH_WALKER = s -> Queryable.as(Paths.get(path)).stream();
      Edition foundReporter = Queryable.as(Edition.values())
          .first(x -> new IntelliJPathResolver(x).findIt() != null);
      return String.format("%s <- %s", foundReporter, path);
    }
    finally
    {
      IntelliJPathResolver.PATH_WALKER = IntelliJPathResolver::walkPath;
    }
  }
}
