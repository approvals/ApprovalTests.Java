package org.approvaltests.reporters;

import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.io.File;

public class IntelliJMacResolver
{
  public static DiffInfo findIt()
  {
    return getDiffInfo(System.getProperty("user.home"), f -> new File(f).exists());
  }
  public static DiffInfo getDiffInfo(String userHome, Function1<String, Boolean> fileExists)
  {
    Queryable<String> list = Queryable.as("IntelliJ IDEA Ultimate", "IntelliJ IDEA", "IntelliJ IDEA Community",
        "IntelliJ IDEA Community Edition");
    Queryable<String> applications = Queryable.as("/Applications", userHome + "/Applications");
    String matching = applications.selectMany(a -> list.select(l -> a + "/" + l + ".app/Contents/MacOS/idea"))
        .first(fileExists);
    return new DiffInfo(matching, "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
