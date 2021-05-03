package org.approvaltests.core;

import java.util.HashMap;
import java.util.Map;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamerWrapper;
import org.approvaltests.scrubbers.NoOpScrubber;
import org.lambda.functions.Function0;

public class Options
{
  private final Map<String, Object> fields = new HashMap<>();
  public Options()
  {
  }
  public Options(Scrubber scrubber)
  {
    fields.put("scrubber", scrubber);
  }
  public Options(ApprovalFailureReporter reporter)
  {
    fields.put("reporter", reporter);
  }
  private Options(Map<String, Object> fields, String key, Object value)
  {
    this.fields.putAll(fields);
    this.fields.put(key, value);
  }
  public Options(Options parent, FileOptions fileOptions)
  {
    this(parent.fields, "fileOptions", fileOptions);
  }
  public ApprovalFailureReporter getReporter()
  {
    return getOrElse("reporter", ReporterFactory::get);
  }
  private <T> T getOrElse(String key, Function0<T> defaultIfNotFound)
  {
    return getOrElse(fields, key, defaultIfNotFound);
  }
  public static <T> T getOrElse(Map<String, Object> fields, String key, Function0<T> defaultIfNotFound)
  {
    if (fields.containsKey(key))
    {
      return (T) fields.get(key);
    }
    else
    {
      return defaultIfNotFound.call();
    }
  }
  public Options withReporter(ApprovalFailureReporter reporter)
  {
    return new Options(fields, "reporter", reporter);
  }
  public Options withScrubber(Scrubber scrubber)
  {
    return new Options(fields, "scrubber", scrubber);
  }
  public String scrub(String input)
  {
    return getScrubber().scrub(input);
  }
  private Scrubber getScrubber()
  {
    return getOrElse("scrubber", () -> NoOpScrubber.INSTANCE);
  }
  public FileOptions forFile()
  {
    return new FileOptions(this.fields);
  }
  public static class FileOptions
  {
    private final Map<String, Object> fields;
    public FileOptions(Map<String, Object> fields)
    {
      this.fields = fields;
    }
    public Options withExtension(String fileExtensionWithDot)
    {
      if (!fileExtensionWithDot.startsWith("."))
      {
        fileExtensionWithDot = "." + fileExtensionWithDot;
      }
      return new Options(fields, "fileOptions.fileExtension", fileExtensionWithDot);
    }
    public ApprovalNamer getNamer()
    {
      return getOrElse(fields, "fileOptions.namer", Approvals::createApprovalNamer);
    }
    public String getFileExtension()
    {
      return getOrElse(fields, "fileOptions.fileExtension", () -> ".txt");
    }
    public Options withBaseName(String fileBaseName)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      return new Options(fields, "fileOptions.namer", approvalNamer);
    }
    public Options withName(String fileBaseName, String extension)
    {
      NamerWrapper approvalNamer = new NamerWrapper(() -> fileBaseName, getNamer());
      HashMap<String, Object> newFields = new HashMap<>(fields);
      newFields.put("fileOptions.fileExtension", extension);
      return new Options(newFields, "fileOptions.namer", approvalNamer);
    }
  }
}
