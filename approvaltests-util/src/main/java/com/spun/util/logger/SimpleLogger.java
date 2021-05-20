package com.spun.util.logger;

public class SimpleLogger
{
  private static final SimpleLoggerInstance log = new SimpleLoggerInstance(1);
  public static void toggleAll(boolean t)
  {
    log.toggleAll(t);
  }
  public static void setHourGlassWrap(int numberOfDots)
  {
    log.setHourGlassWrap(numberOfDots);
  }
  public static void hourGlass()
  {
    log.hourGlass();
  }
  @Deprecated
  //use useMarkers
  public static void markerIn(String statement)
  {
    log.markerIn(statement);
  }
  @Deprecated
  //use useMarkers
  public synchronized static void markerOut(String text)
  {
    log.markerOut(text);
  }
  public synchronized static void query(String sqlQuery)
  {
    log.query(sqlQuery);
  }
  /**
   * Prints to screen any variable information to be viewed.
   **/
  public synchronized static void query(String queryName, Object sqlQuery)
  {
    log.query(queryName, sqlQuery);
  }
  public static void variableFormated(String string, Object... parameters)
  {
    log.variableFormated(string, parameters);
  }
  public synchronized static void variable(String statement)
  {
    log.variable(statement);
  }
  /**
   * Prints to screen any variable information to be viewed.
   **/
  public synchronized static void variable(String name, Object value)
  {
    log.variable(name, value);
  }
  public synchronized static void variable(String name, Object array[])
  {
    log.variable(name, array);
  }
  public synchronized static <T> void variable(T array[])
  {
    log.variable(array);
  }
  public synchronized static void message(String Statement)
  {
    log.message(Statement);
  }
  public static void event(String Statement)
  {
    log.event(Statement);
  }
  public synchronized static void warning(String statement)
  {
    log.warning(statement);
  }
  public synchronized static void warning(Throwable throwable)
  {
    log.warning(throwable);
  }
  public synchronized static void warning(String statement, Throwable throwable)
  {
    log.warning(statement, throwable);
  }
  /**
   * Logs the current memory status [total, used, free].
   * This forces garbage collection to run first. 
   **/
  public static void logMemoryStatus()
  {
    log.logMemoryStatus();
  }
  /**
   * {@code
   * try (Markers m = SimpleLogger.useMarkers();)
   * {
   * }
   * }
   */
  public static Markers useMarkers()
  {
    return log.useMarkers();
  }
  public static StringBuffer logToString()
  {
    return log.logToString();
  }
  public static Appendable logToNothing()
  {
    return logTo(new NullLogger());
  }
  public static void useOutputFile(String file, boolean addDateStamp)
  {
    log.useOutputFile(file, addDateStamp);
  }
  public static Appendable logTo(Appendable writer)
  {
    log.logTo(writer);
    return writer;
  }
  public static Appendable getLogTo()
  {
    return log.getLogTo();
  }
  public static SimpleLoggerInstance get()
  {
    return log;
  }
}
