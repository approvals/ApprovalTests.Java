package org.approvaltests.reporters;

import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IntelliJMacResolverTest
{
  @Test
  void testFindIt()
  {
    String userHome = "Users/lars";
    Queryable<String> validPaths = Queryable.as("/Applications/IntelliJ IDEA.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Ultimate.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Community.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Community Edition.app/Contents/MacOS/idea");
    for (String path : validPaths)
    {
      DiffInfo diffInfo = IntelliJMacResolver.getDiffInfo(userHome, f -> f.equals(path));
      assertNotEquals("", diffInfo.diffProgram);
    }
  }
}
