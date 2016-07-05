package com.spun.util;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * A class for printing comments in a standardized format.
 **/
public class MySystem
{
  public static boolean           USE_LOG_FILE        = false;
  public static final String      CLIENT_OUTPUT_FILE  = "logs\\CCS_ClientLog.txt";
  public static final String      SERVER_OUTPUT_FILE  = "logs\\ServerLog.txt";
  public static final String      LOGGER_OUTPUT_FILE  = "logs\\Logger.txt";
  public static final String      SERVLET_OUTPUT_FILE = "logs\\ServletLog.txt";
  public static String            ROOT_DIR            = "C:\\temp\\";
  public static final int         IN                  = 1;
  public static final int         OUT                 = -1;
  public static boolean           marker              = true;
  public static boolean           event               = true;
  public static boolean           variable            = true;
  public static boolean           query               = true;
  public static int               hourGlass           = 0;
  public static int               hourGlassWrap       = 100;
  private static int              m_indent            = 0;
  private static long             lastTime            = System.currentTimeMillis();
  private static DualOutputStream cos;
  private static String           logFileUsed         = null;
  private static PrintWriter      SYSTEM_OUT_WRITER   = new PrintWriter(System.out, true);
  /***********************************************************************/
  public static void toggleAll(boolean t)
  {
    marker = t;
    event = t;
    variable = t;
    query = t;
  }
  /***********************************************************************/
  private static void clearHourGlass()
  {
    if (hourGlass > 0)
    {
      System.out.println("");
      hourGlass = 0;
    }
  }
  /***********************************************************************/
  public static void setHourGlassWrap(int numberOfDots)
  {
    hourGlassWrap = numberOfDots;
  }
  /***********************************************************************/
  public static void hourGlass()
  {
    if (hourGlass >= hourGlassWrap)
    {
      clearHourGlass();
    }
    hourGlass++;
    if ((hourGlass % 10) == 0)
    {
      System.out.print(hourGlass / 10);
    }
    else
    {
      System.out.print(".");
    }
  }
  /***********************************************************************/
  public static long startTimer()
  {
    return System.currentTimeMillis();
  }
  /***********************************************************************/
  public static void stopTimer(long startTime, long maxTime, String function)
  {
    long diff = (System.currentTimeMillis() - startTime);
    if (diff > maxTime)
    {
      MySystem.warning("Time Limit Exceeded - " + function + " [" + new DateDifference(diff).getStandardTimeText(2)
          + " > " + maxTime + "]");
    }
  }
  /***********************************************************************/
  /**
   * Sets output to a file.
   * @param file The name of the file.
   **/
  public synchronized static void useOutputFile(String file, boolean addDateStamp)
  {
    if (!USE_LOG_FILE || (logFileUsed != null) || (file == null)) { return; }
    try
    {
      if (addDateStamp)
      {
        int seperator = file.lastIndexOf('.');
        if (seperator != 0)
        {
          file = file.substring(0, seperator) + "[" + System.currentTimeMillis() + "]" + file.substring(seperator);
        }
        else
        {
          file += System.currentTimeMillis();
        }
      }
      cos = new DualOutputStream();
      cos.setOutputStream(file);
      java.io.PrintStream p = new java.io.PrintStream(cos);
      logFileUsed = file;
      System.setErr(p);
      System.setOut(p);
    }
    catch (SecurityException e)
    {
    }
    catch (Exception e)
    {
    }
  }
  /***********************************************************************/
  /**
   * Sets output to a file.
   * @param file The name of the file.
   **/
  public synchronized static void useOutputFile(String file)
  {
    useOutputFile(file, false);
  }
  /***********************************************************************/
  /**
   * Closes the Output File.
   **/
  public synchronized static void closeOutputFile()
  {
    if (logFileUsed == null) { return; }
    try
    {
      cos.close();
    }
    catch (Exception e)
    {
    }
  }
  /***********************************************************************/
  /**
   * Prints to screen the marker specifying function entered.
   **/
  public synchronized static void markerOut()
  {
    markerOut(extractMarkerText());
  }
  /***********************************************************************/
  /**
   * Prints to screen the marker specifying function entered.
   **/
  public synchronized static void markerIn()
  {
    markerIn(extractMarkerText());
  }
  public static void markerIn(String statement)
  {
    if (!marker) { return; }
    System.out.println(timeStamp() + "**** " + statement + " - IN");
    m_indent++;
  }
  /***********************************************************************/
  private static String extractMarkerText()
  {
    try
    {
      StackTraceElement trace[] = ThreadUtils.getStackTrace();
      StackTraceElement element = trace[4];
      String text = element.getMethodName();
      String className = element.getClassName();
      className = className.substring(className.lastIndexOf(".") + 1);
      text += "(" + className + ":" + element.getLineNumber() + ")";
      return text;
    }
    catch (Throwable t)
    {
      return "Can't Inspect Stack Trace";
    }
  }
  /***********************************************************************/
  private static String getIndent()
  {
    String theIndention = "";
    for (int i = 0; i < m_indent; i++)
    {
      theIndention += " ";
    }
    return theIndention;
  }
  /***********************************************************************/
  private static String timeStamp()
  {
    clearHourGlass();
    String text = "";
    long current = System.currentTimeMillis();
    java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();
    text = "[" + df.format(new java.util.Date(current)) + " ~" + padNumber(current - lastTime) + "ms]"
        + getIndent();
    lastTime = current;
    return text;
  }
  /***********************************************************************/
  private static String padNumber(long number)
  {
    String text = "" + number;
    while (text.length() < 6)
    {
      text = "0" + text;
    }
    return text;
  }
  /***********************************************************************/
  private static String indentMessage(String message)
  {
    Vector<Integer> v = new Vector<Integer>();
    int place = 0;
    while ((place = message.indexOf('\n', place + 1)) != -1)
    {
      v.addElement(place);
    }
    if (v.size() == 0)
    {
      // no '\n'
      return message;
    }
    String theIndention = getIndent();
    StringBuffer buffer = new StringBuffer(message);
    for (int i = (v.size() - 1); i >= 0; i--)
    {
      int tempplace = ((Integer) v.elementAt(i)).intValue();
      buffer.insert(tempplace + 1, theIndention);
    }
    return buffer.toString();
  }
  /***********************************************************************/
  /**
   * Prints to screen the marker specifying function exited.
   **/
  public synchronized static void markerOut(String text)
  {
    if (!marker) { return; }
    m_indent--;
    System.out.println(timeStamp() + "**** " + text + " - OUT");
  }
  /***********************************************************************/
  /**
   * Prints to screen any variable information to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void query(String sqlQuery)
  {
    if (!query) { return; }
    System.out.println(timeStamp() + "~~> SQL - " + sqlQuery);
  }
  /***********************************************************************/
  /**
   * Prints to screen any variable information to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void query(String queryName, Object sqlQuery)
  {
    if (!query) { return; }
    System.out.println(timeStamp() + "~~> SQL [" + queryName + "] - " + sqlQuery);
  }
  /***********************************************************************/
  /**
   * Prints to screen any variable information to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void variable(String statement)
  {
    variable(statement, SYSTEM_OUT_WRITER);
  }
  /***********************************************************************/
  public static void variableFormated(String string, Object... parameters)
  {
    variable(String.format(string, parameters));
  }
  /***********************************************************************/
  public synchronized static void variable(String statement, PrintWriter out)
  {
    if (!variable) { return; }
    out.println(timeStamp() + "*=>" + statement);
  }
  /***********************************************************************/
  /**
   * Prints to screen any variable information to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void variable(String name, Object value)
  {
    if (!variable) { return; }
    System.out.println(timeStamp() + "*=> " + name + " = '" + (value == null ? null : value.toString()) + "'");
  }
  /***********************************************************************/
  /**
   * Prints to screen any variable information to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void variable(String name, Object array[])
  {
    if (!variable) { return; }
    name = (name == null ? "array" : name);
    if (array == null || array.length == 0)
    {
      System.out.println(timeStamp() + "*=> " + name + ".length = 0");
    }
    else
    {
      for (int i = 0; i < array.length; i++)
      {
        System.out.println(
            timeStamp() + "*=> " + name + "[" + i + "] = " + ((array[i] == null) ? "null" : array[i].toString()));
      }
    }
  }
  /***********************************************************************/
  /**
   * Prints to screen any variable information to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void variable(Object array[])
  {
    variable(null, array);
  }
  /***********************************************************************/
  /**
   * Prints to screen any messages to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void message(String Statement)
  {
    System.out.println(timeStamp() + indentMessage(Statement));
  }
  /***********************************************************************/
  /**
   * Prints to screen any events to be viewed.
   * @param Statement The statement to print
   **/
  public static void event(String Statement)
  {
    if (!event) { return; }
    System.out.println(timeStamp() + "*--" + Statement);
  }
  /***********************************************************************/
  /**
   * Prints to screen any warnings to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void warning(String statement)
  {
    warning(statement, null);
  }
  /***********************************************************************/
  /**
   * Prints to screen any warnings to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void warning(Throwable throwable)
  {
    warning(null, throwable);
  }
  /***********************************************************************/
  /**
   * Prints to screen any warnings to be viewed.
   * @param Statement The statement to print
   **/
  public synchronized static void warning(String statement, Throwable throwable)
  {
    warning(statement, throwable, null);
  }
  public synchronized static void warning(String statement, Throwable throwable, PrintWriter out)
  {
    clearHourGlass();
    dualPrintln("******************************************************************************************", out);
    dualPrintln(timeStamp(), out);
    if (statement != null)
    {
      dualPrintln(statement, out);
    }
    printFullTrace(throwable, false, out);
    if (throwable instanceof OutOfMemoryError)
    {
      dumpMemory(out);
    }
    dualPrintln("******************************************************************************************", out);
    if ((throwable instanceof Error) || (throwable instanceof RuntimeException))
    {
      //mailLog("pschaefer@atsgroup.com;lfalco@atsgroup.com","TESTING ERROR","TESTING ERROR","TESTING ERROR","csem03");
    }
  }
  /***********************************************************************/
  private static void dualPrintln(String string, PrintWriter out)
  {
    System.out.println(string);
    if (out != null)
    {
      out.println(string);
    }
  }
  /***********************************************************************/
  private static void printFullTrace(Throwable throwable, boolean causedBy, PrintWriter out)
  {
    if (throwable != null)
    {
      dualPrintln((causedBy ? "Caused by : " : "") + throwable.getMessage(), out);
      throwable.printStackTrace();
      if (out != null)
      {
        throwable.printStackTrace(out);
      }
      if (throwable.getCause() != null)
      {
        printFullTrace(throwable.getCause(), true, out);
      }
    }
  }
  /***********************************************************************/
  /**
   * The log file being used, null if not being used.
   * @return log file being used.
   **/
  public static String getLogFileUsed()
  {
    return logFileUsed;
  }
  /************************************************************************/
  /**
   * Returns all the Thread output of a group as a string.
   * @return A List of The thread, and if it is alive and/or interrupted.
   **/
  public static String dumpAllThreadsInGroup(ThreadGroup tg)
  {
    Thread[] threads = new Thread[tg.activeCount()];
    tg.enumerate(threads);
    StringBuffer s = new StringBuffer("Active Thread Dump\n");
    for (int x = 0; x < threads.length; x++)
    {
      if (threads[x] != null)
      {
        s.append("thread=" + threads[x] + ", isAlive=" + threads[x].isAlive() + ", isInterrupted="
            + threads[x].isInterrupted() + "\n");
      }
    }
    return s.toString();
  }
  /************************************************************************/
  /**
   * Dumps the current memory status [total, used, free].
   * This forces garbage collection to run first. 
   **/
  public static void dumpMemory()
  {
    dumpMemory(SYSTEM_OUT_WRITER);
  }
  /************************************************************************/
  public static void dumpMemory(PrintWriter out)
  {
    System.gc();
    java.text.NumberFormat format = java.text.NumberFormat.getNumberInstance();
    long freeMemory = Runtime.getRuntime().freeMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    long usedMemory = totalMemory - freeMemory;
    String statement = "Memory [total, used, free] = [" + format.format(totalMemory) + " , "
        + format.format(usedMemory) + " , " + format.format(freeMemory) + "]";
    variable(statement, out);
  }
  /************************************************************************/
  /**
   * Dumps the current thread status for ALL groups.
   * @see  My_System.dumpAllThreadsInGroup(ThreadGroup)
   **/
  public static String dumpAllThreads()
  {
    ThreadGroup place = Thread.currentThread().getThreadGroup();
    while (place.getParent() != null)
    {
      place = place.getParent();
    }
    //place.list();
    return dumpAllThreadsInGroup(place);
  }
  /***********************************************************************/
  public static String getStringArrayToString(String array[])
  {
    StringBuffer buffer = new StringBuffer("[");
    for (int i = 0; i < array.length; i++)
    {
      buffer.append("\"" + array[i] + "\"\n");
    }
    buffer.delete(buffer.length() - 3, buffer.length());
    buffer.append("]");
    return buffer.toString();
  }
  /**
   * <pre>
   * {@code
   * try (Markers m = MySystem.useMarkers();)
   * {
   * }
   * 
   * </pre> 
   */
  public static Markers useMarkers()
  {
    final String text = extractMarkerText();
    return new Markers(text);
  }
  /***********************************************************************/
  /***********************************************************************/
}