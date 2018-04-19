package com.spun.util.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.mail.Message;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.spun.util.ArrayUtils;
import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;
import com.spun.util.images.ImageWriter;
import com.spun.util.io.FileUtils;
import com.spun.util.io.StackElementLevelSelector;
import com.spun.util.io.StackElementSelector;

import junit.framework.TestCase;

public class TestUtils
{
  private static Random      random;
  public static final String INTERNET_EXPLORER = "\"C:\\Program Files\\Internet Explorer\\iexplore.exe\" ";
  /***********************************************************************/
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
  /***********************************************************************/
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
  /***********************************************************************/
  public static String getRandomString()
  {
    if (random == null)
    {
      random = new Random();
    }
    return Long.toString(Math.abs(random.nextLong()), 36);
  }
  /***********************************************************************/
  public static void displayHtml(String htmlOutput) throws FileNotFoundException, IOException, InterruptedException
  {
    displayHtml(null, ".html", htmlOutput, 3);
  }
  /***********************************************************************/
  public static void displayHtmlFile(String fileName) throws IOException
  {
    displayFile(fileName);
  }
  public static void displayHtmlFile(File file) throws IOException
  {
    if (!file.exists()) { return; }
    displayHtmlFile(file.getAbsolutePath());
  }
  /***********************************************************************/
  public static void displayHtml(String outputFile, String htmlOutput)
      throws FileNotFoundException, IOException, InterruptedException
  {
    displayHtml(outputFile, ".html", htmlOutput, 15);
  }
  /***********************************************************************/
  public static void displayHtml(String outputFile, String fileExtention, String htmlOutput, int secondsTimeout)
      throws FileNotFoundException, IOException, InterruptedException
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
  /***********************************************************************/
  public static void displayText(String output) throws IOException, InterruptedException
  {
    displayHtml(null, ".txt", output, 3);
  }
  /***********************************************************************/
  public static void displayExcel(String output) throws IOException, InterruptedException
  {
    displayHtml(null, ".csv", output, 3);
    //    Runtime.getRuntime().exec("notepad.exe " + outputFile);
  }
  /***********************************************************************/
  public static void assertForEach(String comment, Object[] objects, String method, Object expectedResult)
  {
    if (objects == null || objects.length == 0) { return; }
    try
    {
      Method m = objects[0].getClass().getMethod(method, (Class[]) null);
      for (int i = 0; i < objects.length; i++)
      {
        Object v = m.invoke(objects[i], (Object[]) null);
        TestCase.assertEquals(comment + " - FOR - [" + i + "]", expectedResult, v);
      }
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  /***********************************************************************/
  public static void assertEqualForMethods(String title, Object expectedResult, Object testResult,
      String[] matchingMethods)
  {
    try
    {
      Method[] m1 = ObjectUtils.getMethodsForObject(expectedResult, matchingMethods);
      Method[] m2 = ObjectUtils.getMethodsForObject(testResult, matchingMethods);
      for (int i = 0; i < m1.length; i++)
      {
        Object v1 = m1[i].invoke(expectedResult, (Object[]) null);
        Object v2 = m2[i].invoke(testResult, (Object[]) null);
        TestCase.assertEquals(title + " - FOR - " + matchingMethods[i], v1, v2);
      }
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  /***********************************************************************/
  public static void displayEmail(Message email)
  {
    if (email == null) { return; }
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
  /***********************************************************************/
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
  /***********************************************************************/
  public static double getTimerMultiplier() throws InterruptedException
  {
    long start = System.currentTimeMillis();
    Thread.sleep(500);
    long end = System.currentTimeMillis();
    return (end - start) / 500.00;
  }
  /***********************************************************************/
  public static void assertLength(int length, Object[] array)
  {
    if (length != array.length)
    {
      TestCase.fail(String.format("Array.length %s != %s \n %s", length, array.length, Arrays.asList(array)));
    }
  }
  /***********************************************************************/
  public static void assertLength(int length, List<?> list)
  {
    if (length != list.size())
    {
      TestCase.fail(String.format("Array.length %s != %s \n %s", length, list.size(), list));
    }
  }
  /***********************************************************************/
  public static void displayImage(BufferedImage image) throws Exception
  {
    File f = File.createTempFile("temp", ".gif");
    ImageWriter.writeImage(image, new FileOutputStream(f), ImageWriter.Encoding.GIF);
    Runtime.getRuntime().exec("C:\\PROGRA~1\\MOZILL~1\\FIREFOX.EXE " + f.getAbsolutePath());
  }
  /***********************************************************************/
  /***********************************************************************/
  public static void assertContains(String expecting, String[] in)
  {
    if (!ArrayUtils.contains(in, expecting))
    {
      TestCase.fail(String.format("Didn't find '%s' in %s", expecting, Arrays.asList(in)));
    }
  }
  public static void assertEqualArray(Object[] expected, Object[] actual)
  {
    boolean passed = (expected.length == actual.length);
    if (passed)
    {
      for (int i = 0; i < actual.length; i++)
      {
        passed &= expected[i].equals(actual[i]);
      }
    }
    if (!passed)
    {
      TestCase.fail(String.format("Arrays didn't Match \n[expected] = %s\n[actual]=%s", Arrays.asList(expected),
          Arrays.asList(actual)));
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
  private static StackTraceReflectionResult getInfo(StackTraceElement element) throws ClassNotFoundException
  {
    String fullClassName = element.getClassName();
    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    String fileName = element.getFileName();
    File dir = ClassUtils.getSourceDirectory(ObjectUtils.loadClass(fullClassName), fileName);
    return new StackTraceReflectionResult(dir, className, element.getMethodName());
  }
}