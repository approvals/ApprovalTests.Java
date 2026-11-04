package org.approvaltests.partial;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PartialEvaluationCli
{
  public static final String USAGE = "usage: approvaltests-partial build --config <approvaltests-selection.json>\n"
      + "       approvaltests-partial explain --config <approvaltests-selection.json> <class or package prefix>";
  public static void main(String[] args)
  {
    if (args.length == 3 && "build".equals(args[0]) && "--config".equals(args[1]))
    {
      build(new File(args[2]));
    }
    else if (args.length == 4 && "explain".equals(args[0]) && "--config".equals(args[1]))
    {
      explain(new File(args[2]), args[3]);
    }
    else
    {
      System.err.println(USAGE);
      System.exit(1);
    }
  }

  public static void explain(File configFile, String prefix)
  {
    SelectionConfig config = SelectionConfig.load(configFile);
    File checkout = new File(System.getProperty("approvaltests.partial.local", ".local"),
        "approvaltests-" + config.approvaltestsVersion);
    checkout = SourceCheckout.cloneTag(config.approvaltestsVersion, checkout);
    List<File> thirdPartyJars = JarBuilder.thirdPartyJars(checkout);
    if (thirdPartyJars == null)
    {
      JarBuilder.build(checkout);
      thirdPartyJars = JarBuilder.thirdPartyJars(checkout);
    }
    ShakeReport report = TreeShaker.shake(checkout, config.entryPoints, thirdPartyJars);
    report.keptBecause.forEach((type, chain) -> {
      if (type.startsWith(prefix))
      {
        System.out.println(type + " is kept because:");
        chain.forEach(line -> System.out.println("    " + line));
        System.out.println();
      }
    });
  }

  public static void build(File configFile)
  {
    configFile = configFile.getAbsoluteFile().toPath().normalize().toFile();
    SelectionConfig config = SelectionConfig.load(configFile);
    String version = config.approvaltestsVersion;
    File checkout = new File(System.getProperty("approvaltests.partial.local", ".local"), "approvaltests-" + version);
    System.out.println("Checking out ApprovalTests " + version + " into " + checkout + "...");
    checkout = SourceCheckout.cloneTag(version, checkout);
    System.out.println("Building original jars...");
    JarBuilder.Stats originalStats = JarBuilder.stats(JarBuilder.build(checkout));
    Map<String, String> originalSources = HtmlReport.snapshot(checkout);
    System.out.println("Tree Shaking...");
    System.out.println(TreeShaker.shake(checkout, config.entryPoints, JarBuilder.thirdPartyJars(checkout)));
    System.out.println("Building partial jar...");
    List<File> jars = JarBuilder.build(checkout);
    File outputDirectory = new File(configFile.getParentFile(), config.output.directory);
    String artifactVersion = config.output.version == null ? version + "-custom" : config.output.version;
    File jar = JarBuilder.merge(jars, new File(outputDirectory, "approvaltests-" + artifactVersion + ".jar"),
        artifactVersion);
    HtmlReport.Inputs inputs = new HtmlReport.Inputs();
    inputs.config = config;
    inputs.configFile = configFile;
    inputs.checkout = checkout;
    inputs.originalSources = originalSources;
    inputs.originalStats = originalStats;
    inputs.partialStats = JarBuilder.stats(List.of(jar));
    inputs.jar = jar;
    File report = HtmlReport.write(inputs,
        new File(outputDirectory, "approvaltests-" + artifactVersion + "-report.html"));
    System.out.println("Jar:     " + jar);
    System.out.println("Report:  " + report);
    System.out.println("Changes: git -C " + checkout + " status");
  }
}
