package org.approvaltests.reporters;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractJUnitReporter implements EnvironmentAwareReporter
{
  private String className;
  public AbstractJUnitReporter(String className)
  {
    this.className = className;
  }
  public void assertEquals(String expected, String actual)
  {
    try
    {
      Class<?> clazz = ObjectUtils.loadClass(className);
      Method assertEquals = clazz.getMethod("assertEquals", Object.class, Object.class);
      assertEquals.invoke(null, expected, actual);
    }
    catch (InvocationTargetException exception)
    {
      throw ObjectUtils.throwAsError(exception.getTargetException());
    }
    catch (Throwable throwable)
    {
      throw ObjectUtils.throwAsError(throwable);
    }
  }
  @Override
  public boolean report(String received, String approved)
  {
    String aText = new File(approved).exists() ? FileUtils.readFile(approved) : "";
    String rText = FileUtils.readFile(received);
    String approveCommand = "To approve run : " + ClipboardReporter.getAcceptApprovalText(received, approved);
    SimpleLogger.message(approveCommand);
    assertEquals(aText, rText);
    return isWorkingInThisEnvironment(received);
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    boolean present = ObjectUtils.isClassPresent(className);
    return present && GenericDiffReporter.isFileExtensionValid(forFile, GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  }
}
