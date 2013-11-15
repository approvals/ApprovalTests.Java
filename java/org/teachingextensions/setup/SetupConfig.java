package org.teachingextensions.setup;

import org.teachingextensions.setup.SetupValidator.SetupCheckPoints;

import com.spun.util.SystemUtils;
import com.spun.util.servlets.ValidationError;

public class SetupConfig
{
  public ValidationError setup              = new ValidationError(SetupCheckPoints.values());
  public String          workspacePath      = ".";                                                       //"C:\\Users\\Llewellyn\\workspace\\ApprovalTestsKoans\\TeachingKidsProgramming.Java";
  public String          eclipsePath        = SystemUtils.isWindowsEnviroment()
                                                ? eclipsePathWindows
                                                : eclipsePathMac;
  public static String   eclipsePathWindows = "c:\\eclipse\\eclipse.exe";
  public static String   eclipsePathMac     = "/Applications/eclipse/Eclipse.app/Contents/MacOS/eclipse";
  public void assertSetupIsCorrect()
  {
    if (!setup.isOk()) { throw setup; }
  }
}
