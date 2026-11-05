package org.approvaltests.partial;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartialEvaluationCli
{
  public static final String USAGE = "usage: approvaltests-partial build [--current] --config <approvaltests-selection.json>\n"
      + "       approvaltests-partial explain [--current] --config <approvaltests-selection.json> <class or package prefix>\n"
      + "       approvaltests-partial detect [--config <approvaltests-selection.json>]\n"
      + "  --current  build from the latest master on GitHub instead of the approvaltestsVersion tag\n"
      + "  detect     scans the .java files from the current directory down and writes the selection file";
  public static void main(String[] args)
  {
    boolean current = false;
    File config = null;
    List<String> positional = new ArrayList<>();
    for (int i = 0; i < args.length; i++)
    {
      if ("--current".equals(args[i]))
      {
        current = true;
      }
      else if ("--config".equals(args[i]) && i + 1 < args.length)
      {
        config = new File(args[++i]);
      }
      else
      {
        positional.add(args[i]);
      }
    }
    if (positional.size() == 1 && "detect".equals(positional.get(0)))
    {
      detect(config == null ? new File("approvaltests-selection.json") : config);
    }
    else if (config != null && positional.size() == 1 && "build".equals(positional.get(0)))
    {
      build(config, current);
    }
    else if (config != null && positional.size() == 2 && "explain".equals(positional.get(0)))
    {
      explain(config, positional.get(1), current);
    }
    else
    {
      System.err.println(USAGE);
      System.exit(1);
    }
  }

  public static void detect(File configFile)
  {
    File project = new File(".").getAbsoluteFile().toPath().normalize().toFile();
    configFile = configFile.getAbsoluteFile().toPath().normalize().toFile();
    System.out.println("Scanning " + project + " for ApprovalTests usages...");
    SelectionDetector.Result result = SelectionDetector.detect(project, projectClasspath(project));
    SelectionConfig config = configFile.exists() ? SelectionConfig.load(configFile) : new SelectionConfig();
    if (result.approvaltestsVersion != null)
    {
      config.approvaltestsVersion = result.approvaltestsVersion;
    }
    config.entryPoints = new ArrayList<>(result.entryPoints);
    if (config.runtimeDiscoveries == null)
    {
      config.runtimeDiscoveries = new java.util.LinkedHashMap<>(Map.of("*", "keep"));
    }
    if (config.output == null)
    {
      config.output = new SelectionConfig.Output();
      config.output.directory = "target/approvaltests-custom";
      config.output.version = config.approvaltestsVersion + "-custom";
      config.output.pom = true;
      config.output.sources = true;
      config.output.install = "none";
    }
    config.save(configFile);
    System.out.println("Java files scanned: " + result.javaFiles);
    System.out.println("ApprovalTests version: " + config.approvaltestsVersion);
    System.out.println("Entry points (" + config.entryPoints.size() + "):");
    config.entryPoints.forEach(e -> System.out.println("  " + e));
    if (!result.unresolved.isEmpty())
    {
      System.out.println("Could not resolve (check these by hand):");
      result.unresolved.forEach(u -> System.out.println("  " + u));
    }
    System.out.println("Wrote " + configFile);
  }

  private static List<File> projectClasspath(File project)
  {
    if (!new File(project, "pom.xml").exists())
    { throw new RuntimeException("detect needs a pom.xml in " + project + " to find the ApprovalTests jars"); }
    try
    {
      File output = java.nio.file.Files.createTempFile("approvaltests-classpath", ".txt").toFile();
      Shell.output(project, "mvn", "-q", "-B", "dependency:build-classpath", "-Dmdep.includeScope=test",
          "-Dmdep.outputFile=" + output.getAbsolutePath());
      List<File> classpath = new ArrayList<>();
      for (String entry : com.spun.util.io.FileUtils.readFile(output).trim().split(File.pathSeparator))
      {
        classpath.add(new File(entry));
      }
      output.delete();
      return classpath;
    }
    catch (java.io.IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  private static File checkout(SelectionConfig config, boolean current)
  {
    File root = new File(System.getProperty("approvaltests.partial.local", ".local"));
    if (current)
    {
      File checkout = new File(root, "approvaltests-master");
      System.out.println("Checking out the latest ApprovalTests master into " + checkout + "...");
      checkout = SourceCheckout.cloneBranch("master", checkout);
      config.approvaltestsVersion = "master@" + SourceCheckout.shortCommit(checkout);
      return checkout;
    }
    File checkout = new File(root, "approvaltests-" + config.approvaltestsVersion);
    System.out.println("Checking out ApprovalTests " + config.approvaltestsVersion + " into " + checkout + "...");
    return SourceCheckout.cloneTag(config.approvaltestsVersion, checkout);
  }

  public static void explain(File configFile, String prefix, boolean current)
  {
    SelectionConfig config = SelectionConfig.load(configFile);
    File checkout = checkout(config, current);
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

  public static void build(File configFile, boolean current)
  {
    configFile = configFile.getAbsoluteFile().toPath().normalize().toFile();
    SelectionConfig config = SelectionConfig.load(configFile);
    File checkout = checkout(config, current);
    System.out.println("Building original jars...");
    JarBuilder.Stats originalStats = JarBuilder.stats(JarBuilder.build(checkout));
    Map<String, String> originalSources = HtmlReport.snapshot(checkout);
    System.out.println("Tree Shaking...");
    System.out.println(TreeShaker.shake(checkout, config.entryPoints, JarBuilder.thirdPartyJars(checkout)));
    System.out.println("Building partial jar...");
    List<File> jars = JarBuilder.build(checkout);
    File outputDirectory = new File(configFile.getParentFile(), config.output.directory);
    String artifactVersion = current ? "master-custom"
        : config.output.version == null ? config.approvaltestsVersion + "-custom" : config.output.version;
    File jar = JarBuilder.merge(jars, new File(outputDirectory, "approvaltests-" + artifactVersion + ".jar"),
        current ? config.approvaltestsVersion + "-custom" : artifactVersion);
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
