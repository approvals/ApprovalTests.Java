package com.spun.util.logger;

import com.spun.util.QuietAutoCloseable;
import com.spun.util.SingleWrapper;
import com.spun.util.ThreadedWrapper;
import com.spun.util.Wrapper;

public class SimpleLogger
{
  private static Wrapper<SimpleLoggerInstance> wrapper = new SingleWrapper<>(new SimpleLoggerInstance(1));
  public static void toggleAll(boolean t)
  {
    wrapper.get().toggleAll(t);
  }

  public static void setHourGlassWrap(int numberOfDots)
  {
    wrapper.get().setHourGlassWrap(numberOfDots);
  }

  public static void hourGlass()
  {
    wrapper.get().hourGlass();
  }

  @Deprecated
  //use useMarkers
  public static void markerIn(String statement)
  {
    wrapper.get().markerIn(statement);
  }

  @Deprecated
  //use useMarkers
  public synchronized static void markerOut(String text)
  {
    wrapper.get().markerOut(text);
  }

  public synchronized static void query(String sqlQuery)
  {
    wrapper.get().query(sqlQuery);
  }

  /**
   * Prints to screen any variable information to be viewed.
   **/
  public synchronized static void query(String queryName, Object sqlQuery)
  {
    wrapper.get().query(queryName, sqlQuery);
  }

  public static void variableFormated(String string, Object... parameters)
  {
    wrapper.get().variableFormated(string, parameters);
  }

  public synchronized static void variable(String statement)
  {
    wrapper.get().variable(statement);
  }

  public static void variable(String name, Object value, boolean showTypes)
  {
    wrapper.get().variable(name, value, showTypes);
  }

  /**
   * Prints to screen any variable information to be viewed.
   **/
  public synchronized static void variable(String name, Object value)
  {
    variable(name, value, false);
  }

  public synchronized static void variable(String name, Object[] array)
  {
    wrapper.get().variable(name, array);
  }

  public synchronized static <T> void variable(T[] array)
  {
    wrapper.get().variable(array);
  }

  public synchronized static void message(String Statement)
  {
    wrapper.get().message(Statement);
  }

  public static void event(String Statement)
  {
    wrapper.get().event(Statement);
  }

  public synchronized static void warning(String statement)
  {
    wrapper.get().warning(statement);
  }

  public synchronized static void warning(Throwable throwable)
  {
    wrapper.get().warning(throwable);
  }

  public synchronized static void warning(String statement, Throwable throwable)
  {
    wrapper.get().warning(statement, throwable);
  }

  /**
   * Logs the current memory status [total, used, free].
   * This forces garbage collection to run first.
   **/
  public static void logMemoryStatus()
  {
    wrapper.get().logMemoryStatus();
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
    return wrapper.get().useMarkers();
  }

  public static StringBuffer logToString()
  {
    return logToString(true);
  }

  public static StringBuffer logToString(boolean threadSafe)
  {
    synchronized (SimpleLogger.wrapper)
    {
      if (threadSafe && !(wrapper instanceof ThreadedWrapper))
      {
        wrapper = new ThreadedWrapper<>(() -> new SimpleLoggerInstance(1));
      }
      return wrapper.get().logToString();
    }
  }

  public static Appendable logToNothing()
  {
    return logTo(new NullLogger());
  }

  public static void useOutputFile(String file, boolean addDateStamp)
  {
    wrapper.get().useOutputFile(file, addDateStamp);
  }

  public static Appendable logTo(Appendable writer)
  {
    wrapper.get().logTo(writer);
    return writer;
  }

  public static Appendable getLogTo()
  {
    return wrapper.get().getLogTo();
  }

  public static SimpleLoggerInstance get()
  {
    return wrapper.get();
  }

  public static QuietAutoCloseable quiet()
  {
    Appendable originalLogTo = getLogTo();
    logToNothing();
    return () -> logTo(originalLogTo);
  }
}
