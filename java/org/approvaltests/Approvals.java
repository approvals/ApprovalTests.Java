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
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.namer.StackTraceNamer;
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
import com.spun.util.JsonUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.images.ImageWriter;
import com.spun.util.persistence.ExecutableQuery;
import com.spun.util.persistence.Loader;
import com.spun.util.persistence.SqlLoader;

public class Approvals
{
  public static Loader<ApprovalNamer> namerCreater = new Loader<ApprovalNamer>()
  {
    public ApprovalNamer load()
    {
      return new StackTraceNamer();
    }
  };
  public static void verify(String response) throws Exception
  {
    verify(new ApprovalTextWriter(response, "txt"), FileTypes.Text);
  }
  public static void verify(Object o) throws Exception
  {
    verify("" + o);
  }
  public static <T> void verifyAll(String label, T[] array) throws Exception
  {
    verify(new ApprovalTextWriter(StringUtils.toString(label, array), "txt"), FileTypes.Text);
  }
  public static <T> void verifyAll(String header, String label, T[] array) throws Exception
  {
    verify(new ApprovalTextWriter(formatHeader(header) + StringUtils.toString(label, array), "txt"),
        FileTypes.Text);
  }
  public static <T> void verifyAll(T[] values, Function1<T, String> f1)
  {
    String text = ArrayUtils.toString(values, f1);
    verify(new ApprovalTextWriter(text, "txt"), FileTypes.Text);
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
    return StringUtils.isEmpty(header) ? "" : header + "\n\n\n";
  }
  public static <T> void verifyAll(String label, Iterable<T> array) throws Exception
  {
    verify(new ApprovalTextWriter(StringUtils.toString(label, array), "txt"), FileTypes.Text);
  }
  public static <T> void verifyAll(String header, String label, Iterable<T> array) throws Exception
  {
    verify(new ApprovalTextWriter(formatHeader(header) + StringUtils.toString(label, array), "txt"),
        FileTypes.Text);
  }
  public static void verify(Component c) throws Exception
  {
    try (NamedEnvironment env = NamerFactory.asOsSpecificTest())
    {
      verify(new ComponentApprovalWriter(c), FileTypes.Image);
    }
  }
  public static void verifyHtml(String response) throws Exception
  {
    verify(new ApprovalTextWriter(response, "html"), FileTypes.Html);
  }
  public static void verify(File generateFile)
  {
    verify(new FileApprovalWriter(generateFile), FileTypes.File);
  }
  public static void verify(Image image)
  {
    approve(ImageWriter.toBufferedImage(image), createApprovalNamer());
  }
  public static void verify(BufferedImage bufferedImage)
  {
    verify(new ImageApprovalWriter(bufferedImage), FileTypes.Image);
  }
  public static void verify(ApprovalWriter writter, ApprovalNamer namer, ApprovalFailureReporter reporter)
  {
    verify(new FileApprover(writter, namer), reporter);
  }
  public static void verify(ApprovalWriter writter, String fileType)
  {
    verify(writter, createApprovalNamer(), ReporterFactory.get(fileType));
  }
  public static void verifyXml(String xml) throws Exception
  {
    verify(new ApprovalXmlWriter(xml), FileTypes.Text);
  }
  public static void verify(FileApprover approver, ApprovalFailureReporter reporter)
  {
    try
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
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void verify(ExecutableQuery query) throws Exception
  {
    verify(new ApprovalTextWriter(query.getQuery(), "txt"), createApprovalNamer(),
        new ExecutableQueryFailure(query));
  }
  public static void verify(Map map) throws Exception
  {
    verify(new ApprovalTextWriter(StringUtils.toString(map), "txt"), FileTypes.Text);
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
  public static void verify(ResultSet rs) throws Exception
  {
    verify(new ResultSetApprovalWriter(rs), "csv");
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
    return namerCreater.load();
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
    String dirName = namer.getSourceFilePath() + Path.SEPARATOR + namer.getApprovalName() + ".Files";
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
  public static void verifyJson(String json) throws Exception
  {
    verify(JsonUtils.prettyPrint(json), "json");
  }
  public static void verify(Object actual, String fileExtensionWithoutDot) throws Exception
  {
    verify(new ApprovalTextWriter("" + actual, fileExtensionWithoutDot), FileTypes.Text);
  }
  public static ApprovalFailureReporter getReporter()
  {
    return ReporterFactory.get();
  }
}