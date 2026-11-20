package org.approvaltests.namer;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;
import java.io.File;

public class StackTraceNamer implements ApprovalNamer
{
  private StackTraceReflectionResult info;
  private TemplateFieldValues        template;
  public StackTraceReflectionResult getInfo()
  {
    return info;
  }

  public StackTraceNamer()
  {
    this(TestUtils.getCurrentFileForMethod(new AttributeStackSelector()),
        NamerFactory.getAndClearAdditionalInformation());
  }

  public StackTraceNamer(StackTraceReflectionResult info, String additionalInformation)
  {
    this(createTemplateFieldValues(info, additionalInformation), info);
  }

  public StackTraceNamer(TemplateFieldValues template, StackTraceReflectionResult info)
  {
    this.info = info;
    this.template = template;
  }

  @Override
  public String getApprovalName()
  {
    return TemplatedCustomNamer
        .resolveTemplate("{test_file_name}.{test_case_name}{additional_information}", template);
  }

  @Override
  public String getSourceFilePath()
  {
    return TemplatedCustomNamer.resolveTemplate("{test_source_directory}/{approvals_subdirectory}/", template);
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
    return new File(TemplatedCustomNamer.resolveTemplate(TemplatedCustomNamer.DEFAULT_TEMPLATE, fields));
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

  public ApprovalNamer addAdditionalInformation(String additionalInformation)
  {
    return new StackTraceNamer(
        this.template.withAdditionalInformation(this.template.additionalInformation + "." + additionalInformation),
        this.info);
  }

  @Override
  public String getAdditionalInformation()
  {
    return template.additionalInformation;
  }
}