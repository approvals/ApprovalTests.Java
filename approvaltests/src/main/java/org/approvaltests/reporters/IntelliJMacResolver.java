package org.approvaltests.reporters;

import org.lambda.query.Queryable;

import java.io.File;

public class IntelliJMacResolver
{
  public static DiffInfo findIt()
  {
    Queryable<String> list = Queryable.as("IntelliJ IDEA Ultimate", "IntelliJ IDEA", "IntelliJ IDEA Community",
        "IntelliJ IDEA Community Edition");
    Queryable<String> applications = Queryable.as("/Applications",
        System.getProperty("user.home") + "/Applications");
    String matching = applications.selectMany(a -> list.select(l -> a + "/" + l + ".app/Contents/MacOS/idea"))
            .first(f -> new File(f).exists());
    return new DiffInfo(matching, "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
