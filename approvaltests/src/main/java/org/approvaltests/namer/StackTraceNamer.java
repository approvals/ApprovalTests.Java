package org.approvaltests.namer;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;
import java.io.File;

public class StackTraceNamer implements ApprovalNamer
{
  private StackTraceReflectionResult info;
  private String                     additionalInformation;
  private TemplateFieldValues        template;
  public StackTraceNamer()
  {
    info = TestUtils.getCurrentFileForMethod(new AttributeStackSelector());
    additionalInformation = NamerFactory.getAndClearAdditionalInformation();
    template = createTemplateFieldValues();
  }

  public StackTraceNamer(StackTraceReflectionResult info, String additionalInformation)
  {
    this.info = info;
    this.additionalInformation = additionalInformation;
    template = createTemplateFieldValues();
  }

  @Override
  public String getApprovalName()
  {
    return String.format("%s.%s%s", template.testFileName, template.testCaseName, template.additionalInformation);
  }

  @Override
  public String getSourceFilePath()
  {
    String sub = template.approvalsSubdirectory;
    String subdirectory = StringUtils.isEmpty(sub) ? "" : sub + File.separator;
    return template.testSourceDirectory + File.separator + subdirectory;
  }

  public String getBaseDirectory()
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
    TemplateFieldValues fields = getTemplateFieldValues(approvedOrReceived, extensionWithDot);
    return new File(TemplatedCustomNamer.resolveTemplate(TemplatedCustomNamer.DEFAULT_TEMPLATE, fields));
  }

  private TemplateFieldValues createTemplateFieldValues()
  {
    String subdirectory = NamerFactory.getSubdirectory();
    return new TemplateFieldValues("", StringUtils.isEmpty(subdirectory) ? "" : subdirectory, "", "",
        info.getMethodName(), info.getClassName(), getBaseDirectory(), additionalInformation);
  }

  public TemplateFieldValues getTemplateFieldValues(String approvedOrReceived, String extensionWithDot)
  {
    String extension = extensionWithDot.startsWith(".") ? extensionWithDot.substring(1) : extensionWithDot;
    return template.with(approvedOrReceived, extension);
  }

  public ApprovalNamer addAdditionalInformation(String additionalInformation)
  {
    return new StackTraceNamer(this.info, this.additionalInformation + "." + additionalInformation);
  }

  @Override
  public String getAdditionalInformation()
  {
    return template.additionalInformation;
  }

  public StackTraceReflectionResult getInfo()
  {
    return info;
  }
}