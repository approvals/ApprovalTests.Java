package org.approvaltests.partial;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartialEvaluationCli
{
  public static final String USAGE = "usage: approvaltests-partial build [--current] --config <approvaltests-selection.json>\n"
      + "       approvaltests-partial explain [--current] --config <approvaltests-selection.json> <class or package prefix>\n"
      + "  --current  build from the latest master on GitHub instead of the approvaltestsVersion tag";
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
    if (config != null && positional.size() == 1 && "build".equals(positional.get(0)))
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
