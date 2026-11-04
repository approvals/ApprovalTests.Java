package org.approvaltests.partial;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PartialEvaluationCli
{
  public static final String USAGE = "usage: approvaltests-partial build --config <approvaltests-selection.json>";
  public static void main(String[] args)
  {
    if (args.length != 3 || !"build".equals(args[0]) || !"--config".equals(args[1]))
    {
      System.err.println(USAGE);
      System.exit(1);
    }
    build(new File(args[2]));
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
    System.out.println(TreeShaker.shake(checkout, config.entryPoints));
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
