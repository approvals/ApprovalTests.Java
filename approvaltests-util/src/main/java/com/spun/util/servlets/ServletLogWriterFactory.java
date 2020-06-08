package com.spun.util.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.spun.util.logger.SimpleLogger;

public class ServletLogWriterFactory
{
  private static String                      TOMCAT_LOGS_PATH = getLogPath();
  private static HashMap<String, Appendable> writers          = new HashMap<String, Appendable>();

  public static void reset()
  {
    TOMCAT_LOGS_PATH = getLogPath();
    writers = new HashMap<>();
  }
  public static Appendable getWriter(BasicServlet servlet) throws IOException
  {
    String name = servlet.getClass().getName();
    if (writers.get(name) == null)
    {
      Appendable writer = createWriter(name);
      writers.put(name, writer);
      return writer;
    }
    else
    {
      return writers.get(name);
    }
  }
  private static String getLogPath()
  {
    String catalina = System.getProperty("catalina.base");
    if (catalina == null)
    {
      SimpleLogger.variable("properties", System.getProperties());
    }
    catalina = catalina == null ? "." + File.separator : catalina;
    return catalina + File.separator + "logs";
  }
  private static Appendable createWriter(String name) throws IOException
  {
    String shortName = !name.contains("$")
        ? name.substring(name.lastIndexOf(".") + 1)
        : name.substring(name.lastIndexOf("$") + 1);
    String logName = TOMCAT_LOGS_PATH + File.separator + shortName + ".log";
    File file = new File(logName);
    file.getParentFile().mkdirs();

    OutputStreamWriter out = new OutputStreamWriter(
        new FileOutputStream(file), StandardCharsets.UTF_8);
    BufferedWriter writer = new BufferedWriter(out);
    PrintWriter bWriter = new PrintWriter(writer, true);
    writers.put(name, bWriter);
    return bWriter;
  }
}
