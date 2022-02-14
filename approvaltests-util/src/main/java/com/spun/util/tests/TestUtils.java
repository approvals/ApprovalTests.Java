package com.spun.util.tests;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;
import com.spun.util.images.ImageWriter;
import com.spun.util.io.FileUtils;
import com.spun.util.io.StackElementLevelSelector;
import com.spun.util.io.StackElementSelector;
import org.lambda.functions.Function2;

import javax.mail.Message;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtils
{
  private static Random                                            random;
  private static final ThreadLocal<Function2<Class, String, File>> getSourceDirectory = ThreadLocal
      .withInitial(() -> ClassUtils::getSourceDirectory);
  public static SourceDirectoryRestorer registerSourceDirectoryFinder(
      Function2<Class, String, File> sourceDirectoryFinder)
  {
    SourceDirectoryRestorer c = new SourceDirectoryRestorer();
    TestUtils.getSourceDirectory.set(sourceDirectoryFinder);
    return c;
  }
  public static class SourceDirectoryRestorer implements AutoCloseable
  {
    private final Function2<Class, String, File> original;
    public SourceDirectoryRestorer()
    {
      this.original = TestUtils.getSourceDirectory.get();
    }
    @Override
    public void close()
    {
      TestUtils.getSourceDirectory.set(original);
    }
  }
  public static File getFile(String startingDir)
  {
    JFrame frame = new JFrame();
    WindowUtils.testFrame(frame, false);
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(startingDir));
    int returnVal = chooser.showOpenDialog(frame);
    File returning = null;
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      returning = chooser.getSelectedFile();
    }
    frame.dispose();
    return returning;
  }
  public static void displayXml(String htmlOutput)
  {
    try
    {
      displayHtml(null, ".xml", htmlOutput, 3);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static String getRandomString()
  {
    if (random == null)
    {
      random = new Random();
    }
    return Long.toString(Math.abs(random.nextLong()), 36);
  }
  public static void displayHtml(String htmlOutput)
  {
    displayHtml(null, ".html", htmlOutput, 3);
  }
  public static void displayHtmlFile(String fileName)
  {
    displayFile(fileName);
  }
  public static void displayHtmlFile(File file)
  {
    if (!file.exists())
    { return; }
    displayHtmlFile(file.getAbsolutePath());
  }
  public static void displayHtml(String outputFile, String htmlOutput)
  {
    displayHtml(outputFile, ".html", htmlOutput, 15);
  }
  public static void displayHtml(String outputFile, String fileExtention, String htmlOutput, int secondsTimeout)
  {
    try
    {
      File file = (outputFile == null) ? File.createTempFile("temp", fileExtention) : new File(outputFile);
      FileUtils.writeFile(file, htmlOutput);
      displayHtmlFile(file);
      Thread.sleep(secondsTimeout * 1000);
      if (outputFile == null)
      {
        file.deleteOnExit();
      }
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void displayText(String output)
  {
    displayHtml(null, ".txt", output, 3);
  }
  public static void displayExcel(String output)
  {
    displayHtml(null, ".csv", output, 3);
    //    Runtime.getRuntime().exec("notepad.exe " + outputFile);
  }
  public static void displayEmail(Message email)
  {
    if (email == null)
    { return; }
    try
    {
      File f = File.createTempFile("email", ".eml");
      f.deleteOnExit();
      FileOutputStream out = new FileOutputStream(f);
      email.writeTo(out);
      out.close();
      displayFile(f.getAbsolutePath());
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void displayFile(String fileName)
  {
    String cmd = "";
    if (File.separatorChar == '\\')
    {
      cmd = "cmd /C start \"Needed Title\" \"%s\" /B";
    }
    else
    {
      cmd = "open %s";
    }
    try
    {
      cmd = String.format(cmd, fileName);
      Runtime.getRuntime().exec(cmd);
      Thread.sleep(2000);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static double getTimerMultiplier()
  {
    long start = System.currentTimeMillis();
    ThreadUtils.sleep(500);
    long end = System.currentTimeMillis();
    return (end - start) / 500.00;
  }
  public static void displayImage(BufferedImage image)
  {
    try
    {
      File f = File.createTempFile("temp", ".gif");
      ImageWriter.writeImage(image, new FileOutputStream(f), ImageWriter.Encoding.GIF);
      Runtime.getRuntime().exec("C:\\PROGRA~1\\MOZILL~1\\FIREFOX.EXE " + f.getAbsolutePath());
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static StackTraceReflectionResult getCurrentFileForMethod(int ignoreLevels)
  {
    return getCurrentFileForMethod(new StackElementLevelSelector(ignoreLevels + 2));
  }
  public static StackTraceReflectionResult getCurrentFileForMethod(StackElementSelector stackElementSelector)
  {
    StackTraceElement trace[] = ThreadUtils.getStackTrace();
    stackElementSelector.increment();
    return getCurrentFileForMethod(stackElementSelector, trace);
  }
  public static StackTraceReflectionResult getCurrentFileForMethod(StackElementSelector stackElementSelector,
      StackTraceElement[] trace)
  {
    try
    {
      StackTraceElement element = stackElementSelector.selectElement(trace);
      return getInfo(element);
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  private static StackTraceReflectionResult getInfo(StackTraceElement element)
  {
    String fullClassName = element.getClassName();
    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    className = handleInnerClasses(className);
    String fileName = element.getFileName();
    File dir = getSourceDirectory.get().call(ObjectUtils.loadClass(fullClassName), fileName);
    String methodName = unrollLambda(element.getMethodName());
    return new StackTraceReflectionResult(dir, className, fullClassName, methodName);
  }
  private static String handleInnerClasses(String className)
  {
    return className.replaceAll("\\$", ".");
  }

  public static String unrollLambda(String methodName)
  {
    Matcher javaMatcher = unrollJavaLambdaPattern.matcher(methodName);
    if (javaMatcher.matches())
    { return javaMatcher.group(1); }

    Matcher kotlinMatcher = unrollKotlinLambdaPattern.matcher(methodName);
    if (kotlinMatcher.matches())
    { return kotlinMatcher.group(1); }
    return methodName;
  }
  private static final Pattern unrollJavaLambdaPattern = Pattern.compile("lambda\\$(.*)\\$\\d+");
  private static final Pattern unrollKotlinLambdaPattern = Pattern.compile("(.*?)(\\$lambda-\\d+)+");
}
