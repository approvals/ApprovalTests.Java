package org.approvaltests.reporters;

import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IntelliJResolverTest
{
  @Test
  void testFindItOnMac()
  {
    Queryable<String> validPaths = Queryable.as("/Applications/IntelliJ IDEA.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Ultimate.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Community.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Community Edition.app/Contents/MacOS/idea");
    verifyPaths(IntelliJToolboxResolver::getDiffInfoMac, "Users/lars", validPaths);
  }
  private static void verifyPaths(Function2<String, Function1<String, Boolean>, DiffInfo> finder, String userHome,
      Queryable<String> validPaths)
  {
    for (String path : validPaths)
    {
      DiffInfo diffInfo = finder.call(userHome, f -> f.equals(path));
      assertNotEquals("", diffInfo.diffProgram, path);
    }
  }
  @Test
  void testFindItOnLinux()
  {
    Queryable<String> validPaths = Queryable.as(
        "/home/lars/.local/share/JetBrains/Toolbox/apps/intellij-idea-ultimate/bin/idea.sh",
        "/home/lars/.local/share/JetBrains/Toolbox/apps/intellij-idea-community-edition/bin/idea.sh");
    verifyPaths(IntelliJToolboxResolver::getDiffInfoLinux, "/home/lars", validPaths);
  }
  // "C:\Users\larse\AppData\Local\Programs\IntelliJ IDEA Ultimate\bin\idea64.exe"
  @Test
  void testFindItOnWindows()
  {
    Queryable<String> validPaths = Queryable
        .as("C:\\Users\\larse\\AppData\\Local\\Programs\\IntelliJ IDEA Ultimate\\bin\\idea64.exe");
    String[] programFiles = {"C:\\Users\\larse\\AppData\\Local\\Programs"};
    for (String path : validPaths)
    {
      DiffInfo diffInfo = IntelliJToolboxResolver.getDiffInfoWindows(programFiles, f -> f.equals(path));
      assertNotEquals("", diffInfo.diffProgram, path);
    }
  }
}
