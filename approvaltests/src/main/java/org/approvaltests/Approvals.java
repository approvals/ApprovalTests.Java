package org.approvaltests;

import com.spun.util.*;
import com.spun.util.persistence.ExecutableQuery;
import com.spun.util.persistence.Loader;
import com.spun.util.persistence.SqlLoader;
import org.approvaltests.approvers.ApprovalApprover;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.reporters.ExecutableQueryFailure;
import org.approvaltests.writers.*;
import org.lambda.actions.Action0;
import org.lambda.functions.Function1;
import org.lambda.query.Query;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Approvals
{
  public static Loader<ApprovalNamer> namerCreater = new Loader<ApprovalNamer>()
  {
    public ApprovalNamer load()
    {
      return new StackTraceNamer();
    }
  };
  public static void verify(String response)
  {
    verify(new ApprovalTextWriter(response, "txt"));
  }
  public static void verify(Object o)
  {
    verify("" + o);
  }
  public static <T> void verifyAll(String label, T[] array)
  {
    verify(new ApprovalTextWriter(StringUtils.toString(label, array), "txt"));
  }
  public static <T> void verifyAll(String header, String label, T[] array)
  {
    verify(new ApprovalTextWriter(formatHeader(header) + StringUtils.toString(label, array), "txt"));
  }
  public static <T> void verifyAll(T[] values, Function1<T, String> f1)
  {
    String text = ArrayUtils.toString(values, f1);
    verify(new ApprovalTextWriter(text, "txt"));
  }
  public static <T> void verifyAll(String header, T[] values, Function1<T, String> f1)
  {
    verifyAll(header, Arrays.asList(values), f1);
  }
  public static <T> void verifyAll(String header, Iterable<T> array, Function1<T, String> f1)
  {
    String text = formatHeader(header) + ArrayUtils.toString(array, f1);
    verify(new ApprovalTextWriter(text, "txt"));
  }
  private static String formatHeader(String header)
  {
    return StringUtils.isEmpty(header) ? "" : header + "\n\n\n";
  }
  public static <T> void verifyAll(String label, Iterable<T> array)
  {
    verify(new ApprovalTextWriter(StringUtils.toString(label, array), "txt"));
  }
  public static <T> void verifyAll(String header, String label, Iterable<T> array)
  {
    verify(new ApprovalTextWriter(formatHeader(header) + StringUtils.toString(label, array), "txt"));
  }

  public static void verifyHtml(String response)
  {
    verify(new ApprovalTextWriter(response, "html"));
  }
  public static void verify(File generateFile)
  {
    verify(new FileApprovalWriter(generateFile));
  }

  public static void verify(ApprovalWriter writer, ApprovalNamer namer, ApprovalFailureReporter reporter)
  {
    verify(new FileApprover(writer, namer), reporter);
  }
  public static void verify(ApprovalWriter writer)
  {
    verify(writer, createApprovalNamer(), getReporter());
  }
  public static void verifyXml(String xml)
  {
    verify(new ApprovalXmlWriter(xml));
  }
  public static void verify(ApprovalApprover approver)
  {
    verify(approver, getReporter());
  }
  public static void verify(ApprovalApprover approver, ApprovalFailureReporter reporter)
  {
    if (!approver.approve())
    {
      approver.reportFailure(reporter);
      approver.fail();
    }
    else
    {
      approver.cleanUpAfterSuccess(reporter);
    }
  }
  public static void verify(ExecutableQuery query)
  {
    verify(new ApprovalTextWriter(query.getQuery(), "txt"), createApprovalNamer(),
        new ExecutableQueryFailure(query));
  }
  public static void verify(Map<?, ?> map)
  {
    verify(new ApprovalTextWriter(StringUtils.toString(map), "txt"));
  }
  public static void verify(ResultSet rs)
  {
    verify(new ResultSetApprovalWriter(rs));
  }
  public static <T> void verify(SqlLoader<T> loader)
  {
    verify(new SqlLoader.ExecutableWrapper<T>(loader));
  }
  public static ApprovalNamer createApprovalNamer()
  {
    return namerCreater.load();
  }

  public static void verifyEachFileInDirectory(File directory)
  {
    verifyEachFileAgainstMasterDirectory(directory.listFiles());
  }
  public static void verifyEachFileInDirectory(File directory, FileFilter filter)
  {
    verifyEachFileAgainstMasterDirectory(directory.listFiles(filter));
  }
  public static void verifyEachFileInDirectory(File directory, FilenameFilter filter)
  {
    verifyEachFileAgainstMasterDirectory(directory.listFiles(filter));
  }
  private static void verifyEachFileAgainstMasterDirectory(File[] files) throws Error
  {
    ApprovalNamer namer = createApprovalNamer();
    String dirName = namer.getSourceFilePath() + File.separator + namer.getApprovalName() + ".Files";
    File approvedDirectory = new File(dirName);
    List<File> mismatched = new ArrayList<File>();
    for (File f : files)
    {
      if (!f.isDirectory())
      {
        try
        {
          verify(new DirectoryToDirectoryWriter(f, approvedDirectory));
        }
        catch (Throwable e)
        {
          mismatched.add(f);
        }
      }
    }
    if (!mismatched.isEmpty())
    {
      String message = "The Following Files did not match up: " + getFileNameList(mismatched);
      throw new Error(message);
    }
  }
  private static String getFileNameList(List<File> mismatched)
  {
    return Query.select(mismatched, a -> a.getName()).toString();
  }
  public static void verifyJson(String json)
  {
    verify(JsonUtils.prettyPrint(json), "json");
  }
  public static void verify(Object actual, String fileExtensionWithoutDot)
  {
    verify(new ApprovalTextWriter("" + actual, fileExtensionWithoutDot));
  }
  public static ApprovalFailureReporter getReporter()
  {
    return ReporterFactory.get();
  }
  public static void verifyAsJson(Object o)
  {
    verify(JsonUtils.asJson(o), "json");
  }
  public static void verifyException(Action0 runnableBlock)
  {
    Throwable t = ObjectUtils.captureException(runnableBlock);
    if (t == null) { throw new FormattedException("No exception thrown when running %s", runnableBlock); }
    Approvals.verify(String.format("%s: %s", t.getClass().getName(), t.getMessage()));
  }
}