package com.spun.util.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.lambda.functions.Function2;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;
import com.spun.util.images.ImageWriter;
import com.spun.util.io.FileUtils;
import com.spun.util.io.StackElementLevelSelector;
import com.spun.util.io.StackElementSelector;

public class TestUtils
{
  private static Random                         random;
  private static Function2<Class, String, File> getSourceDirectory = ClassUtils::getSourceDirectory;
  public static SourceDirectoryRestorer registerSourceDirectoryFinder(
      Function2<Class, String, File> sourceDirectoryFinder)
  {
    SourceDirectoryRestorer c = new SourceDirectoryRestorer();
    TestUtils.getSourceDirectory = sourceDirectoryFinder;
    return c;
  }
  public static class SourceDirectoryRestorer implements AutoCloseable
  {
    private final Function2<Class, String, File> original;
    public SourceDirectoryRestorer()
    {
      this.original = TestUtils.getSourceDirectory;
    }
    @Override
    public void close()
    {
      TestUtils.getSourceDirectory = original;
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
  public static void displayHtmlFile(String fileName) throws IOException
  {
    displayFile(fileName);
  }
  public static void displayHtmlFile(File file) throws IOException
  {
    if (!file.exists())
    { return; }
    displayHtmlFile(file.getAbsolutePath());
  }
  public static void displayHtml(String outputFile, String htmlOutput)
      throws FileNotFoundException, IOException, InterruptedException
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
  public static void displayText(String output) throws IOException, InterruptedException
  {
    displayHtml(null, ".txt", output, 3);
  }
  public static void displayExcel(String output) throws IOException, InterruptedException
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
  public static double getTimerMultiplier() throws InterruptedException
  {
    long start = System.currentTimeMillis();
    Thread.sleep(500);
    long end = System.currentTimeMillis();
    return (end - start) / 500.00;
  }
  public static void displayImage(BufferedImage image) throws Exception
  {
    File f = File.createTempFile("temp", ".gif");
    ImageWriter.writeImage(image, new FileOutputStream(f), ImageWriter.Encoding.GIF);
    Runtime.getRuntime().exec("C:\\PROGRA~1\\MOZILL~1\\FIREFOX.EXE " + f.getAbsolutePath());
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
  private static StackTraceReflectionResult getInfo(StackTraceElement element) throws ClassNotFoundException
  {
    String fullClassName = element.getClassName();
    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    className = handleInnerClasses(className);
    String fileName = element.getFileName();
    File dir = getSourceDirectory.call(ObjectUtils.loadClass(fullClassName), fileName);
    String methodName = unrollLambda(element.getMethodName());
    return new StackTraceReflectionResult(dir, className, fullClassName, methodName);
  }
  private static String handleInnerClasses(String className)
  {
    return className.replaceAll("\\$", ".");
  }
  public static String unrollLambda(String methodName)
  {
    Pattern p = Pattern.compile("lambda\\$(.*)\\$\\d+");
    Matcher m = p.matcher(methodName);
    if (m.matches())
    { return m.group(1); }
    return methodName;
  }
}
