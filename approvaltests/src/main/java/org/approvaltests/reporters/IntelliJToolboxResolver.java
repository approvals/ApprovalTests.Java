package org.approvaltests.reporters;

import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

import java.io.File;

import static org.lambda.query.Queryable.as;

public class IntelliJToolboxResolver
{
  private interface Resolver extends Function2<String, Function1<String, Boolean>, DiffInfo>
  {

  }
  public static DiffInfo findIt()
  {
    for (Resolver function : new Resolver[]{IntelliJToolboxResolver::getDiffInfoMac,
                                            IntelliJToolboxResolver::getDiffInfoLinux})
    {
      DiffInfo diffInfo = function.call(System.getProperty("user.home"), f -> new File(f).exists());
      if (!diffInfo.isEmpty())
      { return diffInfo; }
    }
    return DiffInfo.getNull();
  }
  public static DiffInfo getDiffInfoMac(String userHome, Function1<String, Boolean> fileExists)
  {
    Queryable<String> locations = as("IntelliJ IDEA Ultimate", "IntelliJ IDEA", "IntelliJ IDEA Community",
        "IntelliJ IDEA Community Edition");
    Queryable<String> applications = as("/Applications", userHome + "/Applications");
    String postfix = ".app/Contents/MacOS/idea";
    return getDiffInfo(fileExists, applications, locations, postfix);
  }
  public static DiffInfo getDiffInfoLinux(String userHome, Function1<String, Boolean> fileExists)
  {
    Queryable<String> locations = as("intellij-idea-ultimate", "intellij-idea-community-edition");
    Queryable<String> applications = as(userHome + "/.local/share/JetBrains/Toolbox/apps");
    String postfix = "/bin/idea.sh";
    return getDiffInfo(fileExists, applications, locations, postfix);
  }
  private static DiffInfo getDiffInfo(Function1<String, Boolean> fileExists, Queryable<String> locations,
      Queryable<String> applications, String postfix)
  {
    Queryable<String> paths = locations.selectMany(a -> applications.select(l -> a + "/" + l + postfix));
    String matching = paths.first(fileExists);
    return new DiffInfo(matching, "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
  public static DiffInfo getDiffInfoWindows(String[] programFiles, Function1<String, Boolean> fileExists) {
    Queryable<String> applications = as("IntelliJ IDEA Ultimate", "IntelliJ IDEA", "IntelliJ IDEA Community",
            "IntelliJ IDEA Community Edition");
    Queryable<String> locations = Queryable.as(programFiles);
    String postfix = "\\bin\\idea64.exe";
    Queryable<String> paths = locations.selectMany(l -> applications.select(a -> l + "\\" + a + postfix));
    String matching = paths.first(fileExists);
    return new DiffInfo(matching, "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
