package org.approvaltests.namer;

import org.approvaltests.writers.Writer;

import java.io.File;

public class NamerUtils
{
  private static final String EXTENSION = ".txt";
  public static String getSourcePath(ApprovalNamer namer)
  {
    return namer.getApprovedFile(EXTENSION).getParent() + File.separator;
  }

  public static String getApprovalName(ApprovalNamer namer)
  {
    String name = namer.getApprovedFile(EXTENSION).getName();
    return name.substring(0, name.length() - (Writer.approved + EXTENSION).length());
  }
}
