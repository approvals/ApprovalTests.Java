package org.approvaltests.namer;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;
import java.io.File;

public class StackTraceNamer implements ApprovalNamer
{
  private TemplateFieldValues template;
  private final String        fileTemplate;
  public TemplateFieldValues getTemplate()
  {
    return template;
  }

  public StackTraceNamer()
  {
    this("");
  }

  public StackTraceNamer(String additionalInformation)
  {
    this(TestUtils.getCurrentFileForMethod(new AttributeStackSelector()),
        NamerFactory.getAndClearAdditionalInformation() + additionalInformation);
  }

  public StackTraceNamer(StackTraceReflectionResult info, String additionalInformation)
  {
    this(createTemplateFieldValues(info, additionalInformation));
  }

  public StackTraceNamer(TemplateFieldValues template)
  {
    this(template, TemplatedCustomNamer.DEFAULT_TEMPLATE);
  }

  public StackTraceNamer(TemplateFieldValues template, String fileTemplate)
  {
    this.template = template;
    this.fileTemplate = fileTemplate;
  }

  public String getBaseDirectory()
  {
    return TemplatedCustomNamer.resolveTemplate("{test_source_directory}", template);
  }

  @Override
  public File getReceivedFile(String extensionWithDot)
  {
    return getFile("received", extensionWithDot);
  }

  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    return getFile("approved", extensionWithDot);
  }

  private File getFile(String approvedOrReceived, String extensionWithDot)
  {
    String extension = extensionWithDot.startsWith(".") ? extensionWithDot.substring(1) : extensionWithDot;
    TemplateFieldValues fields = template.with(approvedOrReceived, extension);
    return new File(TemplatedCustomNamer.resolveTemplate(fileTemplate, fields));
  }

  private static TemplateFieldValues createTemplateFieldValues(StackTraceReflectionResult info,
      String additionalInformation)
  {
    String subdirectory = NamerFactory.getSubdirectory();
    return new TemplateFieldValues("", StringUtils.isEmpty(subdirectory) ? "" : subdirectory, "", "",
        info.getMethodName(), info.getClassName(), computeBaseDirectory(info), additionalInformation);
  }

  private static String computeBaseDirectory(StackTraceReflectionResult info)
  {
    String baseDir = info.getSourceFile().getAbsolutePath();
    if (!StringUtils.isEmpty(NamerFactory.getApprovalBaseDirectory()))
    {
      String packageName = info.getFullClassName().substring(0, info.getFullClassName().lastIndexOf("."));
      String packagepath = File.separator + packageName.replace('.', File.separatorChar);
      String currentBase = baseDir.substring(0, baseDir.indexOf(packagepath) + 1);
      String newBase = currentBase + NamerFactory.getApprovalBaseDirectory() + File.separator + packagepath;
      baseDir = ObjectUtils.throwAsError(() -> new File(newBase).getCanonicalPath().toString());
    }
    return baseDir;
  }
}