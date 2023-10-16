package org.approvaltests.reporters;

import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.io.File;

public class IntelliJMacResolver
{
  public static DiffInfo findIt()
  {
    DiffInfo diffInfo = getDiffInfo(System.getProperty("user.home"), f -> new File(f).exists());
    DiffInfo diffInfo2 = getDiffInfoLinux(System.getProperty("user.home"), f -> new File(f).exists());
    return diffInfo.diffProgram.equals("") ? diffInfo2 : diffInfo;
  }
  public static DiffInfo getDiffInfo(String userHome, Function1<String, Boolean> fileExists)
  {
    Queryable<String> locations = Queryable.as("IntelliJ IDEA Ultimate", "IntelliJ IDEA", "IntelliJ IDEA Community",
            "IntelliJ IDEA Community Edition");
    Queryable<String> applications = Queryable.as("/Applications", userHome + "/Applications");
    String postfix = ".app/Contents/MacOS/idea";
    return getDiffInfo(fileExists, applications, locations, postfix);
  }

  public static DiffInfo getDiffInfoLinux(String userHome, Function1<String, Boolean> fileExists)
  {
    Queryable<String> locations = Queryable.as("intellij-idea-ultimate");
    Queryable<String> applications = Queryable.as( userHome + "/.local/share/JetBrains/Toolbox/apps");
    String postfix = "/bin/idea.sh";
    return getDiffInfo(fileExists, applications, locations, postfix);
  }

  private static DiffInfo getDiffInfo(Function1<String, Boolean> fileExists, Queryable<String> locations, Queryable<String> list, String postfix) {
    Queryable<String> paths = locations.selectMany(a -> list.select(l -> a + "/" + l + postfix));
    String matching = paths.first(fileExists);
    return new DiffInfo(matching, "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
