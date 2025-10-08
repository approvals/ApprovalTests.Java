package org.approvaltests;

import com.github.javaparser.Range;
import com.spun.util.ClassUtils;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class ParsingFilesTest
{
  public static void addApprovalTestPath()
  {
    ParserUtilities.SOURCE_PATHS.add(ClassUtils.getProjectRootPath() + "/../approvaltests/src/main/java");
  }

  @Test
  public void getLineNumberOfThisMethod() throws Exception
  {
    addApprovalTestPath();
    var expected = """
        (line 13,col 3)-(line 16,col 3)
        """;
    Method method = XmlXomApprovals.class.getMethod("verifyXml", String.class);
    Range r = ParserUtilities.getLineNumbersForMethod(method);
    Approvals.verify(r, new Options().inline(expected));
  }
}
