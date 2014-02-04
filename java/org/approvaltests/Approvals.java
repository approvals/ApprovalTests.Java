package org.approvaltests;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.approvaltests.ReporterFactory.FileTypes;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.ApprovalFailureOverrider;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.ApprovalNamerFactory;
import org.approvaltests.namer.JUnitStackTraceNamer;
import org.approvaltests.reporters.ExecutableQueryFailure;
import org.approvaltests.writers.ApprovalBinaryFileWriter;
import org.approvaltests.writers.ApprovalTextWriter;
import org.approvaltests.writers.ApprovalXmlWriter;
import org.approvaltests.writers.ComponentApprovalWriter;
import org.approvaltests.writers.DirectoryToDirectoryWriter;
import org.approvaltests.writers.FileApprovalWriter;
import org.approvaltests.writers.ImageApprovalWriter;
import org.approvaltests.writers.ResultSetApprovalWriter;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import org.lambda.functions.Function1;
import org.lambda.functions.implementations.F1;
import org.lambda.query.Query;

import com.spun.util.ArrayUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.images.ImageWriter;
import com.spun.util.persistence.ExecutableQuery;
import com.spun.util.persistence.SqlLoader;

public class Approvals
{
  /**
   * @deprecated Use verify()
   */
  public static void approve(String response) throws Exception
  {
    verify(response);
  }
  public static void verify(String response) throws Exception
  {
    verify(new ApprovalTextWriter(response, "txt"), FileTypes.Text);
  }
  public static void verify(Object o) throws Exception
  {
    verify("" + o);
  }
  /**
   * @deprecated Use verifyAll()
   */
  public static <T> void approve(String label, T[] array) throws Exception
  {
    verifyAll(label, array);
  }
  public static <T> void verifyAll(String label, T[] array) throws Exception
  {
    verify(new ApprovalTextWriter(StringUtils.toString(label, array), "txt"), FileTypes.Text);
  }
  /**
   * @deprecated Use verifyAll()
   */
  public static <T> void approve(String header, String label, T[] array) throws Exception
  {
    verifyAll(header, label, array);
  }
  public static <T> void verifyAll(String header, String label, T[] array) throws Exception
  {
    verify(new ApprovalTextWriter(formatHeader(header) + StringUtils.toString(label, array), "txt"),
        FileTypes.Text);
  }
  /**
   * @deprecated Use verifyAll()
   */
  public static <T> void approve(T[] values, Function1<T, String> f1)
  {
    verifyAll(values, f1);
  }
  public static <T> void verifyAll(T[] values, Function1<T, String> f1)
  {
    String text = ArrayUtils.toString(values, f1);
    verify(new ApprovalTextWriter(text, "txt"), FileTypes.Text);
  }
  /**
   * @deprecated Use verifyAll()
   */
  public static <T> void approve(String header, T[] values, Function1<T, String> f1)
  {
    verifyAll(header, values, f1);
  }
  public static <T> void verifyAll(String header, T[] values, Function1<T, String> f1)
  {
    verifyAll(header, Arrays.asList(values), f1);
  }
  public static <T> void verifyAll(String header, Iterable<T> array, Function1<T, String> f1)
  {
    String text = formatHeader(header) + ArrayUtils.toString(array, f1);
    verify(new ApprovalTextWriter(text, "txt"), FileTypes.Text);
  }
  private static String formatHeader(String header)
  {
    return StringUtils.isEmpty(header) ? "" : header + "\r\n\r\n\r\n";
  }
  /**
   * @deprecated Use verifyAll()
   */
  public static <T> void approve(String label, Iterable<T> array) throws Exception
  {
    verifyAll(label, array);
  }
  public static <T> void verifyAll(String label, Iterable<T> array) throws Exception
  {
    verify(new ApprovalTextWriter(StringUtils.toString(label, array), "txt"), FileTypes.Text);
  }
  /**
   * @deprecated Use verifyAll()
   */
  public static <T> void approve(String header, String label, Iterable<T> array) throws Exception
  {
    verifyAll(header, label, array);
  }
  public static <T> void verifyAll(String header, String label, Iterable<T> array) throws Exception
  {
    verify(new ApprovalTextWriter(formatHeader(header) + StringUtils.toString(label, array), "txt"),
        FileTypes.Text);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(Component c) throws Exception
  {
    verify(c);
  }
  public static void verify(Component c) throws Exception
  {
    verify(new ComponentApprovalWriter(c), FileTypes.Image);
  }
  /**
   * @deprecated Use verifyHtml()
   */
  public static void approveHtml(String response) throws Exception
  {
    verifyHtml(response);
  }
  public static void verifyHtml(String response) throws Exception
  {
    verify(new ApprovalTextWriter(response, "html"), FileTypes.Html);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(File generateFile)
  {
    verify(generateFile);
  }
  public static void verify(File generateFile)
  {
    verify(new FileApprovalWriter(generateFile), FileTypes.File);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(Image image)
  {
    verify(image);
  }
  public static void verify(Image image)
  {
    approve(ImageWriter.toBufferedImage(image), createApprovalNamer());
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(BufferedImage bufferedImage)
  {
    verify(bufferedImage);
  }
  public static void verify(BufferedImage bufferedImage)
  {
    verify(new ImageApprovalWriter(bufferedImage), FileTypes.Image);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(ApprovalWriter writter, ApprovalNamer namer, ApprovalFailureReporter reporter)
  {
    verify(writter, namer, reporter);
  }
  public static void verify(ApprovalWriter writter, ApprovalNamer namer, ApprovalFailureReporter reporter)
  {
    verify(new FileApprover(writter, namer), reporter);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(ApprovalWriter writter, String fileType)
  {
    verify(writter, fileType);
  }
  public static void verify(ApprovalWriter writter, String fileType)
  {
    verify(writter, createApprovalNamer(), ReporterFactory.get(fileType));
  }
  /**
   * @deprecated Use verifyXml()
   */
  public static void approveXml(String xml) throws Exception
  {
    verifyXml(xml);
  }
  public static void verifyXml(String xml) throws Exception
  {
    verify(new ApprovalXmlWriter(xml), FileTypes.Text);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(FileApprover approver, ApprovalFailureReporter reporter)
  {
    verify(approver, reporter);
  }
  public static void verify(FileApprover approver, ApprovalFailureReporter reporter)
  {
    try
    {
      if (!approver.approve())
      {
        boolean passed = false;
        if (reporter instanceof ApprovalFailureOverrider)
        {
          passed = approver.askToChangeReceivedToApproved((ApprovalFailureOverrider) reporter);
        }
        if (!passed)
        {
          approver.reportFailure(reporter);
          approver.fail();
        }
        else
        {
          approver.cleanUpAfterSuccess(reporter);
        }
      }
      else
      {
        approver.cleanUpAfterSuccess(reporter);
      }
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(ExecutableQuery query) throws Exception
  {
    verify(query);
  }
  public static void verify(ExecutableQuery query) throws Exception
  {
    verify(new ApprovalTextWriter(query.getQuery(), "txt"), new JUnitStackTraceNamer(),
        new ExecutableQueryFailure(query));
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(Map map) throws Exception
  {
    verify(map);
  }
  public static void verify(Map map) throws Exception
  {
    verify(new ApprovalTextWriter(StringUtils.toString(map), "txt"), FileTypes.Text);
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(RackResponse response) throws Exception
  {
    verify(response);
  }
  public static void verify(RackResponse response) throws Exception
  {
    if (isImage(response))
    {
      String fileType = "png";
      verify(new ApprovalBinaryFileWriter(response.getResponse(), fileType), fileType);
    }
    else
    {
      verifyHtml(response.getResponse().toString());
    }
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(ResultSet rs) throws Exception
  {
    verify(rs);
  }
  public static void verify(ResultSet rs) throws Exception
  {
    verify(new ResultSetApprovalWriter(rs), "csv");
  }
  /**
   * @deprecated Use verify()
   */
  public static void approve(SqlLoader loader) throws Exception
  {
    verify(loader);
  }
  public static void verify(SqlLoader loader) throws Exception
  {
    verify(new SqlLoader.ExecutableWrapper(loader));
  }
  private static boolean isImage(RackResponse response)
  {
    String type = response.getHeaders().get(RackResponseUtils.CONTENT_TYPE);
    return RackResponseUtils.CONTENT_TYPE_IMAGE.equals(type);
  }
  public static ApprovalNamer createApprovalNamer()
  {
    return ApprovalNamerFactory.getApprovalNamer();
  }
  private static void approve(BufferedImage bufferedImage, ApprovalNamer namer)
  {
    verify(new ImageApprovalWriter(bufferedImage), FileTypes.Image);
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
    String dirName = namer.getApprovalFileBasePath() + Path.SEPARATOR + namer.getApprovalName() + ".Files";
    File approvedDirectory = new File(dirName);
    List<File> mismatched = new ArrayList<File>();
    for (File f : files)
    {
      if (!f.isDirectory())
      {
        try
        {
          verify(new DirectoryToDirectoryWriter(f, approvedDirectory), FileTypes.File);
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
    return Query.select(mismatched, new F1<File, String>(mismatched.get(1))
    {
      {
        ret(a.getName());
      }
    }).toString();
  }
}