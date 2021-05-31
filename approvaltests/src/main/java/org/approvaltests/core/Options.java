package org.approvaltests.core;

import java.util.HashMap;
import java.util.Map;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamerWrapper;
import org.approvaltests.scrubbers.NoOpScrubber;
import org.approvaltests.writers.ApprovalTextWriter;
import org.lambda.functions.Function2;

import com.spun.util.ArrayUtils;

public class Options
{
  public ApprovalWriter createWriter(Object o)
  {
    return getWriter().call(o, this);
    //    writer.build(o);
    //    return writer;
  }
  private Function2<Object, Options, ApprovalWriter> getWriter()
  {
    return ArrayUtils.getOrElse(fields, Fields.WRITER, () -> (c, o) -> new ApprovalTextWriter("" + c, o));
  }
  public Options withWriter(Function2<Object, Options, ApprovalWriter> approvalWriterSupplier)
  {
    return new Options(fields, Fields.WRITER, approvalWriterSupplier);
  }
  private enum Fields {
                       SCRUBBER, REPORTER, FILE_OPTIONS_FILE_EXTENSION, FILE_OPTIONS_NAMER, WRITER;
  }
  private final Map<Fields, Object> fields = new HashMap<>();
  public Options()
  {
  }
  public Options(Scrubber scrubber)
  {
    fields.put(Fields.SCRUBBER, scrubber);
  }
  public Options(ApprovalFailureReporter reporter)
  {
    fields.put(Fields.REPORTER, reporter);
  }
  private Options(Map<Fields, Object> fields, Fields key, Object value)
  {
    this.fields.putAll(fields);
    this.fields.put(key, value);
  }
  public ApprovalFailureReporter getReporter()
  {
    return ArrayUtils.getOrElse(fields, Fields.REPORTER, ReporterFactory::get);
  }
  public Options withReporter(ApprovalFailureReporter reporter)
  {
    return new Options(fields, Fields.REPORTER, reporter);
  }
  public Options withScrubber(Scrubber scrubber)
  {
    return new Options(fields, Fields.SCRUBBER, scrubber);
  }
  public String scrub(String input)
  {
    return getScrubber().scrub(input);
  }
  private Scrubber getScrubber()
  {
    return ArrayUtils.getOrElse(fields, Fields.SCRUBBER, () -> NoOpScrubber.INSTANCE);
  }
  public FileOptions forFile()
  {
    return new FileOptions(this.fields);
  }
  public static class FileOptions
  {
    private final Map<Fields, Object> fields;
    public FileOptions(Map<Fields, Object> fields)
    {
      this.fields = fields;
    }
    public Options withExtension(String fileExtensionWithDot)
    {
      if (!fileExtensionWithDot.startsWith("."))
      {
        fileExtensionWithDot = "." + fileExtensionWithDot;
      }
      return new Options(fields, Fields.FILE_OPTIONS_FILE_EXTENSION, fileExtensionWithDot);
    }
    public ApprovalNamer getNamer()
    {
      return ArrayUtils.getOrElse(fields, Fields.FILE_OPTIONS_NAMER, Approvals::createApprovalNamer);
    }
    public String getFileExtension()
    {
      return ArrayUtils.getOrElse(fields, Fields.FILE_OPTIONS_FILE_EXTENSION, () -> ".txt");
    }
    public Options withBaseName(String fileBaseName)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      return new Options(fields, Fields.FILE_OPTIONS_NAMER, approvalNamer);
    }
    public Options withName(String fileBaseName, String extension)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      HashMap<Fields, Object> newFields = new HashMap<>(fields);
      newFields.put(Fields.FILE_OPTIONS_FILE_EXTENSION, extension);
      return new Options(newFields, Fields.FILE_OPTIONS_NAMER, approvalNamer);
    }
  }
}
