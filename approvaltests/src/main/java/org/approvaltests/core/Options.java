package org.approvaltests.core;

import com.spun.util.ArrayUtils;
import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.inline.InlineComparator;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamerWrapper;
import org.approvaltests.scrubbers.NoOpScrubber;
import org.approvaltests.writers.ApprovalWriterFactory;
import org.approvaltests.writers.DefaultApprovalWriterFactory;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

public class Options
{
  public static Options inline(String expected)
  {
    InlineComparator comparator = new InlineComparator(expected);
    return new Options().withComparator(comparator).forFile().withNamer(comparator).withWriter(comparator)
        .withReporter(comparator);
  }
  private enum Fields {
                       SCRUBBER, REPORTER, FILE_OPTIONS_FILE_EXTENSION, FILE_OPTIONS_NAMER, WRITER, COMPARATOR;
  }
  private final Map<Fields, Object> fields = new EnumMap<>(Fields.class);
  public Options()
  {
  }
  public Options and(Function1<Options, Options> optionsUpdate)
  {
    return optionsUpdate.call(this);
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
  public Function2<File, File, VerifyResult> getComparator()
  {
    return ArrayUtils.getOrElse(fields, Fields.COMPARATOR, () -> FileApprover::approveTextFile);
  }
  public Options withComparator(Function2<File, File, VerifyResult> comparator)
  {
    return new Options(fields, Fields.COMPARATOR, comparator);
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
  public ApprovalWriter createWriter(Object o)
  {
    ApprovalWriterFactory factory = ArrayUtils.getOrElse(fields, Fields.WRITER,
        DefaultApprovalWriterFactory::getDefaultFactory);
    return factory.create(o, this);
  }
  public Options withWriter(ApprovalWriterFactory approvalWriterFactory)
  {
    return new Options(fields, Fields.WRITER, approvalWriterFactory);
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
    public Options withNamer(ApprovalNamer namer)
    {
      return new Options(fields, Fields.FILE_OPTIONS_NAMER, namer);
    }
    public String getFileExtension()
    {
      return ArrayUtils.getOrElse(fields, Fields.FILE_OPTIONS_FILE_EXTENSION, () -> ".txt");
    }
    public Options withBaseName(String fileBaseName)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      return withNamer(approvalNamer);
    }
    public Options withName(String fileBaseName, String extension)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      EnumMap<Fields, Object> newFields = new EnumMap<>(fields);
      newFields.put(Fields.FILE_OPTIONS_FILE_EXTENSION, extension);
      return new Options(newFields, Fields.FILE_OPTIONS_NAMER, approvalNamer);
    }
    public Options withAdditionalInformation(String additionalInformation)
    {
      return withNamer(getNamer().addAdditionalInformation(additionalInformation));
    }
  }
}
