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
  public StackTraceNamer()
  {
    info = TestUtils.getCurrentFileForMethod(new AttributeStackSelector());
    additionalInformation = NamerFactory.getAndClearAdditionalInformation();
  }

  public StackTraceNamer(StackTraceReflectionResult info, String additionalInformation)
  {
    this.info = info;
    this.additionalInformation = additionalInformation;
  }

  @Override
  public String getApprovalName()
  {
    return String.format("%s.%s%s", info.getClassName(), info.getMethodName(), additionalInformation);
  }

  @Override
  public String getSourceFilePath()
  {
    String sub = NamerFactory.getSubdirectory();
    String subdirectory = StringUtils.isEmpty(sub) ? "" : sub + File.separator;
    String baseDir = getBaseDirectory();
    return baseDir + File.separator + subdirectory;
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

  public TemplateFieldValues getTemplateFieldValues(String approvedOrReceived, String extensionWithDot)
  {
    String extension = extensionWithDot.startsWith(".") ? extensionWithDot.substring(1) : extensionWithDot;
    String subdirectory = NamerFactory.getSubdirectory();
    return new TemplateFieldValues(approvedOrReceived, StringUtils.isEmpty(subdirectory) ? "" : subdirectory,
        extension, "", info.getMethodName(), info.getClassName(), getBaseDirectory(), additionalInformation);
  }

  public ApprovalNamer addAdditionalInformation(String additionalInformation)
  {
    return new StackTraceNamer(this.info, this.additionalInformation + "." + additionalInformation);
  }

  @Override
  public String getAdditionalInformation()
  {
    return additionalInformation;
  }

  public StackTraceReflectionResult getInfo()
  {
    return info;
  }
}