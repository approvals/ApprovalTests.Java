package org.approvaltests;

import com.spun.util.ArrayUtils;
import com.spun.util.FormattedException;
import com.spun.util.JsonUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.persistence.ExecutableCommand;
import com.spun.util.persistence.Loader;
import com.spun.util.persistence.SqlLoader;
import org.approvaltests.approvers.ApprovalApprover;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.*;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.MasterDirectoryNamer;
import org.approvaltests.namer.NamerFactoryForOptions;
import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.reporters.ExecutableQueryFailure;
import org.approvaltests.writers.ApprovalXmlWriter;
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
import java.util.Objects;

public class Approvals
{
  public static final NamerFactoryForOptions NAMES        = NamerFactoryForOptions.INSTANCE;
  public static Loader<ApprovalNamer>        namerCreater = new Loader<ApprovalNamer>()
                                                          {
                                                            public ApprovalNamer load()
                                                            {
                                                              return new StackTraceNamer();
                                                            }
                                                          };
  public static void verify(String response)
  {
    verify(response, new Options());
  }
  public static void verify(String response, Options options)
  {
    verify(options.createWriter(response), options);
  }
  public static void verify(Object object)
  {
    verify(object, new Options());
  }
  public static void verify(Object object, Options options)
  {
    checkForAwtComponents(object == null ? Object.class : object.getClass());
    verify(Objects.toString(object), options);
  }
  public static void verify(Verifiable object)
  {
    verify(object, new Options());
  }
  public static void verify(Verifiable object, Options options)
  {
    VerifyParameters verifyParameters = object.getVerifyParameters(options);
    verify(Objects.toString(object), verifyParameters.getOptions());
  }
  private static void checkForAwtComponents(Class<?> type)
  {
    if (type == Object.class)
    { return; }
    if ("java.awt.Component".equals(type.getCanonicalName()))
    {
      throw new FormattedException(
          "Approvals.verify(Component) has moved to AwtApprovals.verify(Component)\ncalled Approvals.verify(Object)");
    }
    checkForAwtComponents(type.getSuperclass());
  }
  public static <T> void verifyAll(String label, T[] array)
  {
    verifyAll(label, array, new Options());
  }
  public static <T> void verifyAll(String label, T[] array, Options options)
  {
    verify(StringUtils.toString(label, array), options);
  }
  public static <T> void verifyAll(String header, String label, T[] array)
  {
    verifyAll(header, label, array, new Options());
  }
  public static <T> void verifyAll(String header, String label, T[] array, Options options)
  {
    String text = formatHeader(header) + StringUtils.toString(label, array);
    verify(text, options);
  }
  public static <T> void verifyAll(T[] values, Function1<T, String> f1)
  {
    verifyAll(values, f1, new Options());
  }
  public static <T> void verifyAll(Iterable<T> values, Function1<T, String> f1)
  {
    verifyAll(values, f1, new Options());
  }
  public static <T> void verifyAll(Iterable<T> values, Function1<T, String> f1, Options options)
  {
    verifyAll("", values, f1, options);
  }
  public static <T> void verifyAll(T[] values, Function1<T, String> f1, Options options)
  {
    String text = ArrayUtils.toString(values, f1);
    verify(text, options);
  }
  public static <T> void verifyAll(String header, T[] values, Function1<T, String> f1)
  {
    verifyAll(header, values, f1, new Options());
  }
  public static <T> void verifyAll(String header, T[] values, Function1<T, String> f1, Options options)
  {
    verifyAll(header, Arrays.asList(values), f1, options);
  }
  public static <T> void verifyAll(String header, Iterable<T> array, Function1<T, String> f1)
  {
    verifyAll(header, array, f1, new Options());
  }
  public static <T> void verifyAll(String header, Iterable<T> array, Function1<T, String> f1, Options options)
  {
    String text = formatHeader(header) + ArrayUtils.toString(array, f1);
    verify(text, options);
  }
  private static String formatHeader(String header)
  {
    return StringUtils.isEmpty(header) ? "" : header + "\n\n\n";
  }
  public static <T> void verifyAll(String label, Iterable<T> array)
  {
    verifyAll(label, array, new Options());
  }
  public static <T> void verifyAll(String label, Iterable<T> array, Options options)
  {
    verify(StringUtils.toString(label, array), options);
  }
  public static <T> void verifyAll(String header, String label, Iterable<T> array)
  {
    verifyAll(header, label, array, new Options());
  }
  public static <T> void verifyAll(String header, String label, Iterable<T> array, Options options)
  {
    String text = formatHeader(header) + StringUtils.toString(label, array);
    verify(text, options);
  }
  public static void verifyHtml(String response)
  {
    verifyHtml(response, new Options());
  }
  public static void verifyHtml(String response, Options options)
  {
    verify(response, options.forFile().withExtension(".html"));
  }
  public static void verify(File generateFile)
  {
    verify(generateFile, new Options());
  }
  public static void verify(File generateFile, Options options)
  {
    verify(options.createWriter(generateFile), options);
  }
  /**
   * @deprecated Use {@link #verify(ApprovalWriter, ApprovalNamer, Options)} instead.
   */
  @Deprecated
  public static void verify(ApprovalWriter writer, ApprovalNamer namer, ApprovalFailureReporter reporter)
  {
    verify(new FileApprover(writer, namer), new Options(reporter));
  }
  public static void verify(ApprovalWriter writer, ApprovalNamer namer)
  {
    verify(writer, namer, new Options());
  }
  public static void verify(ApprovalWriter writer, ApprovalNamer namer, Options options)
  {
    verify(new FileApprover(writer, namer, options.getComparator()), options);
  }
  public static void verify(ApprovalWriter writer)
  {
    verify(writer, new Options());
  }
  public static void verify(ApprovalWriter writer, Options options)
  {
    verify(writer, options.forFile().getNamer(), options);
  }
  public static void verifyXml(String xml)
  {
    verifyXml(xml, new Options());
  }
  public static void verifyXml(String xml, Options options)
  {
    final String formattedXml = ApprovalXmlWriter.prettyPrint(xml, 2);
    verify(formattedXml, options.forFile().withExtension(".xml"));
  }
  public static void verify(ApprovalApprover approver)
  {
    verify(approver, new Options());
  }
  /**
   * @deprecated Use {@link #verify(ApprovalApprover, Options)} instead.
   */
  @Deprecated
  public static void verify(ApprovalApprover approver, ApprovalFailureReporter reporter)
  {
    verify(approver, new Options(reporter));
  }
  public static void verify(ApprovalApprover approver, Options options)
  {
    ApprovalFailureReporter reporter = options.getReporter();
    VerifyResult result = approver.approve();
    if (result.isFailure())
    {
      result = approver.reportFailure(reporter);
    }
    if (result.isSuccessful())
    {
      approver.cleanUpAfterSuccess(reporter);
    }
    else
    {
      approver.fail();
    }
  }
  public static void verify(ExecutableCommand query)
  {
    verify(query, new Options());
  }
  public static void verify(ExecutableCommand query, Options options)
  {
    verify(query.getCommand(), ExecutableQueryFailure.create(query, options));
  }
  public static void verify(Map<?, ?> map)
  {
    verify(map, new Options());
  }
  public static void verify(Map<?, ?> map, Options options)
  {
    verify(StringUtils.toString(map), options);
  }
  public static void verify(ResultSet rs)
  {
    verify(rs, new Options());
  }
  public static void verify(ResultSet rs, Options options)
  {
    verify(options.createWriter(rs), options);
  }
  public static <T> void verify(SqlLoader<T> loader)
  {
    verify(loader, new Options());
  }
  public static <T> void verify(SqlLoader<T> loader, Options options)
  {
    verify(new SqlLoader.ExecutableWrapper<T>(loader), options);
  }
  public static ApprovalNamer createApprovalNamer()
  {
    return namerCreater.load();
  }
  public static void verifyEachFileInDirectory(File directory)
  {
    verifyEachFileInDirectory(directory, new Options());
  }
  public static void verifyEachFileInDirectory(File directory, Options options)
  {
    verifyEachFileAgainstMasterDirectory(directory.listFiles(), options);
  }
  public static void verifyEachFileInDirectory(File directory, FileFilter filter)
  {
    verifyEachFileInDirectory(directory, filter, new Options());
  }
  public static void verifyEachFileInDirectory(File directory, FileFilter filter, Options options)
  {
    verifyEachFileAgainstMasterDirectory(directory.listFiles(filter), options);
  }
  public static void verifyEachFileInDirectory(File directory, FilenameFilter filter)
  {
    verifyEachFileInDirectory(directory, filter, new Options());
  }
  public static void verifyEachFileInDirectory(File directory, FilenameFilter filter, Options options)
  {
    verifyEachFileAgainstMasterDirectory(directory.listFiles(filter), options);
  }
  private static void verifyEachFileAgainstMasterDirectory(File[] files, Options options)
  {
    List<File> mismatched = new ArrayList<>();
    for (File f : files)
    {
      if (!f.isDirectory())
      {
        try
        {
          verify(options.createWriter(f), new MasterDirectoryNamer(f, options), options);
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
  /**
   * @deprecated Use JsonApprovals
   */
  @Deprecated
  public static void verifyJson(String json)
  {
    JsonApprovals.verifyJson(json);
  }
  /**
   * @deprecated Use JsonApprovals
   */
  @Deprecated
  public static void verifyJson(String json, Options options)
  {
    JsonApprovals.verifyJson(json, options);
  }
  /**
   * @deprecated View method source for example.
   */
  @Deprecated
  public static void verify(Object actual, String fileExtensionWithoutDot)
  {
    verify(actual, fileExtensionWithoutDot, new Options());
  }
  /**
   * @deprecated View method source for example.
   */
  @Deprecated
  public static void verify(Object actual, String fileExtensionWithoutDot, Options options)
  {
    verify("" + actual, options.forFile().withExtension(fileExtensionWithoutDot));
  }
  public static ApprovalFailureReporter getReporter()
  {
    return ReporterFactory.get();
  }
  /**
   * @deprecated Use JsonApprovals
   */
  @Deprecated
  public static void verifyAsJson(Object o)
  {
    JsonApprovals.verifyAsJson(o);
  }
  /**
   * @deprecated Use JsonApprovals
   */
  @Deprecated
  public static <T> void verifyAsJson(Object o, Function1<T, T> gsonBuilder, Class<T> gsonBuilderClass)
  {
    verifyAsJson(o, gsonBuilder, gsonBuilderClass, new Options());
  }
  /**
   * @deprecated Use JsonApprovals
   */
  @Deprecated
  public static <T> void verifyAsJson(Object o, Function1<T, T> gsonBuilder, Class<T> gsonBuilderClass,
      Options options)
  {
    verify(JsonUtils.asJsonWithBuilder(o, gsonBuilder, gsonBuilderClass),
        options.forFile().withExtension(".json"));
  }
  /**
   * @deprecated Use JsonApprovals
   */
  @Deprecated
  public static void verifyAsJson(Object o, Options options)
  {
    JsonApprovals.verifyAsJson(o, options);
  }
  public static void verifyException(Action0 runnableBlock)
  {
    verifyException(runnableBlock, new Options());
  }
  public static void verifyException(Action0 runnableBlock, Options options)
  {
    Throwable t = ObjectUtils.captureException(runnableBlock);
    if (t == null)
    { throw new FormattedException("No exception thrown when running %s", runnableBlock); }
    Approvals.verify(String.format("%s: %s", t.getClass().getName(), t.getMessage()), options);
  }
  public static ApprovalSettings settings()
  {
    return new ApprovalSettings();
  }
}
