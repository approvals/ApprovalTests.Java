package org.approvaltests;

import com.github.javaparser.Range;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class ParsingFilesTest
{
  public static void addApprovalTestPath()
  {
    ParserUtilities.SOURCE_PATHS.add("../approvaltests/src/main/java");
  }
  @Test
  public void getLineNumberOfThisMethod() throws Exception
  {
    addApprovalTestPath();
    var expected = """
        (line 5,col 3)-(line 7,col 3)
        """;
    Method method = XmlXomApprovals.class.getMethod("verify", String.class);
    Range r = ParserUtilities.getLineNumbersForMethod(method);
    Approvals.verify(r, new Options().inline(expected));
  }
}
