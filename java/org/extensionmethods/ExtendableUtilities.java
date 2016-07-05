package org.extensionmethods;

import com.spun.util.logger.SimpleLogger;

public class ExtendableUtilities
{
  public static boolean isNotConfigured()
  {
    try
    {
      new ExtendableBase<Object>();
    }
    catch (SecurityException s)
    {
      SimpleLogger.warning(
          "Please add ExtendableMethods.jar through -Xbootclasspath/p:path option of the JVM. Note 'p' as it has to be loaded first");
      return true;
    }
    return false;
  }
}
