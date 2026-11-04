package org.approvaltests.partial;

import java.io.File;

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
    SelectionConfig config = SelectionConfig.load(configFile);
    File checkout = new File(".local/approvaltests-" + config.approvaltestsVersion);
    System.out.println("Checking out ApprovalTests " + config.approvaltestsVersion + " into " + checkout + "...");
    SourceCheckout.cloneTag(config.approvaltestsVersion, checkout);
    System.out.println("Tree Shaking...");
    System.out.println(TreeShaker.shake(checkout, config.entryPoints));
    System.out.println("To see the changes: git -C " + checkout + " status");
  }
}
