package com.spun.util.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.spun.util.MySystem;

public class ServletLogWriterFactory
{
  private static String                       TOMCAT_LOGS_PATH = getLogPath();
  private static HashMap<String, PrintWriter> writers          = new HashMap<String, PrintWriter>();
  public static void reset()
  {
    TOMCAT_LOGS_PATH = getLogPath();
    writers = new HashMap<String, PrintWriter>();
  }
  public static PrintWriter getWriter(BasicServlet servlet) throws IOException
  {
    String name = servlet.getClass().getName();
    if (writers.get(name) == null)
    {
      return createWriter(name);
    }
    else
    {
      return (PrintWriter) writers.get(name);
    }
  }
  private static String getLogPath()
  {
    String catalina = System.getProperty("catalina.base");
    if (catalina == null)
    {
      MySystem.variable("properties", System.getProperties());
    }
    catalina = catalina == null ? "." + File.separator : catalina;
    return catalina + File.separator + "logs";
  }
  private static PrintWriter createWriter(String name) throws IOException
  {
    String shortName = name.indexOf("$") == -1 ? name.substring(name.lastIndexOf(".") + 1) : name.substring(name
        .lastIndexOf("$") + 1);
    File file = new File(TOMCAT_LOGS_PATH + File.separator + shortName + ".log");
    file.getParentFile().mkdirs();
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    PrintWriter bWriter = new PrintWriter(writer, true);
    writers.put(name, bWriter);
    return bWriter;
  }
}
