package org.approvaltests.namer;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;
import org.approvaltests.writers.Writer;

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
      String packagepath = packageName.replace('.', File.separatorChar);
      String currentBase = baseDir.substring(0, baseDir.indexOf(packagepath));
      String newBase = currentBase + NamerFactory.getApprovalBaseDirectory() + File.separator + packagepath;
      baseDir = ObjectUtils.throwAsError(() -> new File(newBase).getCanonicalPath().toString());
    }
    return baseDir;
  }
  @Override
  public File getReceivedFile(String extensionWithDot)
  {
    return new File(getSourceFilePath() + "/" + getApprovalName() + Writer.received + extensionWithDot);
  }
  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    return new File(getSourceFilePath() + "/" + getApprovalName() + Writer.approved + extensionWithDot);
  }
}
